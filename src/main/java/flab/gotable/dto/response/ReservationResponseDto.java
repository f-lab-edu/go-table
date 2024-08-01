package flab.gotable.dto.response;

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
}
