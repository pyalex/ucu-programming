package edu.ucu.assignmenttwo.http;


public class SuccessHandler implements HttpHandler {
    @Override
    public void handle(int code) {
        System.out.printf("SuccessHandler received code %d\n", code);
    }
}
