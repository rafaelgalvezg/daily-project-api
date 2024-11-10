package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.PermissionDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PermissionDtoMapper {

    private final ModelMapper modelMapper;

    public PermissionDto toDto(Permission permission) {
        return modelMapper.map(permission, PermissionDto.class);
    }

    public Permission toEntity(PermissionDto permissionDto) {
        return modelMapper.map(permissionDto, Permission.class);
    }
}
