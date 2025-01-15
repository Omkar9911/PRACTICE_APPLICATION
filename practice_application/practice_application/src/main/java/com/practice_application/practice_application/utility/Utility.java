package com.practice_application.practice_application.utility;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class Utility {

    public void validateField(Consumer<String> setter, String value, String fieldName) {
        if (value == null || value.isEmpty() || "string".equalsIgnoreCase(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be null, empty, or 'string'");
        }
        setter.accept(value);
    }
}
