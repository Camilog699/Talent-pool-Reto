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
    public static final String AUTH_TOKEN = "a3ff969c01fe3dbd3c3fd7e14140439d";
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