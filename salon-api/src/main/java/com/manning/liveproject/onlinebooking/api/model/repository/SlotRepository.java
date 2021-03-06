package com.manning.liveproject.onlinebooking.api.model.repository;

import com.manning.liveproject.onlinebooking.api.model.Slot;
import com.manning.liveproject.onlinebooking.api.model.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    Optional<Slot> findByIdAndStatus(Long id, SlotStatus status);

    List<Slot> getSlotByAvailableServices_IdAndStatusAndSlotForBetweenOrderBySlotFor(
            Long id,
            SlotStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
