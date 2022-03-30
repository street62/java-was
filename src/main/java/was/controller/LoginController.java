package was.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.db.DataBase;
import was.db.Session;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.ParamMap;
import was.model.User;
import was.util.HttpRequestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LoginController implements MyController{
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        ParamMap paramMap = request.getParamMap();
        String userId = paramMap.get("userId");
        String password = paramMap.get("password");
        User user = DataBase.findByUserIdAndPassword(userId, password);

        if (Objects.isNull(user)) {
            log.debug("로그인 실패!");
            return new HttpResponse("user/login_failed.html", 200);
        }
        log.debug("로그인 성공!");

        String sessionId = Session.createSession();
        HttpRequestUtils.Pair cookie = new HttpRequestUtils.Pair("sessionId", sessionId);
        HttpResponse response = new HttpResponse("index.html", 200, cookie);

        return response;
    }


}
