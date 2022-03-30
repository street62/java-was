package was.controller;

import was.db.DataBase;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.ParamMap;
import was.model.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController implements MyController{
    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        ParamMap paramMap = request.getParamMap();
        String userId = paramMap.get("userId");
        String password = paramMap.get("password");
        User user = DataBase.findByUserIdAndPassword(userId, password);
        if (Objects.isNull(user)) {
            return new HttpResponse("user/login_failed.html", 200);
        }
        HttpResponse response = new HttpResponse("index.html", 200);
//        response.setCookie("sessionid", );

    }


}
