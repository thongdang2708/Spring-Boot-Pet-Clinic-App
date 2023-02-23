package com.ltp.PetClinic.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name must be filled!")
    private String name;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address must be filled!")
    private String address;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City must be filled!")
    private String city;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Phone must be filled!")
    private String phone;

    @JsonIgnore
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pet = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Visit> visit = new ArrayList<>();

    public Owner(Long id, String name, String address, String city, String phone, User user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.user = user;
    }

    public Owner() {
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Pet> getPet() {
        return this.pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    public List<Visit> getVisit() {
        return this.visit;
    }

    public void setVisit(List<Visit> visit) {
        this.visit = visit;
    }

}
