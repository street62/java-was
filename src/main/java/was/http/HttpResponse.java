package was.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HttpResponse {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String responseHeader = "";
    private byte[] responseBody;
    private DataOutputStream outputStream;

    public HttpResponse(String path, OutputStream out) throws IOException {
        outputStream = new DataOutputStream(out);
        responseBody = createResponseBody(path);
        response200Header(responseBody.length);
    }

    private byte[] createResponseBody(String path) throws IOException {
        if (path.equals("/")) {
            return "Hello World".getBytes(StandardCharsets.UTF_8);
        }
        return Files.readAllBytes(new File("./webapp/" + path).toPath());
    }

    private void response200Header(int lengthOfBodyContent) {
        responseHeader += ("HTTP/1.1 200 OK \r\n");
        responseHeader += ("Content-Type: text/html;charset=utf-8\r\n");
        responseHeader += ("Content-Length: " + lengthOfBodyContent + "\r\n");
        responseHeader += ("\r\n");
    }

    public void writeResponseMessage() throws IOException {
        outputStream.writeBytes(responseHeader);
        outputStream.write(responseBody);
        outputStream.flush();
    }
}
