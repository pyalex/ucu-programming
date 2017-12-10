package edu.ucu.assigmenttwo.http;

public class HttpService {
    public void handleHttpCode(int code) {
        HttpStatus httpCode = HttpStatus.findByHttpCode(code);
        HttpHandler handler =  httpCode.getHandler();
        handler.handle(code);
    }
}
