package FitMate.FitMateBackend.cjjsWorking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    BODY_PART_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동 부위입니다. 이름을 확인해주세요."),
    BODY_PART_NOT_FOUND_EXCEPTION("존재하지 않는 운동 부위입니다."),

    MACHINE_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동기구입니다. 이름을 확인해주세요."),
    MACHINE_NOT_FOUND_EXCEPTION("존재하지 않는 운동기구입니다."),

    PAGE_NOT_FOUND_EXCEPTION("존재하지 않는 페이지입니다.");

    private final String statusMessage;
}
