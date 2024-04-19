package org.ispp4.cohabify.APIs;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import org.springframework.beans.factory.annotation.Value;

public class SmsVerificationTwilio {
    
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.service.sid}")
    private String SERVICE_SID;

    public Service createService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return Service.creator("Cohabify").create();
    }

    public void startVerification(String phoneNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                SERVICE_SID,
                "+34"+phoneNumber,
                "sms")
            .create();
    }

    public String checkVerification(String phoneNumber, String code){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verification = VerificationCheck.creator(
                SERVICE_SID)
                .setTo("+34"+phoneNumber)
                .setCode(code)
            .create();
        return verification.getStatus();

    }
}
