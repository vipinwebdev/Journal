package com.vip.journal.cache;

import com.vip.journal.entity.ConfigJournalAppEntity;
import com.vip.journal.repository.ConfigJournalAppRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppCache {

    @Resource
    private ConfigJournalAppRepository configJournalAppRepository;

    Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        appCache = configJournalAppRepository.findAll().stream().collect(Collectors.toMap(ConfigJournalAppEntity::getKey, ConfigJournalAppEntity::getValue));
    }

}
