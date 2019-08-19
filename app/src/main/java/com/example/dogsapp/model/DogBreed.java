package com.example.dogsapp.model;

import com.google.gson.annotations.SerializedName;

public class DogBreed {

    @SerializedName("id")
    public String breedID;

    @SerializedName("name")
    public String dogBreed;

    @SerializedName("life_span")
    public String lifeSpan;

    @SerializedName("breed_group")
    public String breedGroup;

    @SerializedName("bred_for")
    public String bredFor;

    @SerializedName("temperament")
    public String temperament;

    @SerializedName("url")
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
