package com.ltp.PetClinic.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.ltp.PetClinic.entity.VetType.VetOption;

@Getter
@Setter
public class VetStatus {
    enum VetCondition {
        active,
        inactive
    }

    VetCondition activeCondition = VetCondition.active;
    VetCondition inactiveCondition = VetCondition.inactive;

    private VetCondition vetCondition;

    public List<VetCondition> getAllValues() {

        List<VetCondition> vetConditions = new ArrayList<>();

        for (VetCondition vetCondition : VetCondition.values()) {
            vetConditions.add(vetCondition);
        }

        return vetConditions;
    }

    public VetCondition getActiveMode() {
        return activeCondition;
    }

    public VetCondition getInactiveMode() {
        return inactiveCondition;
    }

    public VetCondition searchByStatus(String status) {

        for (int i = 0; i < VetCondition.values().length; i++) {
            if (VetCondition.values()[i].name().contains(status.toLowerCase())) {
                return VetCondition.values()[i];
            }
        }

        return null;
    }

}
