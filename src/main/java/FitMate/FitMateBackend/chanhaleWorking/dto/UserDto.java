package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 요청에 대한 dto
 * 개인정보 수정에서 활용
 */
@NoArgsConstructor
@Getter
public class UserDto {
    private String userName;
    private String loginId;
    private String Sex;

    //    private Float height;
//    private Float weight;
    public static UserDto createUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.userName = user.getUserName();
        userDto.loginId = user.getLoginId();
        userDto.Sex = user.getSex();
        return userDto;
    }
}
