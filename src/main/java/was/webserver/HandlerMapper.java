package was.webserver;

import was.controller.MyController;
import was.controller.SaveUserController;
import was.controller.WebappController;
import was.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private Map<HttpRequest, MyController> handlerMapper = new HashMap<>();

    public HandlerMapper() {
        handlerMapper.put(new HttpRequest("/user/create", "POST"), new SaveUserController());
    }

    public MyController getHandler(HttpRequest request) {
        MyController myController = handlerMapper.get(request);
        if (myController == null) {
            return new WebappController();
        }
        return myController;
    }
}
