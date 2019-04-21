package com.eddy.bookworm;

import com.eddy.data.models.BookEntity;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Converts an iterable to a list
     *
     * @param iterable Iterable instance
     *
     * @return List of tye <E>
     */
    public static List<BookEntity> toList(Iterable<DataSnapshot> iterable) {
        ArrayList<BookEntity> list = new ArrayList<>();
        if (iterable != null) {
            for (DataSnapshot dataSnapshot : iterable) {
                list.add(dataSnapshot.getValue(BookEntity.class));
            }
        }
        return list;
    }
}
