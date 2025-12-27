package com.vip.journal.service;

import com.vip.journal.entity.JournalEntry;
import com.vip.journal.entity.User;
import com.vip.journal.repository.JournalEntryRepository;
import com.vip.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry save(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        Optional<User> user = userRepository.findByUsername(username);

        user.ifPresent(user1 -> {
            user.get().getJournalEntries().add(saved);
            userRepository.save(user1);
        });

        return journalEntryRepository.save(journalEntry);
    }

    public JournalEntry getById(ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            ObjectId objectId = Objects.requireNonNull(user.get().getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).findFirst().orElse(null)).getId();
            return journalEntryRepository.findById(objectId).orElse(null);
        }
        return null;
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        boolean removedFromUser = user.getJournalEntries()
                .removeIf(entry -> entry.getId().equals(id));
        if (!removedFromUser) {
            return false;
        }
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
        return true;
    }

    public JournalEntry updateEntry(ObjectId id, JournalEntry newEntry) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            ObjectId objectId = Objects.requireNonNull(user.get().getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).findFirst().orElse(null)).getId();
            JournalEntry oldEntry = journalEntryRepository.findById(objectId).orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : "");
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : "");
            oldEntry.setDate(LocalDateTime.now());
            return journalEntryRepository.save(oldEntry);
        }

        return null;
    }
}
