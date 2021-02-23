package com.wvapp.webviewapp.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Delete
    void delete(Person person);

    @Update
    void update(Person person);

    @Query("Delete From person_table")
    void deleteAllPersons();

    @Query("Select * From person_table Order By id Desc")
    LiveData<List<Person>> getAllPersons();
}
