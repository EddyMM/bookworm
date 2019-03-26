package com.eddy.bookworm;

import com.eddy.data.models.Book;
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
    public static List<Book> toList(Iterable<DataSnapshot> iterable) {
        ArrayList<Book> list = new ArrayList<>();
        if (iterable != null) {
            for (DataSnapshot dataSnapshot : iterable) {
                list.add(dataSnapshot.getValue(Book.class));
            }
        }
        return list;
    }
}
