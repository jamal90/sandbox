package io.jam.springcloud.msscbrewery.service;

import io.jam.springcloud.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomer(UUID uuid);
}
