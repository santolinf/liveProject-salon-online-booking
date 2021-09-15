package com.manning.liveproject.onlinebooking.api.service;

import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import com.manning.liveproject.onlinebooking.api.model.Slot;
import com.manning.liveproject.onlinebooking.api.model.enums.SlotStatus;
import com.manning.liveproject.onlinebooking.api.model.repository.SalonServiceDetailRepository;
import com.manning.liveproject.onlinebooking.api.model.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonService {

    private final SalonServiceDetailRepository salonServiceDetailRepository;
    private final SlotRepository slotRepository;

    public List<SalonServiceDetail> getAvailableSalonServices() {
        return salonServiceDetailRepository.findAll();
    }

    public List<Slot> getAvailableSlots(Long salonServiceId, LocalDate selectedDate) {
        final LocalDateTime selectedStartDateTime = selectedDate.atStartOfDay();
        final LocalDateTime selectedEndDateTime = selectedStartDateTime.plusHours(23);

        return slotRepository.getSlotByAvailableServices_IdAndStatusAndSlotForBetweenOrderBySlotFor(
                salonServiceId,
                SlotStatus.AVAILABLE,
                selectedStartDateTime,
                selectedEndDateTime
        );
    }
}
