package flab.gotable.service;

import flab.gotable.dto.request.ReservationRequestDto;
import flab.gotable.dto.response.ReservationResponseDto;
import flab.gotable.exception.*;
import flab.gotable.mapper.MemberMapper;
import flab.gotable.mapper.ReservationMapper;
import flab.gotable.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationServiceTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @BeforeAll
    public static void setup() {
        mySQLContainer.start();
    }

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Test
    @DisplayName("존재하지 않는 회원 seq로 예약하는 경우 MemberNotFoundException 예외를 발생시킨다.")
    void reserveNotExistMember() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 99L, LocalDateTime.of(2024, 8, 14, 10, 0), LocalDateTime.of(2023, 8, 14, 11, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(MemberNotFoundException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("존재하지 않는 식당 id로 예약하는 경우 StoreNotFoundException 예외를 발생시킨다.")
    void reserveNotExistRestaurant() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(351L, 1L, LocalDateTime.of(2024, 8, 14, 10, 0), LocalDateTime.of(2024, 8, 14, 11, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(StoreNotFoundException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("0 이하의 인원 수로 예약하는 경우 InvalidReservationMemberCountException 예외를 발생시킨다.")
    void reserveUnderMinMemberCount() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2024, 8, 14, 10, 0), LocalDateTime.of(2024, 8, 14, 11, 0), 0L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(InvalidReservationMemberCountException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("예약 인원 수가 예약 가능 최대 인원 수를 초과했을 경우 InvalidReservationMemberCountException 예외를 발생시킨다.")
    void reserveExceedsMaxMemberCount() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2024, 8, 14, 10, 0), LocalDateTime.of(2024, 8, 14, 11, 0), 99L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(InvalidReservationMemberCountException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("예약 종료 시간이 예약 시작 시간보다 앞서거나 동일한 경우 InvalidReservationTimeException 예외를 발생시킨다.")
    void reserveEndTimeBeforeOrEqualStartTime() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2024, 8, 14, 10, 0), LocalDateTime.of(2024, 8, 14, 9, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(InvalidReservationTimeException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("예약 시작 시간이 현재 시간보다 이전인 경우 InvalidReservationTimeException 예외를 발생시킨다.")
    void reservePastStartTime() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2024, 1, 1, 10, 0), LocalDateTime.of(2024, 8, 14, 9, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(InvalidReservationTimeException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("예약 시간이 중복되는 경우 InvalidReservationTimeException 예외를 발생시킨다.")
    void duplicatedReservationTime() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(2L, 2L, LocalDateTime.of(2024, 8, 15, 11, 0), LocalDateTime.of(2024, 8, 15, 12, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(DuplicatedReservationException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("예약하는 시간이 일반 또는 특수 영업 스케줄에 존재하지 않을 경우 ScheduleNotFoundException 예외를 발생시킨다.")
    void reserveNotExistSchedule() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(2L, 1L, LocalDateTime.of(2024, 8, 12, 11, 0), LocalDateTime.of(2024, 8, 12, 12, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when, then
        Assertions.assertThrows(ScheduleNotFoundException.class, () -> { reservationService.reserve(requestDto); });
    }

    @Test
    @DisplayName("유효한 예약 정보로 예약할 경우 ReservationResponseDto를 반환한다.")
    void reserveSuccess() {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2024, 8, 18, 11, 0), LocalDateTime.of(2024, 8, 18, 12, 0), 3L);

        ReservationService reservationService = new ReservationService(reservationMapper, memberMapper, storeMapper);

        // when
        ReservationResponseDto responseDto = reservationService.reserve(requestDto);

        // then
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(1L, responseDto.getMemberSeq());
        Assertions.assertEquals(1L, responseDto.getRestaurantId());
        Assertions.assertEquals(LocalDateTime.of(2024, 8, 18, 11, 0), responseDto.getReservationStartTime());
        Assertions.assertEquals( LocalDateTime.of(2024, 8, 18, 12, 0), responseDto.getReservationEndTime());
        Assertions.assertEquals(3L, responseDto.getMemberCount());
    }
}
