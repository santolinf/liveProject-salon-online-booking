package com.manning.liveproject.onlinebooking.api.model.repository;

import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonServiceDetailRepository extends JpaRepository<SalonServiceDetail, Long> {
}
