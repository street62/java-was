package was.model;

import java.time.LocalDate;

public class Article {
    private LocalDate createDate;
    private String userName;
    private String content;

    public Article(LocalDate createDate, String userName, String content) {
        this.createDate = createDate;
        this.userName = userName;
        this.content = content;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
