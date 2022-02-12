package factoryRequest;

import java.util.Map;

public class RequestInfo {
    private String url;
    private String body;
    private Map<String,String> headers;

    public RequestInfo(){}

    public String getUrl() {
        return url;
    }

    public RequestInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RequestInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
