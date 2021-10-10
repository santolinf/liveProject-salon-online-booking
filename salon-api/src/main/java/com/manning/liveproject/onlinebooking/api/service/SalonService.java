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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalonService {

    private final SalonServiceDetailRepository salonServiceDetailRepository;
    private final SlotRepository slotRepository;

    public List<SalonServiceDetail> getAvailableSalonServices() {
        return salonServiceDetailRepository.findAll();
    }

    public Optional<SalonServiceDetail> getSalonServiceDetail(Long id) {
        return salonServiceDetailRepository.findById(id);
    }

    public List<Slot> getAvailableSlots(Long salonServiceDetailId, LocalDate selectedDate) {
        final LocalDateTime selectedStartDateTime = selectedDate.atStartOfDay();
        final LocalDateTime selectedEndDateTime = selectedStartDateTime.plusHours(23);

        return slotRepository.getSlotByAvailableServices_IdAndStatusAndSlotForBetweenOrderBySlotFor(
                salonServiceDetailId,
                SlotStatus.AVAILABLE,
                selectedStartDateTime,
                selectedEndDateTime
        );
    }

    public Optional<Slot> getAvailableSlot(Long slotId) {
        return slotRepository.findByIdAndStatus(slotId, SlotStatus.AVAILABLE);
    }

    public void lockSlot(Long slotId, Long salonServiceDetailId) {
        slotRepository.findById(slotId).ifPresent(slot -> {
            slot.setStatus(SlotStatus.LOCKED);
            slot.setLockedAt(LocalDateTime.now());
            slot.setSelectedService(getSalonServiceDetail(salonServiceDetailId).orElse(null));
            slotRepository.save(slot);
        });
    }

    public void confirmSlot(Long slotId) {
        slotRepository.findById(slotId).ifPresent(slot -> {
            slot.setStatus(SlotStatus.CONFIRMED);
            slot.setConfirmedAt(LocalDateTime.now());
            slotRepository.save(slot);
        });
    }
}
