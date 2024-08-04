package flab.gotable.dto.request;

import flab.gotable.domain.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class ReservationRequestDto {
    private long restaurantId;
    private long memberSeq;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private long memberCount;

    public static Reservation toEntity(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation();

        reservation.setMemberSeq(reservationRequestDto.memberSeq);
        reservation.setRestaurantId(reservationRequestDto.restaurantId);
        reservation.setStatus("success");
        reservation.setMemberCount(reservationRequestDto.memberCount);
        reservation.setReservationStartAt(reservationRequestDto.reservationStartTime);
        reservation.setReservationEndAt(reservationRequestDto.reservationEndTime);

        return reservation;
    }
}
