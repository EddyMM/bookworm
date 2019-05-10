package com.eddy.data;

import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.BooksListRepository;

import org.junit.Test;

import java.util.List;

public class TestBooksRepository {

    @Test
    public void testFetchBooks() {
        MockBooksApiService mockBooksApiService = new MockBooksApiService();
        BooksListRepository booksListRepository = new BooksListRepository(
                mockBooksApiService
        );

        List<Book> bookList = booksListRepository.fetchBooks("test_category");
        List<Book> originalBooks = mockBooksApiService
                .generateTestBookResults()
                .getBooks();

        assert originalBooks.equals(bookList);
    }
}
