package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    private Long id;
    private Long seq;
    private Long restaurantId;
    private String status;
    private Long memberCount;
    private Timestamp createdAt;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private String cancelReason;
}