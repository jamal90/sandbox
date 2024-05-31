package io.jam.springcloud.msscbrewery.service;

import io.jam.springcloud.msscbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto saveBeer(BeerDto beerDto);

    void update(UUID beerId, BeerDto beerDto);

    void delete(UUID beerId);
}
