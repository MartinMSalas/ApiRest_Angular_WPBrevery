package com.wpbrewery.mms.walterpenk.services;

import com.wpbrewery.mms.walterpenk.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
