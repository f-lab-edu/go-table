package flab.gotable.service;

import flab.gotable.domain.entity.DailySchedule;
import flab.gotable.domain.entity.SpecificSchedule;
import flab.gotable.domain.entity.Store;
import flab.gotable.domain.entity.WorkSchedule;
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
    public boolean getStoreById(Long id) {
        return storeMapper.findStoreById(id) != null;
    }

    @Transactional
    public Map<String, Object> getStoreDetail(Long id) {
        // 식당 기본 정보 조회
        Store store = storeMapper.findStoreById(id);

        // 영업 스케줄 정보 조회
        List<DailySchedule> dailySchedules = storeMapper.findDailyScheduleByStoreId(id);
        List<SpecificSchedule> specificSchedules = storeMapper.findSpecificScheduleByStoreId(id);

        // 예약 가능 날짜
        Map<String, Object> availableDays = new LinkedHashMap<>();
        int daysAdded = 0;

        for (int i = 0; daysAdded < store.getMaxAvailableDay(); i++) {
            LocalDate targetDate = LocalDate.now().plusDays(i);

            // 특수 영업 스케줄 확인
            SpecificSchedule specificSchedule = specificSchedules.stream()
                    .filter(s -> s.getDate().equals(targetDate))
                    .findFirst()
                    .orElse(null);

            // 해당 날짜의 영업 스케줄 결정
            WorkSchedule workSchedule;
            String splitTime;
            if (specificSchedule != null) {
                workSchedule = new WorkSchedule(specificSchedule.getOpenTime(), specificSchedule.getCloseTime());
                splitTime = specificSchedule.getSplitTime();
                daysAdded++;
            } else {
                DailySchedule dailySchedule = dailySchedules.stream()
                        .filter(d -> d.getDay().equalsIgnoreCase(targetDate.getDayOfWeek().name()))
                        .findFirst()
                        .orElse(null);

                if (dailySchedule != null) {
                    workSchedule = new WorkSchedule(dailySchedule.getOpenTime(), dailySchedule.getCloseTime());
                    splitTime = dailySchedule.getSplitTime();
                    daysAdded++;
                } else {
                    continue; // 해당 요일에 일반 영업 스케줄이 없으면 다음 날로 넘어감
                }
            }

            // 선택 가능 시간 계산
            List<String> selectableTimes = calculateSelectableTimes(workSchedule, splitTime);

            // 사용 가능 날짜 추가
            Map<String, Object> dayInfo = new HashMap<>();
            dayInfo.put("workSchedule", workSchedule);
            dayInfo.put("selectableTimes", selectableTimes);

            availableDays.put(targetDate.toString(), dayInfo);
        }

        store.setAvailableDays(availableDays);

        Map<String, Object> result = new HashMap<>();
        result.put("store", store);

        return result;
    }

    private List<String> calculateSelectableTimes(WorkSchedule workSchedule, String splitTime) {
        List<String> times = new ArrayList<>();
        LocalTime openTime = LocalTime.parse(workSchedule.getOpen());
        LocalTime closeTime = LocalTime.parse(workSchedule.getClose());

        int interval = Integer.parseInt(splitTime);

        while (openTime.isBefore(closeTime)) {
            times.add(openTime.toString());
            openTime = openTime.plusMinutes(interval);
        }

        return times;
    }
}
