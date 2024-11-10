package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import dev.rafaelgalvezg.dailyprojectapi.repository.TagRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        tagRepository.delete(tag);
    }

    @Override
    @Transactional
    public Tag update(Long id, Tag tag) {
        if (!tagRepository.existsById(id)) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        try {
            return tagRepository.save(tag);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }
    }

}
