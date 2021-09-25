package com.manning.liveproject.onlinebooking.api.controller;

import com.manning.liveproject.onlinebooking.api.controller.dtos.SalonServiceDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.SlotDto;
import com.manning.liveproject.onlinebooking.api.controller.mapper.MapStructMapper;
import com.manning.liveproject.onlinebooking.api.service.SalonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Salon Services")
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class SalonServicesController {

    private final SalonService service;
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


    @ApiOperation("RetrieveAvailableSlotsAPI")
    @GetMapping(path = "retrieveAvailableSlots/{salonServiceId}/{formattedDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SlotDto> retrieveAvailableSlots(
            @PathVariable Long salonServiceId,
            @ApiParam(value = "date in yyyy-MM-dd format")
            @PathVariable("formattedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate
    ) {
        return service.getAvailableSlots(salonServiceId, selectedDate).stream()
                .map(mapper::slotToSlotDto)
                .collect(Collectors.toList());
    }
}
