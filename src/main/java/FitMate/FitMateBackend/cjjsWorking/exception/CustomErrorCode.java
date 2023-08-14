package FitMate.FitMateBackend.cjjsWorking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    BODY_PART_ALREADY_EXIST("이미 존재하는 운동 부위입니다. 이름을 확인해주세요.");

    private final String statusMessage;
}
