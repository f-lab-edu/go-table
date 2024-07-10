package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WorkSchedule {
    private String open;
    private String close;

    public WorkSchedule(String openTime, String closeTime) {
        this.open = openTime;
        this.close = closeTime;
    }
}
