package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.CollaboratorDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CollaboratorDtoMapper {

    private final ModelMapper modelMapper;

    public CollaboratorDto toDto(Collaborator collaborator) {
        return modelMapper.map(collaborator, CollaboratorDto.class);
    }

    public Collaborator toEntity(CollaboratorDto collaboratorDto) {
        return modelMapper.map(collaboratorDto, Collaborator.class);
    }
}
