package FitMate.FitMateBackend.chanhaleWorking.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testwrite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestWriteDomain {
    @Id
    @Column(name = "testwrite_id")
    private Long id;

    private String str;

}
