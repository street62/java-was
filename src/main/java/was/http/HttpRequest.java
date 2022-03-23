package was.http;

import was.util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import was.util.IOUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {

    private List<HttpRequestUtils.Pair> pairs = new ArrayList<>();
    private String bodyMessages;
    private String path;
    private String method;
    private Map<String, String> paramMap;

    public HttpRequest(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public HttpRequest(BufferedReader bf) throws IOException {
        String firstLine = bf.readLine();
        this.path = HttpRequestUtils.parsePath(firstLine);
        this.method = HttpRequestUtils.parseMethod(firstLine);

        String line = bf.readLine();
        while (!(line == null) || line.equals("")) {
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            pairs.add(pair);
            line = bf.readLine();
        }

        String stringContentLength = pairs.stream()
                .filter(pair -> pair.getKey().equals("Content-Length"))
                .findFirst()
                .orElse(null)
                .getValue();

        int contentLength = Integer.parseInt(stringContentLength);
        bodyMessages = IOUtils.readData(bf, contentLength);

        this.paramMap = HttpRequestUtils.parseValues(bodyMessages, "&");
    }

    public List<HttpRequestUtils.Pair> getPairs() {
        return pairs;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(path, that.path) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
