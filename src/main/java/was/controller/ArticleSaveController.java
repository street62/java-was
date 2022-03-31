package was.controller;

import was.db.DataBase;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.ParamMap;
import was.model.Article;

import java.io.IOException;

public class ArticleSaveController implements MyController {

    @Override
    public HttpResponse process(HttpRequest request) throws IOException {
        ParamMap paramMap = request.getParamMap();
        String writer = paramMap.get("writer");
        String content = paramMap.get("content");

        Article article = new Article(writer, content);
        DataBase.addArticle(article);

        return new HttpResponse("", 302);
    }
}
