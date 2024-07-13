package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DayInfo {
    private WorkSchedule workSchedule;
    private List<String> selectableTimes;

    public DayInfo(WorkSchedule workSchedule, List<String> selectableTimes) {
        this.workSchedule = workSchedule;
        this.selectableTimes = selectableTimes;
    }
}
