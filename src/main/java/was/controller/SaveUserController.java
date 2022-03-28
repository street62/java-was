package was.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.db.DataBase;
import was.model.User;

public class SaveUserController implements MyController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String process(Map<String, String> paramMap) {
        String userId = paramMap.get("userId");
        String password = paramMap.get("password");
        String name = paramMap.get("name");
        String email = paramMap.get("email");
        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        log.debug("users: {}", DataBase.findAll());
//        DataBase.findAll().stream().forEach(System.out::println);
        return "index.html";
    }
}
