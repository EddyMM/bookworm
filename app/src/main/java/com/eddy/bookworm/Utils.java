package com.eddy.bookworm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.eddy.data.models.Book;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

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

    public static boolean isConnected(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            isConnected = networkInfo.isConnected();
        }

        return isConnected;
    }
}
