package com.manning.liveproject.onlinebooking.api.service;

import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import com.manning.liveproject.onlinebooking.api.model.repository.SalonServiceDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceDetailsService {

    private final SalonServiceDetailRepository repository;

    public List<SalonServiceDetail> getAvailableSalonServices() {
        return repository.findAll();
    }
}
