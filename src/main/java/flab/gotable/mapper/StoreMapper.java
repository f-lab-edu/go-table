package flab.gotable.mapper;

import flab.gotable.domain.entity.DailySchedule;
import flab.gotable.domain.entity.SpecificSchedule;
import flab.gotable.domain.entity.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    public Store findStoreById(Long id);
    public List<DailySchedule> findDailyScheduleByStoreId(Long id);
    public List<SpecificSchedule> findSpecificScheduleByStoreId(Long id);
    public boolean isRestaurantExistId(long restaurantId);
    public long getMaxMemberCount(long restaurantId);
}
