package com.yangyimeng.homework.grpc.routeguide;


import io.grpc.examples.routeguide.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class RouteGuideService extends RouteGuideGrpc.RouteGuideImplBase{

    private final Collection<Feature> features;
    private final ConcurrentMap<Point, List<RouteNote>> routeNotes = new
            ConcurrentHashMap<Point, List<RouteNote>>();

    protected RouteGuideService(Collection<Feature> features) {
        this.features = features;
    }

    @Override
    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        responseObserver.onNext(checkFeature(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
        int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
        int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
        int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
        int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());
        for (Feature feature : features) {
            if (!RouteGuideUtil.exists(feature)) {
                continue;
            }

            int lat = feature.getLocation().getLatitude();
            int lon = feature.getLocation().getLongitude();
            if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
                responseObserver.onNext(feature);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Point> recordRoute(StreamObserver<RouteSummary> responseObserver) {
        return new StreamObserver<Point>() {
            int pointCount;
            int featureCount;
            int distance;
            Point previous;
            final long startTime = System.nanoTime();

            @Override
            public void onNext(Point point) {
                pointCount++;
                if (RouteGuideUtil.exists(checkFeature(point))) {
                    featureCount++;
                }
                // For each point after the first, add the incremental distance from the previous point to
                // the total distance value.
                if (previous != null) {
                    distance += calcDistance(previous, point);
                }
                previous = point;
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("recordRoute cancelled");
            }

            @Override
            public void onCompleted() {
                long seconds = NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                responseObserver.onNext(RouteSummary.newBuilder().setPointCount(pointCount)
                        .setFeatureCount(featureCount).setDistance(distance)
                        .setElapsedTime((int) seconds).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> responseObserver) {
        return new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                List<RouteNote> notes = getOrCreateNotes(note.getLocation());

                // Respond with all previous notes at this location.
                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                    responseObserver.onNext(prevNote);
                }

                // Now add the new note to the list
                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };    }



    /**
     * Get the notes list for the given location. If missing, create it.
     */
    private List<RouteNote> getOrCreateNotes(Point location) {
        List<RouteNote> notes = Collections.synchronizedList(new ArrayList<RouteNote>());
        List<RouteNote> prevNotes = routeNotes.putIfAbsent(location, notes);
        return prevNotes != null ? prevNotes : notes;
    }

    /**
     * Gets the feature at the given point.
     *
     * @param location the location to check.
     * @return The feature object at the point. Note that an empty name indicates no feature.
     */
    private Feature checkFeature(Point location) {
        for (Feature feature : features) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }

        // No feature was found, return an unnamed feature.
        return Feature.newBuilder().setName("").setLocation(location).build();
    }

    /**
     * Calculate the distance between two points using the "haversine" formula.
     * This code was taken from http://www.movable-type.co.uk/scripts/latlong.html.
     *
     * @param start The starting point
     * @param end The end point
     * @return The distance between the points in meters
     */
    private static int calcDistance(Point start, Point end) {
        double lat1 = RouteGuideUtil.getLatitude(start);
        double lat2 = RouteGuideUtil.getLatitude(end);
        double lon1 = RouteGuideUtil.getLongitude(start);
        double lon2 = RouteGuideUtil.getLongitude(end);
        int r = 6371000; // meters
        double phi1 = toRadians(lat1);
        double phi2 = toRadians(lat2);
        double deltaPhi = toRadians(lat2 - lat1);
        double deltaLambda = toRadians(lon2 - lon1);

        double a = sin(deltaPhi / 2) * sin(deltaPhi / 2)
                + cos(phi1) * cos(phi2) * sin(deltaLambda / 2) * sin(deltaLambda / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return (int) (r * c);
    }

}
