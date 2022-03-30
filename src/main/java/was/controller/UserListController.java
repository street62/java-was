package was.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import was.db.DataBase;
import was.db.Session;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.model.User;
import was.util.HttpRequestUtils;

public class UserListController implements MyController {

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        HttpRequestUtils.Pair cookie = request.getPairs().stream().
                filter(pair -> pair.getKey().equals("Cookie")).
                findAny().orElse(new HttpRequestUtils.Pair("Cookie", ""));

        String[] split = cookie.getValue().split("; ");
        List<HttpRequestUtils.Pair> pairs = Arrays.stream(split)
                .map(s -> s.split("="))
                .map(strings -> new HttpRequestUtils.Pair(strings[0], strings[1]))
                .collect(Collectors.toList());

        System.out.println("pairs");
        pairs.stream().forEach(System.out::println);
        System.out.println(isNotLoginUser(pairs));
        if (isNotLoginUser(pairs)) {
            return new HttpResponse("user/login.html", 200);
        }

        writeListHtml();
        return new HttpResponse("user/list.html", 200);
    }

    private boolean isNotLoginUser(List<HttpRequestUtils.Pair> pairs) {
        return pairs.stream()
                .allMatch(pair -> !("sessionId".equals(pair.getKey()) && Session.containsSessionId(pair.getValue())));
    }

    private void writeListHtml() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("./webapp/user/list.html");
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"1\" width=\"500\">");
        sb.append("    <tr>");
        sb.append("        <th>userId</th>");
        sb.append("        <th>password</th>");
        sb.append("        <th>name</th>");
        sb.append("        <th>email</th>");
        sb.append("    </tr>");

        List<User> users = new ArrayList<>(DataBase.findAll());
        for (User user : users) {
            sb.append("    <tr>");
            sb.append("        <td>").append(user.getUserId()).append("</td>");
            sb.append("        <td>").append(user.getPassword()).append("</td>");
            sb.append("        <td>").append(user.getName()).append("</td>");
            sb.append("        <td>").append(user.getEmail()).append("</td>");
            sb.append("    </tr>");
        }
        sb.append("</table>");

        writer.write(sb.toString());
        writer.close();
    }
}
