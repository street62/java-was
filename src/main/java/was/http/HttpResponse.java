package was.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.util.HttpRequestUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class HttpResponse {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String responseHeader;
    private byte[] responseBody;
    private HttpRequestUtils.Pair cookie;
    private ContentsType contentsType;



    public HttpResponse(String path, int statusCode) throws IOException {
        this(path, statusCode, null);
    }

    public HttpResponse(String path, int statusCode, HttpRequestUtils.Pair cookie) throws IOException {
        this.cookie = cookie;
        String[] splitedPath = path.split("\\.");
        this.contentsType = ContentsType.from(splitedPath[splitedPath.length - 1]);
        if (statusCode == 200) {
            create200ResponseMessage(path);
        } else if (statusCode == 302) {
            create302ResponseMessage(path);
        }
    }

    private byte[] createResponseBody(String path) throws IOException {
        if (path.equals("/")) {
            return "Hello World".getBytes(StandardCharsets.UTF_8);
        }
        return Files.readAllBytes(new File("./webapp/" + path).toPath());
    }

    private void create200ResponseMessage(String path) throws IOException {
        responseBody = createResponseBody(path);
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append("Content-Type: ").append(contentsType.getMime()).append(";charset=utf-8\r\n");
        sb.append("Content-Length: " + responseBody.length + "\r\n");
        if (!Objects.isNull(cookie)) {
            sb.append("Set-Cookie: ").append(cookie.getKey()).append("=").append(cookie.getValue()).append("; Path=/\r\n");
        }
        sb.append("\r\n");
        responseHeader = sb.toString();
    }

    private void create302ResponseMessage(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 302 Found \r\n");
        sb.append("Location: http://localhost:8080/").append(path).append(" \r\n");
        responseHeader = sb.toString();
    }


    public void writeResponseMessage(OutputStream out) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(out);

        outputStream.writeBytes(responseHeader);
        if (!Objects.isNull(responseBody))
            outputStream.write(responseBody);
        outputStream.flush();
        outputStream.close();

        log.debug("responseHeader: {}", responseHeader);
    }
}
