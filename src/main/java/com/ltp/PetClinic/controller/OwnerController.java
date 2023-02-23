package com.ltp.PetClinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.ltp.PetClinic.entity.Owner;
import com.ltp.PetClinic.service.OwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public ResponseEntity<Owner> addOwner(@Valid @RequestBody Owner owner) {

        return new ResponseEntity<>(ownerService.addOwner(owner), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Owner> getOwner(@PathVariable Long id) {

        return new ResponseEntity<>(ownerService.getOwner(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @Valid @RequestBody Owner owner) {

        return new ResponseEntity<>(ownerService.updatedOwner(id, owner), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<HttpStatus> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Owner>> getAllUsers() {

        return new ResponseEntity<>(ownerService.getListOfOwner(), HttpStatus.OK);
    }

}
