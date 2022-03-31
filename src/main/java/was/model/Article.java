package was.model;

import java.time.LocalDate;

public class Article {
    private LocalDate createDate = LocalDate.now();
    private String writer;
    private String content;

    public Article(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }
}
