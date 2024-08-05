package flab.gotable.mapper;

import flab.gotable.domain.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper
public interface ReservationMapper {
    public int getRestaurantLock(long restaurantId);

    public boolean isExistDailySchedule(@Param("restaurantId") long restaurantId,
                                        @Param("dayOfWeek") String dayOfWeek,
                                        @Param("startTime") LocalTime startTime,
                                        @Param("endTime") LocalTime endTime);

    public boolean isExistSpecificSchedule(@Param("restaurantId") long restaurantId,
                                           @Param("date") LocalDate date,
                                           @Param("startTime") LocalTime startTime,
                                           @Param("endTime") LocalTime endTime);

    public boolean isDuplicatedReservation(@Param("restaurantId") long restaurantId,
                                           @Param("reservationStartTime") LocalDateTime reservationStartTime,
                                           @Param("reservationEndTime") LocalDateTime reservationEndTime);

    public void saveReservation(Reservation reservation);
}
