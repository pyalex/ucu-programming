package edu.ucu.assignmenttwo.http;

public class ServerErrorHandler implements HttpHandler {
    @Override
    public void handle(int code) {
        System.out.printf("Something happened. Server responder with code %d\n", code);
    }
}
