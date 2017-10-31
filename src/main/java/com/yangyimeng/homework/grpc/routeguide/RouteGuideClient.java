package com.yangyimeng.homework.grpc.routeguide;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.routeguide.*;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***
 * @author yangyimeng
 */
public class RouteGuideClient {

    private final ManagedChannel channel;
    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    private final RouteGuideGrpc.RouteGuideStub asyncStub;
    private static final Logger logger = LoggerFactory.getLogger(RouteGuideClient.class);

    private Random random = new Random();


    public RouteGuideClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

    public RouteGuideClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        asyncStub = RouteGuideGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void recordRoute(List<Feature> features, int numPoints) throws InterruptedException {
        logger.info("*** RecordRoute");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
            @Override
            public void onNext(RouteSummary summary) {
                logger.info("Finished trip with {0} points. Passed {1} features. "
                                + "Travelled {2} meters. It took {3} seconds.", summary.getPointCount(),
                        summary.getFeatureCount(), summary.getDistance(), summary.getElapsedTime());
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.error("RecordRoute Failed: {0}", status);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("Finished RecordRoute");
                finishLatch.countDown();
            }
        };

        StreamObserver<Point> requestObserver = asyncStub.recordRoute(responseObserver);
        try {
            // Send numPoints points randomly selected from the features list.
            Random rand = new Random();
            for (int i = 0; i < numPoints; ++i) {
                int index = rand.nextInt(features.size());
                Point point = features.get(index).getLocation();
                logger.info("Visiting point {0}, {1}", RouteGuideUtil.getLatitude(point),
                        RouteGuideUtil.getLongitude(point));
                requestObserver.onNext(point);
                // Sleep for a bit before sending the next one.
                Thread.sleep(rand.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                    return;
                }
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        // Receiving happens asynchronously
        finishLatch.await(1, TimeUnit.MINUTES);
    }



    /**
     * Blocking unary call example.  Calls getFeature and prints the response.
     */
    public void getFeature(int lat, int lon) {

        Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();

        Feature feature;
        try {
            feature = blockingStub.getFeature(request);
            System.out.println(feature);
        } catch (StatusRuntimeException e) {
            return;
        }
    }

    /**
     * Blocking server-streaming example. Calls listFeatures with a rectangle of interest. Prints each
     * response feature as it arrives.
     */
    public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon) throws InterruptedException{

        Rectangle request =
                Rectangle.newBuilder()
                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features;
        try {
//            features = blockingStub.listFeatures(request);
//            for (int i = 1; features.hasNext(); i++) {
//                Feature feature = features.next();
//                System.out.println(feature);
//            }
            final CountDownLatch finishLatch = new CountDownLatch(1);
            asyncStub.listFeatures(request, new StreamObserver<Feature>() {
                @Override
                public void onNext(Feature value) {
                    System.out.println(value);
                }

                @Override
                public void onError(Throwable t) {
                    finishLatch.countDown();
                }

                @Override
                public void onCompleted() {
                    finishLatch.countDown();
                }
            });
            while (finishLatch.getCount() != 0) {
                System.out.println("not finish");
            }
            System.out.println("already finish");
        } catch (StatusRuntimeException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String [] args) throws InterruptedException{
        List<Feature> features;
        try {
            features = RouteGuideUtil.parseFeatures(RouteGuideUtil.getDefaultFeaturesFile());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        RouteGuideClient client = new RouteGuideClient("localhost", 8980);
        try {
            // Looking for a valid feature
            client.getFeature(409146138, -746188906);

            // Feature missing.
            client.getFeature(0, 0);

            // Looking for features between 40, -75 and 42, -73.
            //client.listFeatures(400000000, -750000000, 420000000, -730000000);

            List<Feature> featureList = new ArrayList<>();
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(4000).setLatitude(5000).build()).setName("one").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(5000).setLatitude(6000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(7000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(8000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(9000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(10000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(11000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(12000).build()).setName("two").build());
            featureList.add(Feature.newBuilder().setLocation(Point.newBuilder().setLongitude(6000).setLatitude(13000).build()).setName("two").build());

            client.recordRoute(features, 3);

        } finally {
            client.shutdown();
        }

    }


}
