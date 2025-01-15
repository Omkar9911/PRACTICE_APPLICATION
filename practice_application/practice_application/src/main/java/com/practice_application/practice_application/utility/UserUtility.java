package com.practice_application.practice_application.utility;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Random;

@Component
@Slf4j
public class UserUtility {


    public String generateOTP() {
        return  new DecimalFormat("000000").format((new Random().nextInt(999999)));
    }
}
