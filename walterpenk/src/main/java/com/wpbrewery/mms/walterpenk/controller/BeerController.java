package com.wpbrewery.mms.walterpenk.controller;

import com.wpbrewery.mms.walterpenk.model.BeerDTO;
import com.wpbrewery.mms.walterpenk.model.BeerStyle;
import com.wpbrewery.mms.walterpenk.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(value = "beerName", required = false) String beerName,
                                   @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand,
                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize){


        return beerService.listBeers(beerName, beerStyle, showInventoryOnHand, pageNumber, pageSize);
    }



    @GetMapping(value = BEER_PATH_ID)
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by Id, in controller");

        return new ResponseEntity<>(beerService.getBeerById(beerId).orElseThrow(NotFoundException::new), HttpStatus.OK);
    }

}
