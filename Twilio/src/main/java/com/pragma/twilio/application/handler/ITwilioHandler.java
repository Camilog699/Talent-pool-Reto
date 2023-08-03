package com.pragma.twilio.application.handler;


import com.pragma.twilio.application.dto.request.TwilioRequestDto;

public interface ITwilioHandler {
    void sendMessage(TwilioRequestDto twilioRequestDto);
}