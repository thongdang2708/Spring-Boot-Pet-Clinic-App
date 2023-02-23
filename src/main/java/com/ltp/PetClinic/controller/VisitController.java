package com.ltp.PetClinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ltp.PetClinic.entity.VetStatus;
import com.ltp.PetClinic.entity.Visit;
import com.ltp.PetClinic.service.PetService;
import com.ltp.PetClinic.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/owner/{ownerId}/pet/{petId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Visit> getVisit(@PathVariable Long ownerId, @PathVariable Long petId) {

        return new ResponseEntity<>(visitService.getVisit(ownerId, petId), HttpStatus.OK);
    }

    @PostMapping("/owner/{ownerId}/pet/{petId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Visit> addVisit(@PathVariable Long ownerId, @PathVariable Long petId,
            @Valid @RequestBody Visit visit) {

        return new ResponseEntity<>(visitService.addVisit(ownerId, petId, visit), HttpStatus.CREATED);
    }

    @PutMapping("/owner/{ownerId}/pet/{petId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Visit> updateVisit(@PathVariable Long ownerId, @PathVariable Long petId,
            @Valid @RequestBody Visit visit) {

        return new ResponseEntity<>(visitService.updateVisit(ownerId, petId, visit), HttpStatus.OK);
    }

    @DeleteMapping("/owner/{ownerId}/pet/{petId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<HttpStatus> deleteVisit(@PathVariable Long ownerId, @PathVariable Long petId) {
        visitService.deleteVisit(ownerId, petId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<List<Visit>> getVisitsByOwner(@PathVariable Long ownerId) {

        return new ResponseEntity<>(visitService.getAllVisitsOfOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<List<Visit>> getVisitsByPet(@PathVariable Long petId) {

        return new ResponseEntity<>(visitService.getAllVisitsOfPet(petId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Visit>> getAllVisits() {

        return new ResponseEntity<>(visitService.getAllVisits(), HttpStatus.OK);
    }

    @PutMapping("/{id}/vet/{vetId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Visit> assignVetToVisitByAdmin(@PathVariable Long id, @PathVariable Long vetId) {

        return new ResponseEntity<>(visitService.assignVetToVisitByAdmin(id, vetId), HttpStatus.OK);
    }
}
