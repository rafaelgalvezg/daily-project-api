package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.ChangeTrackingDto;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ChangeTrackingDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import dev.rafaelgalvezg.dailyprojectapi.service.ChangeTrackingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
@Tag(name = "Change Tracking", description = "Change Tracking API")
public class ChangeTrackingController {

    private final ChangeTrackingService changeTrackingService;
    private final ChangeTrackingDtoMapper changeTrackingMapper;

    @GetMapping
    public ResponseEntity<Page<ChangeTrackingDto>> findAll(@ParameterObject Pageable pageable){
            Page<ChangeTracking> trackingPage = changeTrackingService.findAll(pageable);
            Page<ChangeTrackingDto> trackingPageDto = trackingPage.map(changeTrackingMapper::toDto);
        return ResponseEntity.ok(trackingPageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChangeTrackingDto> findById(@PathVariable Long id) {
        ChangeTrackingDto tracking = changeTrackingMapper.toDto(changeTrackingService.findById(id));
        return ResponseEntity.ok(tracking);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ChangeTrackingDto changeTrackingDto){
       ChangeTracking saveTracking =  changeTrackingService.save(changeTrackingMapper.toEntity(changeTrackingDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveTracking.getIdTracking()).toUri();
       return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChangeTrackingDto> update(@PathVariable Long id, @Valid @RequestBody ChangeTrackingDto tracking) {
        ChangeTracking updatedTracking = changeTrackingService.update(id, changeTrackingMapper.toEntity(tracking));
        return ResponseEntity.ok(changeTrackingMapper.toDto(updatedTracking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        changeTrackingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
