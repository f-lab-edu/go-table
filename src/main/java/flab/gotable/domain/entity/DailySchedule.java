package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DailySchedule {
    private Long id;
    private String day;
    private String openTime;
    private String closeTime;
    private String splitTime;
}