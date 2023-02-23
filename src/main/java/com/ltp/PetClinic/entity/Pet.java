package com.ltp.PetClinic.entity;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltp.PetClinic.entity.PetType.PetOption;

import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Past(message = "Data must be in the past!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NonNull
    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "pet_type")
    private PetOption petOption;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Visit> visit = new ArrayList<>();

    public Pet(Long id, Date date, PetOption petOption, Owner owner) {
        this.id = id;
        this.date = date;
        this.petOption = petOption;
        this.owner = owner;
    }

    public Pet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PetOption getPetOption() {
        return this.petOption;
    }

    public void setPetOption(PetOption petOption) {
        this.petOption = petOption;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Visit> getVisit() {
        return this.visit;
    }

    public void setVisit(List<Visit> visit) {
        this.visit = visit;
    }

}
