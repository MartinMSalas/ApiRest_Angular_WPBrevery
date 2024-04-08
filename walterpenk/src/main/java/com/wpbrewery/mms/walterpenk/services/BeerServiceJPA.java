package com.wpbrewery.mms.walterpenk.services;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.mappers.BeerMapper;
import com.wpbrewery.mms.walterpenk.model.BeerDTO;
import com.wpbrewery.mms.walterpenk.model.BeerStyle;
import com.wpbrewery.mms.walterpenk.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Beer> beerPage;

        if(StringUtils.hasText(beerName) && beerStyle == null){
            beerPage = listBeersByName(beerName, pageRequest);
        }else if(!StringUtils.hasText(beerName) && beerStyle != null){
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        }else if(StringUtils.hasText(beerName) && beerStyle != null){
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        }else{
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventory != null && !showInventory){
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
            /*
            beerList = beerList.stream()
                    .filter(beer -> beer.getQuantityOnHand() > 0)
                    .collect(Collectors.toList());
             */
        }

//        return Page beerPage.stream()
//                .map(beerMapper::beerToBeerDTO)
//                .collect(Collectors.toList());
        return beerPage.map(beerMapper::beerToBeerDTO);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber -1;
        }else{
            queryPageNumber = DEFAULT_PAGE;
        }
        if (pageSize == null || pageSize < 1) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }else{
            if(pageSize > 1000){
                queryPageSize = 1000;
            }else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
    private Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable){
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }
    private Page<Beer> listBeersByName(String beerName, Pageable pageable){
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable){
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
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
            if(beer.getVersion() != null)
                beerToUpdate.setVersion(beer.getVersion());
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
                    if(beer.getPrice() != null )
                        beerToUpdate.setPrice(beer.getPrice());
                    if(beer.getUpc() != null )
                        beerToUpdate.setUpc(beer.getUpc());
                    Beer beerSaved = beerRepository.save(beerToUpdate);
                    updatedBeer.set(Optional.of(beerMapper.beerToBeerDTO(beerSaved)));
                },
                ()->{updatedBeer.set(Optional.empty());}
        );
        return updatedBeer.get();
    }
}
