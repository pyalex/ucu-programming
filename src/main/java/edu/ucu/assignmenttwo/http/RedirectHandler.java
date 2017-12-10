package edu.ucu.assignmenttwo.http;

public class RedirectHandler implements HttpHandler{
    @Override
    public void handle(int code) {
        System.out.printf("User will be redirected with code %d\n", code);
    }
}
