package flab.gotable.service;

import flab.gotable.dto.request.ReservationRequestDto;
import flab.gotable.exception.DuplicatedReservationException;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.ScheduleNotFoundException;
import flab.gotable.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper reservationMapper;

    // 트랜잭션 격리 레벨 변경
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void reserve(ReservationRequestDto reservationRequestDto) {
        final Long restaurantId = reservationRequestDto.getRestaurantId();
        final LocalDateTime reservationStartTime = reservationRequestDto.getReservationStartTime();
        final LocalDateTime reservationEndTime = reservationRequestDto.getReservationEndTime();

        // daily 시간의 데이터를,,, 어떻게 담아와야할까? daily_schedule은 요일별로만 되어있는데,,, 날짜별로 안되어있고 여튼 쿼리를 손봐야한다.


        // 1. 락 획득 여부
        if(reservationMapper.selectRestaurantForUpdate(reservationRequestDto.getRestaurantId()) > 0) {
            // 2-1. 예약하려는 시간에 다른 사용자가 미예약인가?
            if (!isReservationAvailable(restaurantId, reservationStartTime, reservationEndTime)) { // 예약하려는 시간이 겹칠 때
                throw new DuplicatedReservationException(ErrorCode.DUPLICATED_RESERVATION_TIME, ErrorCode.DUPLICATED_RESERVATION_TIME.getMessage());
            }

            // 2-2. 예약하려는 시간이 일반 또는 영업 스케줄 중에 존재하는가?
            if (!isExistSchedule(restaurantId, reservationStartTime, reservationEndTime)) { // 존재하지 않으면
                throw new ScheduleNotFoundException(ErrorCode.RESERVATION_TIME_NOT_FOUND, ErrorCode.RESERVATION_TIME_NOT_FOUND.getMessage());
            }

            // 3. 예약하기
            // Reservation reservation = reservationRequestDto.toEntity(reservationRequestDto);
            // reservationMapper.saveReservation(reservation);

            // 4-1. 예약 성공하면 reservation 테이블에 예약정보 저장, 트랜잭션 커밋(락 반납)
            // return new ReservationResponseDto(reservation);
        }
        // 4-2. 예약 실패하면 트랜잭션 롤백(락 반납)
        // return new ReservationResponseDto(reservation);
    }

    private boolean isReservationAvailable(Long restaurantId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        final Timestamp startTime = Timestamp.valueOf(reservationStartTime);
        final Timestamp endTime = Timestamp.valueOf(reservationEndTime);

        return !reservationMapper.isDuplicatedReservation(restaurantId, startTime, endTime);
    }

    private boolean isExistSchedule(Long restaurantId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        final Date date = java.sql.Date.valueOf(reservationStartTime.toLocalDate());
        final Time startTime = java.sql.Time.valueOf(reservationStartTime.toLocalTime());
        final Time endTime = java.sql.Time.valueOf(reservationEndTime.toLocalTime());

        boolean isExistDailySchedule = reservationMapper.isExistSchedule(restaurantId, date, startTime, endTime, "daily");
        boolean isExistSpecificSchedule = reservationMapper.isExistSchedule(restaurantId, date, startTime, endTime, "specific");

        return isExistDailySchedule || isExistSpecificSchedule;
    }
}
