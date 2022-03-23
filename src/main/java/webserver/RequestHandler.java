package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MyController;
import controller.SaveUserController;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Map<HttpRequest, MyController> handlerMapper = new HashMap<>();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initHandlerMapper();
    }

    private void initHandlerMapper() {
        handlerMapper.put(new HttpRequest("/user/create", "POST"), new SaveUserController());
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = new HttpRequest(bf);

            IOUtils.printRequestHeader(httpRequest);

            String path = httpRequest.getPath();
            String method = httpRequest.getMethod();

            HttpRequest request = new HttpRequest(path, method);

            if (handlerMapper.containsKey(request)) {
                MyController myController = handlerMapper.get(request);

                Map<String, String> paramMap = httpRequest.getParamMap();
                path = myController.process(paramMap);
            }

            HttpResponse httpResponse = new HttpResponse(path, dos);
            dos.writeBytes(httpResponse.getResponseHeader());
            dos.write(httpResponse.getResponseBody());
            dos.flush();

            System.out.println(httpResponse.getResponseHeader());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
