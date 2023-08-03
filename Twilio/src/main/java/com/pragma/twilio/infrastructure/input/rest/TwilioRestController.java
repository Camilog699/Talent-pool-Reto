package com.pragma.twilio.infrastructure.input.rest;
import com.pragma.twilio.application.dto.request.TwilioRequestDto;
import com.pragma.twilio.application.dto.response.ResponseDto;
import com.pragma.twilio.application.handler.ITwilioHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/twilio")
@RequiredArgsConstructor
public class TwilioRestController {
    private final ITwilioHandler twilioHandler;

    @PostMapping("/")
    public ResponseEntity<ResponseDto> sendMessage(@Valid @RequestBody TwilioRequestDto twilioRequestDto, BindingResult bindingResult){
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            responseDto.setError(true);
            responseDto.setMessage("Validations errors");
            responseDto.setData(errors);

            return ResponseEntity.badRequest().body(responseDto);
        }

        try {
            twilioHandler.sendMessage(twilioRequestDto);

            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData("Message sent successfully");
        }catch (Exception e){
            responseDto.setError(true);
            responseDto.setMessage(e.getMessage());
            responseDto.setData(null);
        }

        return ResponseEntity.ok(responseDto);
    }

}
