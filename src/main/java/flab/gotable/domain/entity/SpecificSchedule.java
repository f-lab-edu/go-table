package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class SpecificSchedule {
    private Long id;
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Long splitTime;

    public SpecificSchedule(LocalDate date, LocalTime openTime, LocalTime closeTime, Long splitTime) {
        this.date = date;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.splitTime = splitTime;
    }
}
