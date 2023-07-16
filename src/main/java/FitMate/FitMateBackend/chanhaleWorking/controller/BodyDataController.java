package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver.Login;
import FitMate.FitMateBackend.chanhaleWorking.dto.BodyDataDto;
import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import FitMate.FitMateBackend.chanhaleWorking.service.BodyDataService;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/bodyData")
public class BodyDataController {
    private final BodyDataService bodyDataService;

    @PostMapping
    public String createBodyData(@Login User loginUser, @RequestBody BodyDataForm form) {
        if (form == null || loginUser == null) {
            return "잘못된 입력";
        }
        String errMsg = form.validateFields();
        if (!errMsg.equals("ok")) {
            return errMsg;
        }
        return bodyDataService.createBodyData(loginUser.getId(), form);
    }

    @GetMapping("/{bodyDataId}")
    public BodyDataDto getBodyData(@Login User user,@PathVariable("bodyDataId") Long bodyDataId) {
        BodyData bodyData = bodyDataService.getBodyData(bodyDataId);
        if (bodyData == null ||!Objects.equals(bodyData.getUser().getId(), user.getId())) {
            return new BodyDataDto();
        }
        return BodyDataDto.createBodyDataDto(bodyData);
    }

    @GetMapping("/list/{pageNum}")
    public List<BodyDataDto> getBodyDataBatch(@Login User user, @PathVariable("pageNum") Long pageNum) {
        List<BodyDataDto> result = new ArrayList<>();
        if (user == null || pageNum < 0) {
            return result;
        }
        List<BodyData> bodyDataList = bodyDataService.getBodyDataBatch(user.getId(), pageNum);
        for (BodyData bodyData : bodyDataList) {
            result.add(BodyDataDto.createBodyDataDto(bodyData));
        }
        return result;
    }

    @GetMapping("/recent")
    public BodyDataDto getRecentData(@Login User user) {

        return BodyDataDto.createBodyDataDto(bodyDataService.getRecentBodyData(user.getId()));
    }

    @PostMapping("/delete/{bodyDataId}")
    public String deleteBodyData(@Login User user,@PathVariable("bodyDataId") Long bodyDataId) {
        BodyData bodyData = bodyDataService.getBodyData(bodyDataId);
        if (bodyData == null ||!Objects.equals(bodyData.getUser().getId(), user.getId())) {
            return "fail";
        }
        bodyDataService.deleteBodyData(bodyDataId);
        return "ok";
    }
}
