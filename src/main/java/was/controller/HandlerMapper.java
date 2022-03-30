package was.controller;

import was.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private Map<HttpRequest, MyController> handlerMapper = new HashMap<>();

    public HandlerMapper() {
        handlerMapper.put(new HttpRequest("/user/create", "POST"), new UserSaveController());
        handlerMapper.put(new HttpRequest("/user/login", "POST"), new LoginController());
        handlerMapper.put(new HttpRequest("/user/list", "GET"), new UserListController());
    }

    public MyController getHandler(HttpRequest request) {
        MyController myController = handlerMapper.get(request);
        if (myController == null) {
            return new WebappController();
        }
        return myController;
    }
}
