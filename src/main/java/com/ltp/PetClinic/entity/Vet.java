package com.ltp.PetClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltp.PetClinic.entity.VetStatus.VetCondition;
import com.ltp.PetClinic.entity.VetType.VetOption;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vet")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name must not be blank!")
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "vet_option", nullable = false)
    private VetOption vetOption;

    @NonNull
    @Column(name = "vet_condition", nullable = false)
    private VetCondition vetCondition;

    @JsonIgnore
    @OneToMany(mappedBy = "vet", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH }, orphanRemoval = false)
    private List<Visit> visit = new ArrayList<>();

    public Vet(Long id, String name, VetOption vetOption, VetCondition vetCondition) {
        this.id = id;
        this.name = name;
        this.vetOption = vetOption;
        this.vetCondition = vetCondition;
    }

    public Vet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VetOption getVetOption() {
        return this.vetOption;
    }

    public void setVetOption(VetOption vetOption) {
        this.vetOption = vetOption;
    }

    public VetCondition getVetCondition() {
        return this.vetCondition;
    }

    public void setVetCondition(VetCondition vetCondition) {
        this.vetCondition = vetCondition;
    }

    public List<Visit> getVisit() {
        return this.visit;
    }

    public void setVisit(List<Visit> visit) {
        this.visit = visit;
    }

}
