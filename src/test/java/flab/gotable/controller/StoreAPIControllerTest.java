package flab.gotable.controller;

import flab.gotable.domain.entity.DailySchedule;
import flab.gotable.domain.entity.SpecificSchedule;
import flab.gotable.domain.entity.Store;
import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.response.StoreDetailsResponseDto;
import flab.gotable.exception.StoreNotFoundException;
import flab.gotable.mapper.StoreMapper;
import flab.gotable.service.StoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StoreAPIControllerTest {
    private StoreAPIController storeAPIController;

    @BeforeEach
    void setup() {
        storeAPIController = new StoreAPIController(
                new StoreService(
                        new StoreMapper() {
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
                                            new DailySchedule(DayOfWeek.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("18:00"), 60L),
                                            new DailySchedule(DayOfWeek.TUESDAY, LocalTime.parse("09:00"), LocalTime.parse("18:00"), 60L)
                                    );
                                }
                                return Collections.emptyList();
                            }

                            @Override
                            public List<SpecificSchedule> findSpecificScheduleByStoreId(Long id) {
                                if (id == 1L) {
                                    return Arrays.asList(
                                            new SpecificSchedule(LocalDate.of(2024, 7, 12), LocalTime.parse("10:00"), LocalTime.parse("15:00"), 60L)
                                    );
                                }
                                return Collections.emptyList();
                            }

                            @Override
                            public boolean isRestaurantExistId(long restaurantId) {
                                return false;
                            }

                            @Override
                            public long getMaxMemberCount(long restaurantId) {
                                return 0;
                            }
                        }
                )
        );
    }

    @Test
    @DisplayName("식당 조회에 성공할 경우 ApiResponse 객체가 반환한다.")
    void getStoreDetailSuccess() {
        // given
        Long storeId = 1L;

        // when
        ApiResponse<StoreDetailsResponseDto> response = storeAPIController.getStoreDetail(storeId);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("식당 상세 조회 성공", response.getMessage());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(storeId, response.getData().getId());
    }

    @Test
    @DisplayName("해당 id를 갖고 있는 식당이 존재하지 않아 조회에 실패할 경우 StoreNotFoundException 예외를 발생시킨다.")
    void getStoreDetailFail() {
        // given
        Long storeId = 2L;

        // then
        Assertions.assertThrows(StoreNotFoundException.class, () -> storeAPIController.getStoreDetail(storeId));
    }
}
