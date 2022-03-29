package was.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.db.DataBase;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.ParamMap;
import was.model.User;

public class SaveUserController implements MyController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        ParamMap paramMap = request.getParamMap();

        String userId = paramMap.get("userId");
        String password = paramMap.get("password");
        String name = paramMap.get("name");
        String email = paramMap.get("email");
        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        log.debug("users: {}", DataBase.findAll());
        return new HttpResponse("index.html", 302);
    }

}
