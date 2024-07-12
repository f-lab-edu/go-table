package flab.gotable.dto.response;

import flab.gotable.domain.entity.DayInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class StoreDetailsResponseDto {
    private Long id;
    private String name;
    private String address;
    private long maxMemberCount;
    private long maxAvailableDay;
    private String openSchedule;
    private Map<String, DayInfo> availableDays;

    public StoreDetailsResponseDto(Long id, String name, String address, long maxMemberCount, long maxAvailableDay, String openSchedule, Map<String, DayInfo> availableDays) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxMemberCount = maxMemberCount;
        this.maxAvailableDay = maxAvailableDay;
        this.openSchedule = openSchedule;
        this.availableDays = availableDays;
    }
}
