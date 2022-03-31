package was.controller;

import was.db.DataBase;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.model.Article;
import was.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleListController implements MyController {

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        writeListHtml();
        return new HttpResponse("test-index.html", 200);
    }

    private void writeListHtml() throws IOException {
        PrintWriter writer = new PrintWriter("./webapp/test-index.html");
        StringBuilder sb = new StringBuilder();

        BufferedReader headerReader = new BufferedReader(new FileReader("./webapp/layout/header.html"));
        String str;
        while ((str = headerReader.readLine()) != null) {
            sb.append(str);
        }
        headerReader.close();

        sb.append("<div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">");
        sb.append("  <div class=\"panel panel-default qna-list\">");
        sb.append("      <ul class=\"list\">");

        List<Article> articles = DataBase.findArticles();
        for (Article article : articles) {
            sb.append("<li>");
            sb.append("  <div class=\"wrap\">");
            sb.append("      <div class=\"main\">");
            sb.append("          <strong class=\"subject\">");
            sb.append("              <a href=\"/qna/show.html\">").append(article.getContent()).append("</a>");
            sb.append("          </strong>");
            sb.append("          <div class=\"auth-info\">");
            sb.append("              <i class=\"icon-add-comment\"></i>");
            sb.append("              <span class=\"time\">").append(article.getCreateDate()).append("</span>");
            sb.append("              <a href=\"/user/profile.html\" class=\"author\">").append(article.getUserName()).append("</a>");
            sb.append("          </div>");
            sb.append("          <div class=\"reply\" title=\"댓글\">");
            sb.append("              <i class=\"icon-reply\"></i>");
            sb.append("              <span class=\"point\">8</span>");
            sb.append("          </div>");
            sb.append("      </div>");
            sb.append("  </div>");
            sb.append("</li>");
        }

        sb.append("</ul>");
        sb.append("<div class=\"row\">");
        sb.append("        <div class=\"col-md-3\"></div>");
        sb.append("        <div class=\"col-md-6 text-center\">");
        sb.append("        <ul class=\"pagination center-block\" style=\"display:inline-block;\">");
        sb.append("        <li><a href=\"#\">«</a></li>");
        sb.append("        <li><a href=\"#\">1</a></li>");
        sb.append("        <li><a href=\"#\">2</a></li>");
        sb.append("        <li><a href=\"#\">3</a></li>");
        sb.append("        <li><a href=\"#\">4</a></li>");
        sb.append("        <li><a href=\"#\">5</a></li>");
        sb.append("        <li><a href=\"#\">»</a></li>");
        sb.append("        </ul>");
        sb.append("    </div>");
        sb.append("    <div class=\"col-md-3 qna-write\">");
        sb.append("        <a href=\"/qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>");
        sb.append("        </div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
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
