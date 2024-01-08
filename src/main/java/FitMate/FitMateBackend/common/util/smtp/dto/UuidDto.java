package FitMate.FitMateBackend.common.util.smtp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UuidDto {
    private String status;
    private String mailAddress;
    private String uuid;

}
