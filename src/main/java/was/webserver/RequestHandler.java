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

    private HandlerMapper handlerMapper;

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initHandlerMapper();
    }

    private void initHandlerMapper() {
        handlerMapper = new HandlerMapper();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);

            IOUtils.printRequestHeader(request);

            int statusCode = 200;
            String path = request.getPath();
            MyController myController = handlerMapper.getHandler(request);
            if (!Objects.isNull(myController)) {
                statusCode = 302;
                Map<String, String> paramMap = request.getParamMap();
                path = myController.process(paramMap);
            }

            HttpResponse httpResponse = new HttpResponse(path, out, statusCode);
            httpResponse.writeResponseMessage();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
