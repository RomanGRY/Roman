package com.wvapp.webviewapp.DB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository personRepository;
    private LiveData<List<Person>> allPersons;

    @Inject
    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository=new PersonRepository(application);
        allPersons=personRepository.getAllPersons();
    }

    public void insert(Person person){
        personRepository.insert(person);
    }

    public void delete(Person person){
        personRepository.delete(person);
    }

    public void update(Person person){
        personRepository.update(person);
    }

    public void deleteAllPersons(){
        personRepository.deleteAllPersons();
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }
}
