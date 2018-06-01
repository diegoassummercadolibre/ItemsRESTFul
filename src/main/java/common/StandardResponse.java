package common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class StandardResponse {

    private StatusResponse status;
    private String message;
    private JsonElement data;

    public StandardResponse(StatusResponse status) {
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, Object data) {
        this.status = status;
        this.data = new Gson().toJsonTree(data);;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
