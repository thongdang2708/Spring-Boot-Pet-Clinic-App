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
import java.util.List;
import com.ltp.PetClinic.entity.Vet;
import com.ltp.PetClinic.entity.VetStatus;
import com.ltp.PetClinic.exception.ErrorResponse;
import com.ltp.PetClinic.repository.VetJPARepository;
import com.ltp.PetClinic.service.PetService;
import com.ltp.PetClinic.service.VetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vet")
public class VetController {

    @Autowired
    private VetService vetService;

    @Autowired
    private VetJPARepository vetJPARepository;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Vet> getVet(@PathVariable Long id) {

        return new ResponseEntity<>(vetService.getVet(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Vet> addVet(@Valid @RequestBody Vet vet) {

        return new ResponseEntity<>(vetService.addVet(vet), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Vet> updateVet(@PathVariable Long id, @Valid @RequestBody Vet vet) {

        return new ResponseEntity<>(vetService.updateVet(id, vet), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<HttpStatus> deleteVet(@PathVariable Long id) {
        vetService.updateChildrenTableFirst(id);
        vetService.deleteVet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Vet>> getAllVets() {

        return new ResponseEntity<>(vetService.getAllVets(), HttpStatus.OK);
    }

    @GetMapping("/search/{status}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Vet>> getVetsByStatus(@PathVariable String status) {

        return new ResponseEntity<>(vetService.getVetsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/search/type/{type}")
    public ResponseEntity<List<Vet>> getVetsByType(@PathVariable String type) {

        return new ResponseEntity<>(vetService.getVetsByType(type), HttpStatus.OK);
    }

}
