package edu.ucu.assigmenttwo.http;

public class ClientErrorHandler implements HttpHandler {
    @Override
    public void handle(int code) {
        System.out.printf("Client sent invalid request and server responded with code %d\n", code);
    }
}
