package was.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public HttpResponse(String path, int statusCode) throws IOException {
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
        sb.append("Content-Type: text/html;charset=utf-8\r\n");
        sb.append("Content-Length: " + responseBody.length + "\r\n");
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
    }
}
