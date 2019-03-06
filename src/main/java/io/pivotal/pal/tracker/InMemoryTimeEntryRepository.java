package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    Map<Long, TimeEntry> map = new HashMap<>();
    Long id = 1L;
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(id);
        map.put(id, timeEntry);
        id++;
        System.out.println(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return map.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<>(map.values());
        return list;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(map.get(id)==null){
            return null;
        }
        timeEntry.setId(id);
        map.replace(id, timeEntry);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }
}
