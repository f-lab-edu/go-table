package flab.gotable.service;

import flab.gotable.domain.entity.Reservation;
import flab.gotable.dto.request.ReservationRequestDto;
import flab.gotable.dto.response.ReservationResponseDto;
import flab.gotable.exception.*;
import flab.gotable.mapper.MemberMapper;
import flab.gotable.mapper.ReservationMapper;
import flab.gotable.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper reservationMapper;
    private final MemberMapper memberMapper;
    private final StoreMapper storeMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        final long restaurantId = reservationRequestDto.getRestaurantId();
        final long memberSeq = reservationRequestDto.getMemberSeq();
        final long memberCount = reservationRequestDto.getMemberCount();
        final LocalDateTime reservationStartTime = reservationRequestDto.getReservationStartTime();
        final LocalDateTime reservationEndTime = reservationRequestDto.getReservationEndTime();

        // 회원 유효성 검사
        if(!isMemberExists(memberSeq)) {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND_SEQ, ErrorCode.MEMBER_NOT_FOUND_SEQ.getMessage());
        }

        // 식당 유효성 검사
        if(!isRestaurantExists(restaurantId)) {
            throw new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND, ErrorCode.STORE_NOT_FOUND.getMessage());
        }

        // 예약 인원 유효성 검사
        checkMemberCount(restaurantId, memberCount);
        
        // 예약 시간 유효성 검사
        checkReservationTime(reservationStartTime, reservationEndTime);

        // 락 획득 여부
        if(reservationMapper.getRestaurantLock(reservationRequestDto.getRestaurantId()) <= 0) {
            // 락 획득 실패
            throw new LockFailureException(ErrorCode.LOCK_ACQUISITION_FAILED, ErrorCode.LOCK_ACQUISITION_FAILED.getMessage());
        }
        // 예약하려는 시간에 다른 사용자가 예약한 경우
        if (!isReservationAvailable(restaurantId, reservationStartTime, reservationEndTime)) {
            throw new DuplicatedReservationException(ErrorCode.DUPLICATED_RESERVATION_TIME, ErrorCode.DUPLICATED_RESERVATION_TIME.getMessage());
        }

        // 예약하려는 시간이 일반 또는 영업 스케줄 중에 존재하지 않는 경우
        if (!isExistSchedule(restaurantId, reservationStartTime, reservationEndTime)) {
            throw new ScheduleNotFoundException(ErrorCode.RESERVATION_TIME_NOT_FOUND, ErrorCode.RESERVATION_TIME_NOT_FOUND.getMessage());
        }

        Reservation reservation = ReservationRequestDto.toEntity(reservationRequestDto);
        reservationMapper.saveReservation(reservation);

        return new ReservationResponseDto(reservation);
    }

    private boolean isMemberExists(long memberSeq) {
        return memberMapper.isMemberExistSeq(memberSeq);
    }

    private boolean isRestaurantExists(long restaurantId) {
        return storeMapper.isRestaurantExistId(restaurantId);
    }

    private void checkMemberCount(long restaurantId, long memberCount) {
        // 예약 인원 수가 0 이하일 경우
        if(memberCount <= 0) {
            throw new InvalidReservationMemberCountException(ErrorCode.INVALID_MAX_MEMBER_COUNT, ErrorCode.INVALID_MAX_MEMBER_COUNT.getMessage());
        }
        
        // 예약 인원 수가 예약 가능 최대 인원 수를 초과했을 경우
        if(memberCount > storeMapper.getMaxMemberCount(restaurantId)) {
            throw new InvalidReservationMemberCountException(ErrorCode.EXCEEDS_MAX_MEMBER_COUNT, ErrorCode.EXCEEDS_MAX_MEMBER_COUNT.getMessage());
        }
    }

    private void checkReservationTime(LocalDateTime startTime, LocalDateTime endTime) {
        // 예약 종료 시간이 예약 시작 시간보다 앞서거나 동일한 경우
        if(endTime.isBefore(startTime) || endTime.equals(startTime)) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME, ErrorCode.INVALID_RESERVATION_TIME.getMessage());
        }

        // 예약 시작 시간이 현재 시간보다 이전인 경우
        if(startTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException(ErrorCode.PAST_RESERVATION_TIME, ErrorCode.PAST_RESERVATION_TIME.getMessage());
        }
    }

    private boolean isReservationAvailable(long restaurantId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        return !reservationMapper.isDuplicatedReservation(restaurantId, reservationStartTime, reservationEndTime);
    }

    private boolean isExistSchedule(long restaurantId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        final LocalTime startTime = reservationStartTime.toLocalTime();
        final LocalTime endTime = reservationEndTime.toLocalTime();
        final String dayOfWeek = reservationStartTime.getDayOfWeek().toString();

        boolean isExistDailySchedule = reservationMapper.isExistDailySchedule(restaurantId, dayOfWeek, startTime, endTime);
        boolean isExistSpecificSchedule = reservationMapper.isExistSpecificSchedule(restaurantId, reservationStartTime.toLocalDate(), startTime, endTime);

        return isExistDailySchedule || isExistSpecificSchedule;
    }
}
