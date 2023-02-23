package com.ltp.PetClinic.service;

import com.ltp.PetClinic.entity.Owner;

import java.util.List;

public interface OwnerService {
    Owner getOwner(Long id);

    Owner addOwner(Owner owner);

    Owner updatedOwner(Long id, Owner owner);

    void deleteOwner(Long id);

    List<Owner> getListOfOwner();
}
