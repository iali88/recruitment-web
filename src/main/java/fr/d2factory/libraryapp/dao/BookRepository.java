package fr.d2factory.libraryapp.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.book.ISBN;

public interface BookRepository {
	
	public void addBooks(List<Book> books);

    public Book findBook(long isbnCode);

    public void saveBookBorrow(Book book, LocalDate borrowedAt); 
    
    public LocalDate findBorrowedBookDate(Book book);
    
    public void restituteBook(Book book);

	public Map<ISBN, Book> getAvailableBooks();

	public Map<Book, LocalDate> getBorrowedBooks();

}
