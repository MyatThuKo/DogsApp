package com.example.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.model.DogDao;
import com.example.dogsapp.model.DogDatabase;
import com.example.dogsapp.model.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    //If these variables are private, use getter and setter methods.
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private DogsApiService dogsService = new DogsApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveTask;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        fetchFromDatabase();
    }

    private void fetchFromRemote() {
        loading.setValue(true);
        disposable.add(
                dogsService.getDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> dogBreeds) {
                                insertTask = new InsertDogsTask();
                                insertTask.execute(dogBreeds);
                                Toast.makeText(getApplication(), "Dogs retrieved from endpoint", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                dogLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchFromDatabase() {
        loading.setValue(true);
        retrieveTask = new RetrieveDogsTask();
        retrieveTask.execute();
    }

    private void dogsRetrieved(List<DogBreed> dogList) {
        dogs.setValue(dogList);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }

    private class InsertDogsTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];
            DogDao dao = DogDatabase.getInstance(getApplication()).dogDao();
            dao.deleteAllDogs();

            ArrayList<DogBreed> newList = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            dogsRetrieved(dogBreeds);
        }
    }

    private class RetrieveDogsTask extends AsyncTask<Void, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).dogDao().getAllDogs();
        }

        //List<DogBreed> dogBreeds in the following onPostExecute method is from the above doInBackground method. (From Database)
        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            dogsRetrieved(dogBreeds);
            Toast.makeText(getApplication(), "Dogs retrieved from database.", Toast.LENGTH_LONG).show();
        }
    }
}
