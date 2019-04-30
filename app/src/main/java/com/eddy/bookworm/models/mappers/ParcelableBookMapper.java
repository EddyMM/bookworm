package com.eddy.bookworm.models.mappers;

import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.data.models.entities.Book;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParcelableBookMapper {

    private ParcelableBook transform(Book book) {
        ParcelableBook parcelableBook = new ParcelableBook();
        parcelableBook.setKey(book.getKey());
        parcelableBook.setTitle(book.getTitle());
        parcelableBook.setAuthor(book.getAuthor());
        parcelableBook.setBookImageUrl(book.getBookImageUrl());
        parcelableBook.setBookImageWidth(book.getBookImageWidth());
        parcelableBook.setBookImageHeight(book.getBookImageHeight());
        parcelableBook.setDescription(book.getDescription());
        parcelableBook.setPublisher(book.getPublisher());
        parcelableBook.setRankLastWeek(book.getRankLastWeek());
        parcelableBook.setRankThisWeek(book.getRankThisWeek());
        parcelableBook.setWeeksOnList(book.getWeeksOnList());
        parcelableBook.setReviewsUrl(book.getReviewUrl());
        parcelableBook.setBuyingLinks(
                new ParcelableBuyLinkMapper().transform(book.getBuyLinks()));

        return parcelableBook;
    }

    public List<ParcelableBook> transform(Collection<Book> books) {
        List<ParcelableBook> parcelableBooks = new ArrayList<>();

        if (books != null) {
            for(Book book: books) {
                ParcelableBook parcelableBook = transform(book);
                if (parcelableBook != null) {
                    parcelableBooks.add(parcelableBook);
                }
            }
        }

        return parcelableBooks;
    }
}
