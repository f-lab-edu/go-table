package flab.gotable.dto.response;

import flab.gotable.domain.entity.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Getter
public class ReservationResponseDto {
    private Long reservationId;
    private Long restaurantId;
    private Long memberSeq;
    private Timestamp reservationStartTime;
    private Timestamp reservationEndTime;
    private Long memberCount;

    public ReservationResponseDto(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.restaurantId = reservation.getRestaurantId();
        this.memberSeq = reservation.getSeq();
        this.reservationStartTime = reservation.getReservationStartAt();
        this.reservationEndTime = reservation.getReservationEndAt();
        this.memberCount = reservation.getMemberCount();
    }
}
