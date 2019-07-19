package com.eddy.data.errors;

public class BooksListSyncThrowable extends Throwable {

    private BooksListSyncThrowable(String message) {
        super(message);
    }

    public BooksListSyncThrowable() {
        this("Problem fetching books");
    }
}
