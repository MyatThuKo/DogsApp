package com.example.dogsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogsapp.model.DogBreed;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<DogBreed>();

    public void fetch() {
        DogBreed dog = new DogBreed("1", "Corgi", "15 years", "", "Companionship", "Calm and friendly", "");
        dogLiveData.setValue(dog);
    }
}
