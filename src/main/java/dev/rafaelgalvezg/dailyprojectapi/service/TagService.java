package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Tag save(Tag tag);
    Tag update(Long id, Tag tag);
    Tag findById(Long id);
    void delete(Long id);
    Page<Tag> findAll(Pageable pageable);
}
