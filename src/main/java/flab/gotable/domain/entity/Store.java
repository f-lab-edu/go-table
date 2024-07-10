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
    private int maxMemberCount;
    private int maxAvailableDay;
    private String openSchedule;
    private Map<String, Object> availableDays;
}
