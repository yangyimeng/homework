package com.yangyimeng.homework.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by liang on 2017/5/30.
 */
public class HelloWorldServer {
    private int port = 50051;
    private Server server;


    private void start() throws IOException{
       server = ServerBuilder.forPort(port)
               .addService(new GreeterImpl())
               .build()
               .start();
        System.out.println("service start...");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException{
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String [] args) throws IOException, InterruptedException{
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }
}
