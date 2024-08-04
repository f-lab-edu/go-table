package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    private long id;
    private long memberSeq;
    private long restaurantId;
    private String status;
    private long memberCount;
    private LocalDateTime createdAt;
    private LocalDateTime reservationStartAt;
    private LocalDateTime reservationEndAt;
    private String cancelReason;
}