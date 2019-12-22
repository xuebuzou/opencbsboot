package cn.com.bocd.opencbsboot.dao.sys;

import cn.com.bocd.opencbsboot.entity.sys.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EventDao {
    List<Event> getByMap(Map<String, Object> map);

    Event getById(Integer id);

    Integer create(Event event);

    int update(Event event);

    int delete(Integer id);
}