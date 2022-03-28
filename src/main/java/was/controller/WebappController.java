package was.controller;

import was.controller.MyController;
import was.http.HttpRequest;
import was.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class WebappController implements MyController {

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        return new HttpResponse(request.getPath(), 200);
    }
}
