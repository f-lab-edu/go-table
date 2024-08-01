package flab.gotable.mapper;

import flab.gotable.domain.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Mapper
public interface ReservationMapper {
    public int selectRestaurantForUpdate(Long restaurantId);

    public boolean isExistDailySchedule(@Param("restaurantId") Long restaurantId,
                                        @Param("dayOfWeek") String dayOfWeek,
                                        @Param("startTime") Time startTime,
                                        @Param("endTime") Time endTime);

    public boolean isExistSpecificSchedule(@Param("restaurantId") Long restaurantId,
                                           @Param("date") Date date,
                                           @Param("startTime") Time startTime,
                                           @Param("endTime") Time endTime);

    public boolean isDuplicatedReservation(@Param("restaurantId") Long restaurantId,
                                           @Param("reservationStartTime") Timestamp reservationStartTime,
                                           @Param("reservationEndTime") Timestamp reservationEndTime);

    public void saveReservation(Reservation reservation);
}
