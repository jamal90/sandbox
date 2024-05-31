package io.jam.springcloud.msscbrewery.service;

import io.jam.springcloud.msscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomer(UUID uuid) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Jam")
                .build();
    }
}
