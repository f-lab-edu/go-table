package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class SpecificSchedule {
    private Long id;
    private LocalDate date;
    private String openTime;
    private String closeTime;
    private String splitTime;

    public SpecificSchedule(LocalDate date, String openTime, String closeTime, String splitTime) {
        this.date = date;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.splitTime = splitTime;
    }
}
