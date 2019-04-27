package com.eddy.domain.mappers;

import com.eddy.data.models.BookEntity;
import com.eddy.domain.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookMapper {

    public Book transform(BookEntity bookEntity) {
        Book book = new Book();
        book.setKey(bookEntity.getKey());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(bookEntity.getAuthor());
        book.setBookImageUrl(bookEntity.getBookImageUrl());
        book.setDescription(bookEntity.getDescription());
        book.setPublisher(bookEntity.getPublisher());
        book.setRankLastWeek(bookEntity.getRankLastWeek());
        book.setRankThisWeek(bookEntity.getRankThisWeek());
        book.setWeeksOnList(bookEntity.getWeeksOnList());

        return book;
    }

    public List<Book> transform(Collection<BookEntity> bookEntities) {
        List<Book> books = new ArrayList<>();

        if (bookEntities != null) {
            for(BookEntity bookEntity: bookEntities) {
                Book book = transform(bookEntity);
                if (book != null) {
                    books.add(book);
                }
            }
        }

        return books;
    }
}
