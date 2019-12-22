package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.sys.EventDao;
import cn.com.bocd.opencbsboot.entity.sys.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventService {
    @Autowired
	private EventDao eventDao;
	
	public List<Event> getByMap(Map<String,Object> map) {
	    return eventDao.getByMap(map);
	}
	
	public Event getById(Integer id) {
		return eventDao.getById(id);
	}
	
	public Event create(Event event) {
		eventDao.create(event);
		return event;
	}
	
	public Event update(Event event) {
		eventDao.update(event);
		return event;
	}
	
	public int delete(Integer id) {
		return eventDao.delete(id);
	}
    
}