package com.example.test.controller;

import com.example.test.dto.request.RegionRequestDto;
import com.example.test.dto.response.RegionResponseDto;
import com.example.test.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/regions")
public class RegionController {
    @Autowired
    private RegionService regionService;

    // Template: List and manage regions
    @GetMapping
    public String getRegionsTemplate(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionResponseDto> regions = regionService.getRegions(pageable);
        model.addAttribute("regions", regions);
        model.addAttribute("regionRequestDto", new RegionRequestDto());
        return "region";
    }

    // Template: Create region
    @PostMapping
    public String createRegionTemplate(@Valid @ModelAttribute("regionRequestDto") RegionRequestDto dto,
                                       BindingResult result,
                                       Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        if (result.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<RegionResponseDto> regions = regionService.getRegions(pageable);
            model.addAttribute("regions", regions);
            return "region";
        }
        regionService.createRegion(dto);
        return "redirect:/regions";
    }

    // Template: Delete region
    @PostMapping("/delete/{id}")
    public String deleteRegionTemplate(@PathVariable UUID id) {
        regionService.deleteRegion(id);
        return "redirect:/regions";
    }

    // Template: Edit region (show form)
    @GetMapping("/edit/{id}")
    public String editRegionTemplate(@PathVariable UUID id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionResponseDto> regions = regionService.getRegions(pageable);
        RegionResponseDto editRegion = regionService.getRegionById(id);
        RegionRequestDto regionRequestDto = new RegionRequestDto();
        regionRequestDto.setName(editRegion.getName());
        model.addAttribute("regions", regions);
        model.addAttribute("editRegion", editRegion);
        model.addAttribute("regionRequestDto", new RegionRequestDto());
        model.addAttribute("editRegionRequestDto", regionRequestDto);
        return "region";
    }

    // Template: Update region (handle form)
    @PostMapping("/update/{id}")
    public String updateRegionTemplate(@PathVariable UUID id,
                                       @Valid @ModelAttribute("editRegionRequestDto") RegionRequestDto dto,
                                       BindingResult result,
                                       Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionResponseDto> regions = regionService.getRegions(pageable);
        if (result.hasErrors()) {
            RegionResponseDto editRegion = regionService.getRegionById(id);
            model.addAttribute("regions", regions);
            model.addAttribute("editRegion", editRegion);
            model.addAttribute("editRegionRequestDto", dto);
            model.addAttribute("regionRequestDto", new RegionRequestDto());
            return "region";
        }
        regionService.updateRegion(id, dto);
        return "redirect:/regions?page=" + page + "&size=" + size;
    }
}
