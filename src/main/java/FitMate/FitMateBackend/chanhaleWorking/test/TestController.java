package FitMate.FitMateBackend.chanhaleWorking.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
@ResponseBody
public class TestController {
    private final TestService testService;

    @GetMapping("/read")
    public String testRead() {
        return testService.getTestWriteDomain().getStr();
    }

    @PostMapping("/write")
    public String testWrite() {
        return testService.writeTWD();
    }

}
