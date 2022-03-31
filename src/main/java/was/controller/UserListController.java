package was.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    private void writeListHtml() throws IOException {
        PrintWriter writer = new PrintWriter("./webapp/user/list.html");
        StringBuilder sb = new StringBuilder();

        BufferedReader headerReader = new BufferedReader(new FileReader("./webapp/layout/header.html"));
        String str;
        while ((str = headerReader.readLine()) != null) {
            sb.append(str);
        }
        headerReader.close();

        sb.append("<div class=\"col-md-10 col-md-offset-1\">");
        sb.append("    <div class=\"panel panel-default\">");
        sb.append("<table class=\"table table-hover\">");
        sb.append("    <thead>");
        sb.append("    <tr>");
        sb.append("        <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>");
        sb.append("    </tr>");
        sb.append("    </thead>");
        sb.append("    <tbody>");

        List<User> users = new ArrayList<>(DataBase.findUsers());
        for (User user : users) {
            sb.append("    <tr>");
            sb.append("        <td>").append(user.getUserId()).append("</td>");
            sb.append("        <td>").append(user.getPassword()).append("</td>");
            sb.append("        <td>").append(user.getName()).append("</td>");
            sb.append("        <td>").append(user.getEmail()).append("</td>");
            sb.append("    </tr>");
        }
        sb.append("             </tbody>");
        sb.append("            </table>");
        sb.append("        </div>");
        sb.append("    </div>");
        sb.append("</div>");

        BufferedReader footerReader = new BufferedReader(new FileReader("./webapp/layout/footer.html"));
        while ((str = footerReader.readLine()) != null) {
            sb.append(str);
        }
        footerReader.close();

        writer.write(sb.toString());
        writer.close();
    }
}
