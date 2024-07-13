package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class DailySchedule {
    private Long id;
    private DayOfWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Long splitTime;

    public DailySchedule(DayOfWeek day, LocalTime openTime, LocalTime closeTime, Long splitTime) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.splitTime = splitTime;
    }
}
