package flab.gotable.dto.request;

import flab.gotable.domain.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationRequestDto {
    private Long restaurantId;
    private Long memberSeq;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Long memberCount;

    public static Reservation toEntity(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation();

        reservation.setSeq(reservationRequestDto.memberSeq);
        reservation.setRestaurantId(reservationRequestDto.restaurantId);
        reservation.setStatus("success");
        reservation.setMemberCount(reservationRequestDto.memberCount);
        reservation.setReservationStartAt(Timestamp.valueOf(reservationRequestDto.reservationStartTime));
        reservation.setReservationEndAt(Timestamp.valueOf(reservationRequestDto.reservationEndTime));

        return reservation;
    }
}
