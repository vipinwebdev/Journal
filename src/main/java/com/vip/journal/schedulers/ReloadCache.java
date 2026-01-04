package com.vip.journal.schedulers;

import com.vip.journal.cache.AppCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ReloadCache {

    @Resource
    private AppCache appCache;

    @Scheduled(cron = "0 * * * * *")
    public void reload(){
        appCache.init();
    }
}
