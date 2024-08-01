package flab.gotable.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class ReservationRequestDto {
    private Long restaurantId;
    private Long memberSeq;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Long memberCount;
}
