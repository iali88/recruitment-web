package fr.d2factory.libraryapp.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.book.ISBN;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepositoryImpl implements BookRepository {
    
	private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
        //TODO implement the missing feature
    }

    public Book findBook(long isbnCode) {
        //TODO implement the missing feature
        return null;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        //TODO implement the missing feature
    }

    public LocalDate findBorrowedBookDate(Book book) {
        //TODO implement the missing feature
        return null;
    }
}
