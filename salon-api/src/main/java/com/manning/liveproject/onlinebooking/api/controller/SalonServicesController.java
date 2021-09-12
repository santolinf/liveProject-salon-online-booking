package com.manning.liveproject.onlinebooking.api.controller;

import com.manning.liveproject.onlinebooking.api.controller.dtos.SalonServiceDto;
import com.manning.liveproject.onlinebooking.api.controller.mapper.MapStructMapper;
import com.manning.liveproject.onlinebooking.api.service.SalonServiceDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Salon Services")
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class SalonServicesController {

    private final SalonServiceDetailsService service;
    private final MapStructMapper mapper;

    @ApiOperation("RetrieveAvailableSalonServicesAPI")
    @GetMapping(path = "retrieveAvailableSalonServices", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SalonServiceDto> retrieveAvailableSalonServices() {
        return service.getAvailableSalonServices().stream()
                .map(mapper::salonServiceDetailsToSalonServiceDto)
                .collect(Collectors.toList());
    }
}
