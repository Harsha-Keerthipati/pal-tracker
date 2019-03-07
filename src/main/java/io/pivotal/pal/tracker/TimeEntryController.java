package io.pivotal.pal.tracker;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntriesRepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;


    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){
        TimeEntry entry1 = timeEntryRepository.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(entry1);
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry entry1 = timeEntryRepository.find(timeEntryId);
        if(entry1 == null){
            actionCounter.increment();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entry1);
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(entry1);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryRepository.list());
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry entry1 = timeEntryRepository.update(timeEntryId, expected);

        if(entry1 == null){
            actionCounter.increment();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entry1);
        }
        else
//            return ResponseEntity.status(HttpStatus.OK).body(entry1);
            return new ResponseEntity<>(entry1, HttpStatus.OK);
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        TimeEntry entry1 = timeEntryRepository.find(timeEntryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(entry1);

    }
}
