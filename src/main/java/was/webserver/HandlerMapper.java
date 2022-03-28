package was.webserver;

import was.controller.MyController;
import was.controller.SaveUserController;
import was.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private Map<HttpRequest, MyController> handlerMapper = new HashMap<>();

    public HandlerMapper() {
        handlerMapper.put(new HttpRequest("/user/create", "POST"), new SaveUserController());
    }

    public MyController getHandler(HttpRequest request) {
        return handlerMapper.get(request);
    }
}
