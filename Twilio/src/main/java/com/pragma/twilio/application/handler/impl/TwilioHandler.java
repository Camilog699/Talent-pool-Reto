package com.pragma.twilio.application.handler.impl;

import com.pragma.twilio.application.dto.request.TwilioRequestDto;
import com.pragma.twilio.application.handler.ITwilioHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TwilioHandler implements ITwilioHandler {

    public static final String ACCOUNT_SID = "AC9165f7676049efeaf94b2cad88df0e83";
    public static final String AUTH_TOKEN = "8c1050326fcfba70846f94c219869c72";
    @Override
    public void sendMessage(TwilioRequestDto twilioRequestDto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(twilioRequestDto.getNumber()),
                        new com.twilio.type.PhoneNumber("+17622512833"),
                        twilioRequestDto.getMessage())
                .create();
    }
}