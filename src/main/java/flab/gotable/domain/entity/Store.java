package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Store {
    private Long id;
    private String name;
    private String address;
    private long maxMemberCount;
    private long maxAvailableDay;
    private String openSchedule;
    private Map<String, DayInfo> availableDays;
}
