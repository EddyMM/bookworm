package com.eddy.bookworm.models.mappers;

import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.domain.Book;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParcelableBookMapper {

    public ParcelableBook transform(Book book) {
        ParcelableBook parcelableBook = new ParcelableBook();
        parcelableBook.setKey(book.getKey());
        parcelableBook.setTitle(book.getTitle());
        parcelableBook.setAuthor(book.getAuthor());
        parcelableBook.setBookImageUrl(book.getBookImageUrl());
        parcelableBook.setDescription(book.getDescription());
        parcelableBook.setPublisher(book.getPublisher());
        parcelableBook.setRankLastWeek(book.getRankLastWeek());
        parcelableBook.setRankThisWeek(book.getRankThisWeek());
        parcelableBook.setWeeksOnList(book.getWeeksOnList());

        return parcelableBook;
    }

    public List<ParcelableBook> transform(Collection<Book> bookEntities) {
        List<ParcelableBook> parcelableBooks = new ArrayList<>();

        for(Book book: bookEntities) {
            ParcelableBook parcelableBook = transform(book);
            if (parcelableBook != null) {
                parcelableBooks.add(parcelableBook);
            }
        }

        return parcelableBooks;
    }
}
