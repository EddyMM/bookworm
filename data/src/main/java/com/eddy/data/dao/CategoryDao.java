package com.eddy.data.dao;

import com.eddy.data.models.entities.Category;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategories(List<Category> categories);

    @Query("SELECT * from category")
    LiveData<List<Category>> getCategories();

    @Query("SELECT COUNT(*) FROM category")
    Integer countCategories();
}
