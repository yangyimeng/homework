package com.yangyimeng.homework.grpc.routeguide;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.routeguide.Feature;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

public class RouteGuideServer {

    private final int port;
    private final Server server;

    public RouteGuideServer(int port) throws IOException{
        this(port, RouteGuideUtil.getDefaultFeaturesFile());
    }

    public RouteGuideServer(int port, URL featureFile) throws IOException{
        this(ServerBuilder.forPort(port), port, RouteGuideUtil.parseFeatures(featureFile));
    }

    public RouteGuideServer(ServerBuilder<?> serverBuilder, int port, Collection<Feature> features) {
        this.port = port;
        this.server = serverBuilder.addService(new RouteGuideService(features)).build();
    }

    public void start() throws IOException{
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                RouteGuideServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        RouteGuideServer server = new RouteGuideServer(8980);
        server.start();
        server.blockUntilShutdown();
    }
}
