package FitMate.FitMateBackend.user.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Data
public class UpdateUserForm {
    private String userName;
    private LocalDate birthDate;
}
