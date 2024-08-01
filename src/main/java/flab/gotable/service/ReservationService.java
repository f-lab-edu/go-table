package flab.gotable.service;

import flab.gotable.domain.entity.Reservation;
import flab.gotable.dto.request.ReservationRequestDto;
import flab.gotable.dto.response.ReservationResponseDto;
import flab.gotable.exception.DuplicatedReservationException;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.ReservationFailedException;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        final Long restaurantId = reservationRequestDto.getRestaurantId();
        final LocalDateTime reservationStartTime = reservationRequestDto.getReservationStartTime();
        final LocalDateTime reservationEndTime = reservationRequestDto.getReservationEndTime();

        // 락 획득 여부
        if(reservationMapper.selectRestaurantForUpdate(reservationRequestDto.getRestaurantId()) > 0) {
            // 예약하려는 시간에 다른 사용자가 예약하지 않았는지
            if (!isReservationAvailable(restaurantId, reservationStartTime, reservationEndTime)) {
                throw new DuplicatedReservationException(ErrorCode.DUPLICATED_RESERVATION_TIME, ErrorCode.DUPLICATED_RESERVATION_TIME.getMessage());
            }

            // 예약하려는 시간이 일반 또는 영업 스케줄 중에 존재하는지
            if (!isExistSchedule(restaurantId, reservationStartTime, reservationEndTime)) {
                throw new ScheduleNotFoundException(ErrorCode.RESERVATION_TIME_NOT_FOUND, ErrorCode.RESERVATION_TIME_NOT_FOUND.getMessage());
            }

            Reservation reservation = ReservationRequestDto.toEntity(reservationRequestDto);
            reservationMapper.saveReservation(reservation);

            return new ReservationResponseDto(reservation);
        }

        throw new ReservationFailedException(ErrorCode.RESERVATION_FAILED, ErrorCode.RESERVATION_FAILED.getMessage());
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
        final String dayOfWeek = reservationStartTime.getDayOfWeek().toString();

        boolean isExistDailySchedule = reservationMapper.isExistDailySchedule(restaurantId, dayOfWeek, startTime, endTime);
        boolean isExistSpecificSchedule = reservationMapper.isExistSpecificSchedule(restaurantId, date, startTime, endTime);

        return isExistDailySchedule || isExistSpecificSchedule;
    }
}
