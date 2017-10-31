package com.yangyimeng.homework.grpc;


import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase{

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        System.out.println("service : " + request.getName());
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello : " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
