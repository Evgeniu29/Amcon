package com.paad.amconsoft.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.paad.amconsoft.model.User;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface UserDao {


    @Delete
    void delete(User... users);


    //Insert one item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User... user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllUsers(List<User> users);


    // Delete one item
    @Delete
    void deleteUser(User user);



    //Get all items
    @Query("SELECT * FROM user")
    List<User> getAllData();

    //Delete All
    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user WHERE name = :name")
    User getUser(String name);

}