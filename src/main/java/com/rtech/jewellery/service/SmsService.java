package com.rtech.jewellery.service;

import com.google.api.client.util.Value;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.twilioNumber}")
    private String twilioNumber;

    public void init() {
        Twilio.init(accountSid,authToken);
    }
    public void sendSms(String toPhoneNumber,String messageBody){
        try {
            init();
            Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(twilioNumber),
                            messageBody)
                    .create();
        }catch(ApiException e){
            throw new RuntimeException("Sms sending failed: "+e.getMessage(),e);
        }
    }
}
