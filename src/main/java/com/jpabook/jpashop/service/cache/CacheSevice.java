package com.jpabook.jpashop.service.cache;

import com.jpabook.jpashop.config.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheSevice {

    @Caching(evict = {
            @CacheEvict(value = CacheKey.POST, key = "#postId"),
            @CacheEvict(value = CacheKey.POSTS, key = "#boardName")
    })
    public boolean deleteBoardCache(long postId, String boardName) {
        log.debug("deleteBoardCache - postId {}, boardName {}", postId, boardName);
        return true;
    }
}
