package com.practice_application.practice_application.utility;


import com.practice_application.practice_application.auth.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class Utility {

    @Autowired
    JwtTokenUtil tokenUtil;


    public void validateField(Consumer<String> setter, String value, String fieldName) {
        if (value == null || value.isEmpty() || "string".equalsIgnoreCase(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be null, empty, or 'string'");
        }
        setter.accept(value);
    }

    public JSONObject authentication(String token) {

        JSONObject response = new JSONObject();

        try {
            log.info("Authentication token is :{}", token);

            if (token == null || token.isEmpty()) {
                response.put("message", "Authentication token is required!");
                response.put("success", false);
                return response;
            }

            boolean validate = tokenUtil.isTokenExpired(token);
            if (validate) {
                response.put("message", "Session is expired!");
                response.put("success", false);
                return response;
            }

            boolean isTokenValid = tokenUtil.isTokenValid(token);
            if (!isTokenValid) {
                response.put("message", "Token is invalid!");
                response.put("success", false);
                return response;
            }

        } catch (Exception e) {
            String errorMsg = e.getMessage();

            response.put("message", errorMsg.contains("expired") ? "Session is Expired!" :
                    errorMsg.contains("signature") ? "Token is invalid!" :
                            "Authentication token is required!");
            response.put("success", false);
            return response;
        }

        response.put("message", "success");
        response.put("success", true);
        return response;
    }


}