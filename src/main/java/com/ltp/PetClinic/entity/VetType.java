package com.ltp.PetClinic.entity;

import com.ltp.PetClinic.entity.PetType.PetOption;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VetType {

    enum VetOption {
        cardiology,
        neurology,
        oncology,
        nutrition
    }

    private VetOption vetOption;

    public List<VetOption> getAllValues() {

        List<VetOption> vetOptions = new ArrayList<>();

        for (VetOption vetOption : VetOption.values()) {
            vetOptions.add(vetOption);
        }

        return vetOptions;
    }

    public VetOption searchWithOption(String type) {

        for (int i = 0; i < VetOption.values().length; i++) {
            if (VetOption.values()[i].name().contains(type.toLowerCase())) {
                return VetOption.values()[i];
            }
        }

        return null;
    }
}
