package flab.gotable.dto.response;

import flab.gotable.domain.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponseDto {
    private long reservationId;
    private long restaurantId;
    private long memberSeq;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private long memberCount;

    public ReservationResponseDto(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.restaurantId = reservation.getRestaurantId();
        this.memberSeq = reservation.getMemberSeq();
        this.reservationStartTime = reservation.getReservationStartAt();
        this.reservationEndTime = reservation.getReservationEndAt();
        this.memberCount = reservation.getMemberCount();
    }
}
