package org.example.perfumecatalog.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomNameGeneratorService {

    public String getRandomName() {
        return UUID.randomUUID().toString();
    }

}
