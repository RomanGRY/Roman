package com.wvapp.webviewapp.DB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class PersonRepository {

    private PersonDao personDao;
    private LiveData<List<Person>> allPersons;

    @Inject
    public PersonRepository(Application application){
        PersonDatabase database=PersonDatabase.getInstance(application);
        personDao=database.personDao();
        allPersons=personDao.getAllPersons();
    }

    public void insert(Person person){
        new InsertOperationTask(personDao).execute(person);
    }

    public void delete(Person person){
        new DeleteOperationTask(personDao).execute(person);
    }

    public void update(Person person){
        new UpdateOperationTask(personDao).execute(person);
    }

    public void deleteAllPersons(){
        new DeleteAllOperationTask(personDao).execute();
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }

    private static class InsertOperationTask extends AsyncTask<Person,Void,Void>{

        private PersonDao personDao;

        private InsertOperationTask(PersonDao personDao){
            this.personDao=personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            personDao.insert(people[0]);
            return null;
        }
    }

    private static class DeleteOperationTask extends AsyncTask<Person,Void,Void>{

        private PersonDao personDao;

        private DeleteOperationTask(PersonDao personDao){
            this.personDao=personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            personDao.delete(people[0]);
            return null;
        }
    }

    private static class UpdateOperationTask extends AsyncTask<Person,Void,Void>{

        private PersonDao personDao;

        private UpdateOperationTask(PersonDao personDao){
            this.personDao=personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            personDao.update(people[0]);
            return null;
        }
    }

    private static class DeleteAllOperationTask extends AsyncTask<Void,Void,Void>{

        private PersonDao personDao;

        private DeleteAllOperationTask(PersonDao personDao){
            this.personDao=personDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            personDao.deleteAllPersons();
            return null;
        }
    }
}
