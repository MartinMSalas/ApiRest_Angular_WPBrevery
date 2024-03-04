package com.wpbrewery.mms.walterpenk.controller;

import com.wpbrewery.mms.walterpenk.model.BeerDTO;
import com.wpbrewery.mms.walterpenk.model.BeerStyle;
import com.wpbrewery.mms.walterpenk.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){

        Optional<BeerDTO> beerDeleted = beerService.deleteById(beerId);

        if (beerDeleted.isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(beerDeleted.get(),HttpStatus.OK);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beer){

        if( beerService.updateBeerById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    /*
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId,@Validated @RequestBody BeerDTO beer){
        Optional<BeerDTO> beerUpdated = beerService.updateBeerById(beerId, beer);
        if (beerUpdated.isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(beerUpdated.get(),HttpStatus.NO_CONTENT);
    }
*/
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer){

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

/*
    @GetMapping(value = "/api/v1/beer2")
    public Page<BeerDTO> listBeers2(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {
        Page<BeerDTO> beerDTOPage = beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
        return beerDTOPage;
    }

 */
    @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize){
        Page<BeerDTO> beerDTOPage = beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
        return beerDTOPage;
/*
        Map<UUID, BeerDTO> beerMap;


        beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

            BeerDTO beer2 = BeerDTO.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            BeerDTO beer3 = BeerDTO.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerMap.put(beer1.getId(), beer1);
            beerMap.put(beer2.getId(), beer2);
            beerMap.put(beer3.getId(), beer3);
        //return new PageImpl<>(new ArrayList<>(beerMap.values()));
        return new PageImpl<>(new ArrayList<>());

 */
    }



    @GetMapping(value = BEER_PATH_ID)
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by Id, in controller");

        return new ResponseEntity<>(beerService.getBeerById(beerId).orElseThrow(NotFoundException::new), HttpStatus.OK);
    }

}
