package com.ltp.PetClinic.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.PetClinic.entity.Pet;
import com.ltp.PetClinic.entity.PetType;
import com.ltp.PetClinic.exception.ErrorResponse;
import com.ltp.PetClinic.exception.InvalidInputDueToEnum;
import com.ltp.PetClinic.service.PetService;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{petId}/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<Pet> getSinglePet(@PathVariable Long petId, @PathVariable Long ownerId) {

        return new ResponseEntity<>(petService.getSinglePet(petId, ownerId), HttpStatus.OK);
    }

    @PostMapping("/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<Pet> addPet(@PathVariable Long ownerId, @Valid @RequestBody Pet pet) {

        return new ResponseEntity<>(petService.addPet(ownerId, pet), HttpStatus.CREATED);
    }

    @PutMapping("/{petId}/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<Pet> updatePet(@PathVariable Long petId, @PathVariable Long ownerId,
            @Valid @RequestBody Pet pet) {

        return new ResponseEntity<>(petService.updatePet(petId, ownerId, pet), HttpStatus.OK);
    }

    @DeleteMapping("/{petId}/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<HttpStatus> deletePet(@PathVariable Long petId, @PathVariable Long ownerId) {
        petService.deletePet(petId, ownerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<List<Pet>> getPetsByOwnerId(@PathVariable Long ownerId) {

        return new ResponseEntity<>(petService.getAllPetsByOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Pet>> getAllPetsByAdmin() {

        return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
    }

}
