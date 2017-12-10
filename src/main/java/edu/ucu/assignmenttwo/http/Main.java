package edu.ucu.assignmenttwo.http;

import lombok.SneakyThrows;
import org.fluttercode.datafactory.impl.DataFactory;

public class Main {
    @SneakyThrows
    public static void main(String... args) {
        HttpService service = new HttpService();
        DataFactory dataFactory = new DataFactory();


        while (true) {
            service.handleHttpCode(dataFactory.getNumberBetween(100, 599));

            Thread.sleep(1000);
        }
    }
}
