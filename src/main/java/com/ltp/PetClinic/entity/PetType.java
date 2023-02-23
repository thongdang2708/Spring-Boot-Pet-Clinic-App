package com.ltp.PetClinic.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PetType {

    enum PetOption {
        bird,
        cat,
        dog,
        hamster,
        lizard,
        snake
    }

    private PetOption petOption;

    public List<PetOption> getAllValues() {
        List<PetOption> petOptions = new ArrayList<>();
        for (PetOption petOption : PetOption.values()) {
            petOptions.add(petOption);
        }

        return petOptions;
    }

}
