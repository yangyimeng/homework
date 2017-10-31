package com.yangyimeng.homework.grpc.routeguide;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuideGrpc;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RouteGuideClient {

    private final ManagedChannel channel;
    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    private final RouteGuideGrpc.RouteGuideStub asyncStub;

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
            client.listFeatures(400000000, -750000000, 420000000, -730000000);

        } finally {
            client.shutdown();
        }

    }


}
