package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class DailySchedule {
    private Long id;
    private String day;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String splitTime;

    public DailySchedule(String day, LocalTime openTime, LocalTime closeTime, String splitTime) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.splitTime = splitTime;
    }
}
