package was.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import was.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("userId1", new User("userId1", "password1", "name1", "email1@gmail.com"));
        users.put("userId2", new User("userId2", "password2", "name2", "email2@gmail.com"));
        users.put("userId3", new User("userId3", "password3", "name3", "email3@gmail.com"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findByUserId(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static User findByUserIdAndPassword(String userId, String password) {
        User user = findByUserId(userId);
        if (user == null) {
            return null;
        }
        if (user.isCorrectPassword(password)) {
            return user;
        }
        return null;
    }
}
