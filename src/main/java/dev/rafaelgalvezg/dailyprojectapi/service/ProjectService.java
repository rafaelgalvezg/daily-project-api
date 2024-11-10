package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
        ProjectDto save (ProjectDto projectDto);
        ProjectDto update (ProjectDto projectDto);
        ProjectDto findById (Long id);
        Page<ProjectDto> findAll (Pageable pageable);
        void deleteById (Long id);
    }
