package com.wpbrewery.mms.walterpenk.mappers;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
