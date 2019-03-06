package io.pivotal.pal.tracker;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){
        TimeEntry entry1 = timeEntryRepository.create(timeEntry);

        return ResponseEntity.status(HttpStatus.CREATED).body(entry1);
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry entry1 = timeEntryRepository.find(timeEntryId);
        if(entry1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entry1);
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(entry1);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> newList = new ArrayList<>(timeEntryRepository.list());
        return ResponseEntity.status(HttpStatus.OK).body(newList);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry entry1 = timeEntryRepository.update(timeEntryId, expected);

        if(entry1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entry1);
        }
        else
//            return ResponseEntity.status(HttpStatus.OK).body(entry1);
            return new ResponseEntity<>(entry1, HttpStatus.OK);
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);

        TimeEntry entry1 = timeEntryRepository.find(timeEntryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(entry1);

    }
}
