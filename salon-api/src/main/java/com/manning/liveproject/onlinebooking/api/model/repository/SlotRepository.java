package com.manning.liveproject.onlinebooking.api.model.repository;

import com.manning.liveproject.onlinebooking.api.model.Slot;
import org.springframework.data.repository.CrudRepository;

public interface SlotRepository extends CrudRepository<Slot, Long> {
}
