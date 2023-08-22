package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.config.ChatGptConfig;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptResponseDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.CodeVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.VerificationRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.CodeVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.UuidVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.VerificationRequestForm;
import FitMate.FitMateBackend.consts.ServiceConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/register/verify")
public class MailVerificationController {

    private static final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/mail")
    @ResponseBody
    public String mailVerificationRequest(@RequestBody VerificationRequestForm verificationRequestForm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<VerificationRequestDto> httpEntity = new HttpEntity<>(VerificationRequestDto.createVerificationRequestDto(verificationRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/mail"), httpEntity, String.class);
        return responseEntity.getBody();
    }
    @PostMapping("/code")
    @ResponseBody
    public UuidDto codeVerificationRequest(@RequestBody CodeVerifyingRequestForm codeVerifyingRequestForm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<CodeVerifyingRequestDto> httpEntity = new HttpEntity<>(CodeVerifyingRequestDto.createCodeVerifyingRequestDto(codeVerifyingRequestForm), headers);

        ResponseEntity<UuidDto> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/code"), httpEntity, UuidDto.class);
        return responseEntity.getBody();
    }
    @PostMapping("/uuid")
    @ResponseBody
    public String uuidVerificationRequest(@RequestBody UuidVerifyingRequestForm uuidVerifyingRequestForm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<UuidVerifyingRequestDto> httpEntity = new HttpEntity<>(UuidVerifyingRequestDto.createUuidVerifyingRequestDto(uuidVerifyingRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/uuid"), httpEntity, String.class);
        return responseEntity.getBody();
    }
}
