package com.vip.journalApp.controller;

import com.vip.journalApp.entity.JournalEntry;
import com.vip.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        return ResponseEntity.ok(journalEntryService.getJournalEntries());
    }

    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry je){
        return new ResponseEntity<>(journalEntryService.save(je), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        return  journalEntryService.getById(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(journalEntryService.getById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteJournalEntryById(@PathVariable ObjectId id){
        return  journalEntryService.deleteById(id) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
    @PutMapping("/id/{id}")
    public JournalEntry deleteJournalEntryById(@PathVariable ObjectId id, @RequestBody  JournalEntry je){
        return  journalEntryService.updateEntry(id,je);
    }
}
