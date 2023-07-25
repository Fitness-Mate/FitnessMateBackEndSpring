package FitMate.FitMateBackend.chanhaleWorking.form.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    private String loginEmail;

    @NotEmpty
    private String password;
}
