package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.domain.BodyData;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * history 그래프 조회 또는 recommendation 화면에 노출 될 bodyData
 *
 */
@Getter
@Data
@NoArgsConstructor
public class BodyDataDto {

    private Long bodyDataId;
    private LocalDate date;
    private Float height;
    private Float weight;
    private Float upperBodyFat;
    private Float lowerBodyFat;
    private Float upperMuscleMass;
    private Float lowerMuscleMass;

    public static BodyDataDto createBodyDataDto(BodyData bodyData) {
        BodyDataDto bodyDataDto = new BodyDataDto();
        if (bodyData != null) {
            bodyDataDto.bodyDataId = bodyData.getId();
            bodyDataDto.date = bodyData.getDate();
            bodyDataDto.height = bodyData.getHeight();
            bodyDataDto.weight = bodyData.getWeight();
            bodyDataDto.upperBodyFat = bodyData.getUpperBodyFat();
            bodyDataDto.lowerBodyFat = bodyData.getLowerBodyFat();
            bodyDataDto.upperMuscleMass = bodyData.getUpperMuscleMass();
            bodyDataDto.lowerMuscleMass = bodyData.getLowerMuscleMass();
        }
        return bodyDataDto;
    }
}
