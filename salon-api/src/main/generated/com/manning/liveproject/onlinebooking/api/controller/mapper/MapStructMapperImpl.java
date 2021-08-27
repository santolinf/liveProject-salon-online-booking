package com.manning.liveproject.onlinebooking.api.controller.mapper;

import com.manning.liveproject.onlinebooking.api.controller.dtos.SalonServiceDto;
import com.manning.liveproject.onlinebooking.api.model.SalonServiceDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-27T19:32:20+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public SalonServiceDto salonServiceDetailsToSalonServiceDto(SalonServiceDetail salonServiceDetail) {
        if ( salonServiceDetail == null ) {
            return null;
        }

        SalonServiceDto salonServiceDto = new SalonServiceDto();

        salonServiceDto.setId( salonServiceDetail.getId() );
        salonServiceDto.setName( salonServiceDetail.getName() );
        salonServiceDto.setDescription( salonServiceDetail.getDescription() );
        salonServiceDto.setPrice( salonServiceDetail.getPrice() );
        salonServiceDto.setTimeInMinutes( salonServiceDetail.getTimeInMinutes() );

        return salonServiceDto;
    }
}
