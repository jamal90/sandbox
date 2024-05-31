package io.jam.springcloud.msscbrewery.service;

import io.jam.springcloud.msscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Test - 2")
                .beerStyle("Typ1")
                .build();
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerDto;
    }

    @Override
    public void update(UUID beerId, BeerDto beerDto) {
        // todo impl
    }

    @Override
    public void delete(UUID beerId) {
        log.debug("Deleting beer with id {}", beerId);
        // todo impl
    }
}
