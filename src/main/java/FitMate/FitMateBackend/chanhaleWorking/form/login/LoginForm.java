package FitMate.FitMateBackend.chanhaleWorking.form.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    @NotEmpty
    private String loginEmail;

    @NotEmpty
    private String password;

    private boolean rememberMe;
}