package com.vip.journal.controller;

import com.vip.journal.entity.JournalEntry;
import com.vip.journal.entity.User;
import com.vip.journal.service.JournalEntryService;
import com.vip.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<JournalEntry> getJournalEntries() {
        return journalEntryService.getJournalEntries();
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(value -> ResponseEntity.ok(value.getJournalEntries())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry journalEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(journalEntryService.save(journalEntry,username), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        return  journalEntryService.getById(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(journalEntryService.getById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> updateJournalEntryById(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return  journalEntryService.deleteById(id,username) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
    @PutMapping("/id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody  JournalEntry journalEntry){
        return  journalEntryService.updateEntry(id,journalEntry);
    }
}
