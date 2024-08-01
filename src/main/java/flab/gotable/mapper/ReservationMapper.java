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
    public boolean isExistSchedule(Long restaurantId, @Param("date") Date startDate, Time startTime, Time endTime, String scheduleType);
    public boolean isDuplicatedReservation(Long restaurantId, Timestamp reservationStartTime, Timestamp reservationEndTime);
    public void saveReservation(Reservation reservation);
}
