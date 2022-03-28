package was.controller;

import was.http.HttpRequest;
import was.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface MyController {

    HttpResponse process(HttpRequest request) throws IOException;
}
