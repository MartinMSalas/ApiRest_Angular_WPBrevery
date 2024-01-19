package com.wpbrewery.mms.walterpenk.services;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.mappers.BeerMapper;
import com.wpbrewery.mms.walterpenk.model.BeerDTO;
import com.wpbrewery.mms.walterpenk.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> updatedBeer = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(beerToUpdate ->{
            if(beer.getBeerName() != null)
                beerToUpdate.setBeerName(beer.getBeerName());
            if(beer.getBeerStyle() != null)
                beerToUpdate.setBeerStyle(beer.getBeerStyle());
            if(beer.getQuantityOnHand() != null)
                beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
            if(beer.getPrice() != null)
                beerToUpdate.setPrice(beer.getPrice());
            if(beer.getUpc() != null)
                beerToUpdate.setUpc(beer.getUpc());
            updatedBeer.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(beerToUpdate))));
        },
                ()->{updatedBeer.set(Optional.empty());}
        );
        return updatedBeer.get();
    }
    public Optional<BeerDTO> updateBeerById2(UUID beerId,BeerDTO beer){
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer ->{
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());

            atomicReference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(foundBeer))));
        },()->{
            atomicReference.set(Optional.empty());
        }
        );
        return atomicReference.get();
    }
    @Override
    public Optional<BeerDTO> deleteById(UUID beerId) {
        AtomicReference<Optional<BeerDTO>> deletedBeer = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(beer ->{
            beerRepository.delete(beer);
            deletedBeer.set(Optional.of(beerMapper.beerToBeerDTO(beer)));
        },()->{
            deletedBeer.set(Optional.empty());
        });
        return deletedBeer.get();
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> updatedBeer = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(beerToUpdate ->{
                    if(beer.getBeerName() != null)
                        beerToUpdate.setBeerName(beer.getBeerName());
                    if(beer.getBeerStyle() != null)
                        beerToUpdate.setBeerStyle(beer.getBeerStyle());
                    if(beer.getQuantityOnHand() != null)
                        beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
                    if(beer.getPrice() != null)
                        beerToUpdate.setPrice(beer.getPrice());
                    if(beer.getUpc() != null)
                        beerToUpdate.setUpc(beer.getUpc());
                    Beer beerSaved = beerRepository.save(beerToUpdate);
                    updatedBeer.set(Optional.of(beerMapper.beerToBeerDTO(beerSaved)));
                },
                ()->{updatedBeer.set(Optional.empty());}
        );
        return updatedBeer.get();
    }
}
