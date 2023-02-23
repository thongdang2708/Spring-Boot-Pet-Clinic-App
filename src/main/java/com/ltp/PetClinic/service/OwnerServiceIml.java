package com.ltp.PetClinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ltp.PetClinic.entity.Owner;
import com.ltp.PetClinic.entity.User;
import com.ltp.PetClinic.exception.OwnerNotFoundWithIdException;
import com.ltp.PetClinic.exception.UsernameExistsException;
import com.ltp.PetClinic.repository.OwnerRepository;
import com.ltp.PetClinic.repository.UserRepository;

@Service
public class OwnerServiceIml implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Owner getOwner(Long id) {
        Optional<Owner> owner = ownerRepository.findById(id);

        if (owner.isPresent()) {
            return owner.get();
        } else {
            throw new OwnerNotFoundWithIdException(id);
        }
    }

    @Override
    public Owner updatedOwner(Long id, Owner owner) {
        Owner updatedOwner = getOwner(id);

        updatedOwner.setName(owner.getName());
        updatedOwner.setAddress(owner.getAddress());
        updatedOwner.setCity(owner.getCity());
        updatedOwner.setPhone(owner.getPhone());

        return ownerRepository.save(updatedOwner);

    }

    @Override
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner addOwner(Owner owner) {

        if (!ownerRepository.existsOwnerByName(owner.getName())) {
            User user = authService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

            Owner createdOwner = ownerRepository.save(owner);

            addOwnerToUser(user.getId(), createdOwner.getId());

            return createdOwner;
        } else {
            throw new UsernameExistsException("This name exists already!");
        }

    }

    public void addOwnerToUser(Long userId, Long ownerId) {

        User user = authService.getUser(userId);
        Owner owner = getOwner(ownerId);

        user.setOwner(owner);
        owner.setUser(user);

        userRepository.save(user);
        ownerRepository.save(owner);

    }

    @Override
    public List<Owner> getListOfOwner() {
        return (List<Owner>) ownerRepository.findAll();
    }
}
