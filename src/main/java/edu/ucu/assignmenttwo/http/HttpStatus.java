package edu.ucu.assigmenttwo.http;

public enum HttpStatus {
    INFO(100, 199, new InfoHandler()),
    SUCCESS(200, 299, new SuccessHandler()),
    REDIRECT(300, 399, new RedirectHandler()),
    CLIENT_ERROR(400, 499, new ClientErrorHandler()),
    SERVER_ERROR(500, 599, new ServerErrorHandler());

    private final int start;
    private final int end;
    private final HttpHandler handler;

    HttpStatus(int start, int end, HttpHandler handler) {
        this.start = start;
        this.end = end;
        this.handler = handler;
    }

    public static HttpStatus findByHttpCode(int code) {
        HttpStatus[] statuses = values();
        for(HttpStatus status: statuses) {
            if (status.start <= code && code <= status.end) {
                return status;
            }
        }
        throw new RuntimeException("HttpStatus does not exist for code " + code);
    }

    public HttpHandler getHandler() {
        return handler;
    }
}
