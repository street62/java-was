package was.http;

import was.util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class ParamMap {
    private Map<String, String> paramMap;

    public ParamMap(String bodyMessages) {
        paramMap = HttpRequestUtils.parseValues(bodyMessages, "&");
    }

    public String get(String key) {
        return paramMap.get(key);
    }
}
