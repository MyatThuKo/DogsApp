package com.example.dogsapp.model;

public class DogBreed {

    public String breedID;
    public String dogBreed;
    public String lifeSpan;
    public String breedGroup;
    public String bredFor;
    public String temperament;
    public String imageUrl;
    public int uuid;

    public DogBreed(String breedID, String dogBreed, String lifeSpan, String breedGroup, String bredFor, String temperament, String imageUrl) {
        this.breedID = breedID;
        this.dogBreed = dogBreed;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }
}
