package com.manning.liveproject.onlinebooking.api.controller.mapper;

import com.manning.liveproject.onlinebooking.api.controller.dtos.PaymentDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.SalonServiceDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.SlotDto;
import com.manning.liveproject.onlinebooking.api.controller.dtos.TicketDto;
import com.manning.liveproject.onlinebooking.api.model.Payment;
import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import com.manning.liveproject.onlinebooking.api.model.Slot;
import com.manning.liveproject.onlinebooking.api.model.Ticket;
import org.mapstruct.Mapper;

// https://auth0.com/blog/how-to-automatically-map-jpa-entities-into-dtos-in-spring-boot-using-mapstruct/
@Mapper(componentModel = "spring")
public interface MapStructMapper {

    SalonServiceDto salonServiceDetailsToSalonServiceDto(SalonServiceDetail salonServiceDetail);

    SlotDto slotToSlotDto(Slot slot);

    PaymentDto paymentToPaymentDto(Payment payment);

    TicketDto ticketToTicketDto(Ticket ticket);
}
