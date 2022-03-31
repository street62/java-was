package was.http;

import java.util.Arrays;

public enum ContentsType {
    PLAIN("txt", "text/plain"),
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    JS("js", "application/js"),
    ICO("ico", "image/x-icon"),
    OTHER(null, "application/octet-stream");

    private String extension;
    private String mime;

    private ContentsType(String extension, String mime) {
        this.extension = extension;
        this.mime = mime;
    }

    public static ContentsType from(String extension) {
        return Arrays.stream(values())
                .filter(type -> extension.equals(type.extension))
                .findAny()
                .orElse(OTHER);
    }

    public String getMime() {
        return mime;
    }

}
