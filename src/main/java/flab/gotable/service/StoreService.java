package flab.gotable.service;

import flab.gotable.domain.entity.*;
import flab.gotable.dto.response.StoreDetailsResponseDto;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.StoreNotFoundException;
import flab.gotable.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;

    @Transactional
    public boolean existById(Long id) {
        return storeMapper.findStoreById(id) != null;
    }

    @Transactional
    public StoreDetailsResponseDto getStoreDetail(Long id) {
        // 식당 기본 정보 조회
        final Store store = Optional.ofNullable(storeMapper.findStoreById(id)).orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND, ErrorCode.STORE_NOT_FOUND.getMessage()));

        // 영업 스케줄 정보 조회
        final List<DailySchedule> dailySchedules = storeMapper.findDailyScheduleByStoreId(id);
        final List<SpecificSchedule> specificSchedules = storeMapper.findSpecificScheduleByStoreId(id);

        // 예약 가능 날짜
        Map<String, DayInfo> availableDays = new HashMap<>();
        int daysAdded = 0;

        for (int i = 0; daysAdded < store.getMaxAvailableDay(); i++) {
            LocalDate targetDate = LocalDate.now().plusDays(i);

            // 해당 날짜의 영업 스케줄 결정
            final WorkSchedule workSchedule = getWorkSchedule(targetDate, specificSchedules, dailySchedules);
            final String splitTime = getSplitTime(targetDate, specificSchedules, dailySchedules);

            if (workSchedule != null && splitTime != null) {
                daysAdded++;
                // 선택 가능 시간 계산
                List<String> selectableTimes = calculateSelectableTimes(workSchedule, splitTime);

                // 사용 가능 날짜 추가
                DayInfo dayInfo = new DayInfo(workSchedule, selectableTimes);
                availableDays.put(targetDate.toString(), dayInfo);
            }
        }

        String openSchedule = calculateOpenSchedule(dailySchedules);

        return new StoreDetailsResponseDto(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getMaxMemberCount(),
                store.getMaxAvailableDay(),
                openSchedule,
                availableDays
        );
    }

    private WorkSchedule getWorkSchedule(LocalDate targetDate, List<SpecificSchedule> specificSchedules, List<DailySchedule> dailySchedules) {
        final SpecificSchedule specificSchedule = findSpecificSchedule(targetDate, specificSchedules);

        if (specificSchedule != null) {
            return new WorkSchedule(specificSchedule.getOpenTime(), specificSchedule.getCloseTime());
        } else {
            final DailySchedule dailySchedule =findDailySchedule(targetDate, dailySchedules);

            if (dailySchedule != null) {
                return new WorkSchedule(dailySchedule.getOpenTime(), dailySchedule.getCloseTime());
            } else {
                return null;
            }
        }
    }

    private String getSplitTime(LocalDate targetDate, List<SpecificSchedule> specificSchedules, List<DailySchedule> dailySchedules) {
        final SpecificSchedule specificSchedule = findSpecificSchedule(targetDate, specificSchedules);

        if (specificSchedule != null) {
            return specificSchedule.getSplitTime();
        } else {
            final DailySchedule dailySchedule =findDailySchedule(targetDate, dailySchedules);

            if (dailySchedule != null) {
                return dailySchedule.getSplitTime();
            } else {
                return null;
            }
        }
    }

    @Nullable
    private SpecificSchedule findSpecificSchedule(LocalDate targetDate, List<SpecificSchedule> specificSchedules) {
        return specificSchedules.stream()
                .filter(s -> s.getDate().equals(targetDate))
                .findFirst()
                .orElse(null);
    }

    private DailySchedule findDailySchedule(LocalDate targetDate, List<DailySchedule> dailySchedules) {
        return dailySchedules.stream()
                .filter(d -> d.getDay().equalsIgnoreCase(targetDate.getDayOfWeek().name()))
                .findFirst()
                .orElse(null);
    }

    private String calculateOpenSchedule(List<DailySchedule> dailySchedules) {
        StringBuilder openSchedule = new StringBuilder();

        for (DailySchedule schedule : dailySchedules) {
            openSchedule.append(String.format("%s %s ~ %s, ", schedule.getDay(), schedule.getOpenTime(), schedule.getCloseTime()));
        }

        if (openSchedule.length() > 0) {
            openSchedule.setLength(openSchedule.length() - 2);
        }

        return openSchedule.toString();
    }

    private List<String> calculateSelectableTimes(WorkSchedule workSchedule, String splitTime) {
        List<String> times = new ArrayList<>();
        LocalTime openTime = workSchedule.getOpenTime();
        LocalTime closeTime = workSchedule.getCloseTime();

        int interval = Integer.parseInt(splitTime);

        while (openTime.isBefore(closeTime)) {
            times.add(openTime.toString());
            openTime = openTime.plusMinutes(interval);
        }

        return times;
    }
}
