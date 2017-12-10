package edu.ucu.assignmenttwo.http;

public class InfoHandler implements HttpHandler {
    @Override
    public void handle(int code) {
        System.out.printf("InfoHandler received code %d\n", code);
    }
}
