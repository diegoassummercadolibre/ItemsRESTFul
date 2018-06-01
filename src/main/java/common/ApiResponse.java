package common;

import com.google.gson.Gson;

public class ApiResponse {

    public static String getResponse(StatusResponse status, Object response) {
        return new Gson().toJson(new StandardResponse(status, response));
    }
}
