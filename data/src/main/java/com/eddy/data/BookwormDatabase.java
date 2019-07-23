package com.eddy.data;

import android.content.Context;

import com.eddy.data.dao.BookDao;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BuyLink;
import com.eddy.data.models.entities.Category;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Category.class, BuyLink.class},
        version = 1)
public abstract class BookwormDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "bookworm_database";
    private static BookwormDatabase BOOKWORM_DATABASE;

    public static BookwormDatabase getInstance(Context context) {
        if (BOOKWORM_DATABASE == null) {
            synchronized (LOCK) {
                BOOKWORM_DATABASE = Room.databaseBuilder(
                        context, BookwormDatabase.class, DB_NAME).build();
            }
        }

        return BOOKWORM_DATABASE;
    }

    public abstract BookDao booksDao();
}
