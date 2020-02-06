package fr.d2factory.libraryapp.service;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;

public interface BookService {

	public void addBooks(List<Book> books);

    public Book findBook(long isbnCode);

    public void saveBookBorrow(Book book, LocalDate borrowedAt); 
    
    public LocalDate findBorrowedBookDate(Book book);
    
}
