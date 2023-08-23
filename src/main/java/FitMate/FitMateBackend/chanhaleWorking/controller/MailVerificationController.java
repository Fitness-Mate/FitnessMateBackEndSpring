package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.config.ChatGptConfig;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.ChatGptResponseDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.*;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.CodeVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.UuidVerifyingRequestForm;
import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.VerificationRequestForm;
import FitMate.FitMateBackend.consts.ServiceConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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
    public String mailVerificationRequest(@RequestBody VerificationRequestForm verificationRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<VerificationRequestDto> httpEntity = new HttpEntity<>(VerificationRequestDto.createVerificationRequestDto(verificationRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/mail"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /verify/mail request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    @PostMapping("/code")
    @ResponseBody
    public UuidDto codeVerificationRequest(@RequestBody CodeVerifyingRequestForm codeVerifyingRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<CodeVerifyingRequestDto> httpEntity = new HttpEntity<>(CodeVerifyingRequestDto.createCodeVerifyingRequestDto(codeVerifyingRequestForm), headers);

        ResponseEntity<UuidDto> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/code"), httpEntity, UuidDto.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /verify/code request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    @PostMapping("/uuid")
    @ResponseBody
    public String uuidVerificationRequest(@RequestBody UuidVerifyingRequestForm uuidVerifyingRequestForm, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<UuidVerifyingRequestDto> httpEntity = new HttpEntity<>(UuidVerifyingRequestDto.createUuidVerifyingRequestDto(uuidVerifyingRequestForm), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/verify/uuid"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            response.setStatus(400);
            log.info("error status code responded for /verify/uuid request [{}]",responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    @PostMapping("/purge")
    @ResponseBody
    public String mailServerPurgeRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<PurgeRequestDto> httpEntity = new HttpEntity<>(new PurgeRequestDto(), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/purge"), httpEntity, String.class);
        log.info("purge mail server request");
        return responseEntity.getBody();
    }
}
