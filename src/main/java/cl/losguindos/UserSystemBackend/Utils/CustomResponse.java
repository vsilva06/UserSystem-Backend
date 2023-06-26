package cl.losguindos.UserSystemBackend.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CustomResponse {
    public static String generateResponse(String message, String status) {
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("Code", status);
            jsonResponse.put("Message", message);
            return jsonResponse.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Internal server error");
        }
    }

    public static String tokenResponse(String token) throws JSONException {
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("token", "Bearer "+token);
            return jsonResponse.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Internal server error");
        }
    }
}