package com.vip.journalApp.service;

import com.vip.journalApp.entity.JournalEntry;
import com.vip.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry save(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }

    public JournalEntry getById(ObjectId id) {
        return journalEntryRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
        return true;
    }

    public JournalEntry updateEntry(ObjectId id, JournalEntry newEntry) {
        JournalEntry oldEntry = journalEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));

        oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : "");
        oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : "");
        oldEntry.setDate(LocalDateTime.now());

        return journalEntryRepository.save(oldEntry);
    }
}
