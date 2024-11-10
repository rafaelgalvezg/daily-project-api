package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.ChangeTrackingDto;
import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChangeTrackingDtoMapper {
    private final ModelMapper modelMapper;

    public ChangeTrackingDto toDto(ChangeTracking changeTracking) {
        return modelMapper.map(changeTracking, ChangeTrackingDto.class);
    }

    public ChangeTracking toEntity(ChangeTrackingDto changeTrackingDto) {
        return modelMapper.map(changeTrackingDto, ChangeTracking.class);
    }
}
