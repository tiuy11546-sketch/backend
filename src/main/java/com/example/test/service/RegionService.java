package com.example.test.service;

import com.example.test.dto.request.RegionRequestDto;
import com.example.test.dto.response.RegionResponseDto;
import com.example.test.entities.Region;
import com.example.test.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionResponseDto createRegion(RegionRequestDto requestDto) {
        Region region = new Region();
        region.setName(requestDto.getName());
        region.setCreatedAt(LocalDateTime.now());
        region.setUpdatedAt(LocalDateTime.now());
        Region saved = regionRepository.save(region);
        return toResponseDto(saved);
    }

    public RegionResponseDto getRegionById(UUID id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region not found"));
        return toResponseDto(region);
    }

    public RegionResponseDto updateRegion(UUID id, RegionRequestDto requestDto) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region not found"));
        region.setName(requestDto.getName());
        region.setUpdatedAt(LocalDateTime.now());
        Region updated = regionRepository.save(region);
        return toResponseDto(updated);
    }

    public void deleteRegion(UUID id) {
        regionRepository.deleteById(id);
    }

    public Page<RegionResponseDto> getRegions(Pageable pageable) {
        Page<Region> page = regionRepository.findAll(pageable);
        return new PageImpl<>(
            page.getContent().stream().map(this::toResponseDto).collect(Collectors.toList()),
            pageable,
            page.getTotalElements()
        );
    }

    private RegionResponseDto toResponseDto(Region region) {
        RegionResponseDto dto = new RegionResponseDto();
        dto.setId(region.getId());
        dto.setName(region.getName());
        dto.setCreatedAt(region.getCreatedAt());
        dto.setUpdatedAt(region.getUpdatedAt());
        return dto;
    }
}
