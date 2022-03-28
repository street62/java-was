package was.webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import was.http.HttpRequest;
import was.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import was.controller.MyController;
import was.controller.SaveUserController;
import was.util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private HandlerMapper handlerMapper = new HandlerMapper();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);

            IOUtils.printRequestHeader(request);

            MyController myController = handlerMapper.getHandler(request);
            HttpResponse httpResponse = myController.process(request);

            httpResponse.writeResponseMessage(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
