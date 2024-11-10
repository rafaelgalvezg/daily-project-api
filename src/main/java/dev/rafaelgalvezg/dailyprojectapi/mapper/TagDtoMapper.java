package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.TagDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TagDtoMapper {

    private final ModelMapper modelMapper;

    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    public Tag toEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }
}
