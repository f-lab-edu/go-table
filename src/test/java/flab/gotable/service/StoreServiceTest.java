package flab.gotable.service;

import flab.gotable.domain.entity.DailySchedule;
import flab.gotable.domain.entity.SpecificSchedule;
import flab.gotable.domain.entity.Store;
import flab.gotable.mapper.StoreMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StoreServiceTest {
    private StoreService storeService;

    @BeforeEach
    void setup() {
        storeService = new StoreService(new StoreMapper() {
            @Override
            public Store findStoreById(Long id) {
                if (id == 1L) {
                    Store store = new Store();
                    store.setId(1L);
                    store.setName("차알 엘지아트센터 서울점");
                    store.setAddress("서울 강서구 마곡중앙로 136 지하1층");
                    store.setMaxMemberCount(8);
                    store.setMaxAvailableDay(7);
                    return store;
                }
                return null;
            }

            @Override
            public List<DailySchedule> findDailyScheduleByStoreId(Long id) {
                if (id == 1L) {
                    return Arrays.asList(
                            new DailySchedule("MONDAY", "09:00", "18:00", "60"),
                            new DailySchedule("TUESDAY", "09:00", "18:00", "60")
                    );
                }
                return Collections.emptyList();
            }

            @Override
            public List<SpecificSchedule> findSpecificScheduleByStoreId(Long id) {
                if (id == 1L) {
                    return Arrays.asList(
                            new SpecificSchedule(LocalDate.of(2024, 7, 12), "10:00", "15:00", "60")
                    );
                }
                return Collections.emptyList();
            }
        });
    }

    @Test
    @DisplayName("해당 id를 갖고 있는 식당이 존재하는 경우 TRUE를 반환한다.")
    void isExistStore() {
        // given
        Long storeId = 1L;

        // when
        boolean result = storeService.getStoreById(storeId);

        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("해당 id를 갖고 있는 식당이 존재하지 않을 경우 FALSE를 반환한다.")
    void isNotExistStore() {
        // given
        Long storeId = 2L;

        // when
        boolean result = storeService.getStoreById(storeId);

        // then
        Assertions.assertFalse(result);
    }


    @Test
    @DisplayName("식당 id로 식당 상세 정보 조회에 성공한다.")
    void getStoreDetailSuccess() {
        // given
        Long storeId = 1L;

        // when
        Map<String, Object> result = storeService.getStoreDetail(storeId);

        // then
        Assertions.assertNotNull(result);

        Store store = (Store) result.get("store");
        Assertions.assertNotNull(store);
        Assertions.assertEquals(storeId, store.getId());
        Assertions.assertEquals("차알 엘지아트센터 서울점", store.getName());
        Assertions.assertEquals("서울 강서구 마곡중앙로 136 지하1층", store.getAddress());
        Assertions.assertEquals(8, store.getMaxMemberCount());
        Assertions.assertEquals(7, store.getMaxAvailableDay());

        String expectedOpenSchedule = "MONDAY 09:00 ~ 18:00, TUESDAY 09:00 ~ 18:00";
        Assertions.assertEquals(expectedOpenSchedule, store.getOpenSchedule());

        Map<String, Object> availableDays = store.getAvailableDays();
        Assertions.assertNotNull(availableDays);
        Assertions.assertTrue(availableDays.containsKey("2024-07-12"));

        Map<String, Object> dayInfo = (Map<String, Object>) availableDays.get("2024-07-12");
        Assertions.assertNotNull(dayInfo);

        List<String> selectableTimes = (List<String>) dayInfo.get("selectableTimes");
        Assertions.assertNotNull(selectableTimes);
        Assertions.assertEquals(Arrays.asList("10:00", "11:00", "12:00", "13:00", "14:00"), selectableTimes);
    }
}
