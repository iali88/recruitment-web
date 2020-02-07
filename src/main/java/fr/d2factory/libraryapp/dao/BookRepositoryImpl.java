package fr.d2factory.libraryapp.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.book.ISBN;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepositoryImpl implements BookRepository {

	private Map<ISBN, Book> availableBooks = new HashMap<>();
	
	private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

	/**
	 * Add books to the map availableBooks.
	 * 
	 * @param books a list containing books
	 */
	public void addBooks(List<Book> books) {
		
		// if not empty
		if (books != null && !books.isEmpty()) {
			// adding only non null element of the list
			books.stream().filter(Objects::nonNull).forEach(book -> {
				availableBooks.put(book.getIsbn(), book);
			});
		}
	}

	/**
	 * Search a book by ISBN code in 'availableBooks' list Return null if book is
	 * not found
	 * 
	 * @param isbnCode the isbn code
	 * @return the book we are searching for, null if not found
	 */
	public Book findBook(long isbnCode) {
		
		Book book = null;

		if (availableBooks != null && !availableBooks.isEmpty()) {

			book = availableBooks.get(new ISBN(isbnCode));

		}

		return book;
	}

	/**
	 * Register a borrowed book by adding to the hashmap borrowedBooks The borrowed
	 * book is then no longer available in availableBooks
	 * 
	 * @param book       the borrowed book
	 * @param borrowedAt borrowing date
	 */
	public void saveBookBorrow(Book book, LocalDate borrowedAt) {

		if (book != null && borrowedAt != null) {

			borrowedBooks.put(book, borrowedAt);

			// book is no longer available to borrow
			availableBooks.remove(book.getIsbn());
		}

	}

	/**
	 * Search for borrowing date of a book
	 * 
	 * @param book the borrowed book
	 */
	public LocalDate findBorrowedBookDate(Book book) {

		LocalDate date = null;

		if (book != null) {
			date = borrowedBooks.get(book);
		} else {
			System.out.println("book.isbn == null or book == null");
		}

		return date;
	}
	
	/**
	 * Restitute a book.
	 * 
	 * @param book the book to restitute
	 */
	public void restituteBook(Book book) {

		// si liste non vide
		if (book != null) {

			borrowedBooks.remove(book);
			// book is now available
			availableBooks.put(book.getIsbn(), book);
		}

	}

	/**
	 * Return borrowed books map
	 */
	public Map<Book, LocalDate> getBorrowedBooks() {
		return this.borrowedBooks;
	}

	/**
	 * Return available books map
	 */
	public Map<ISBN, Book> getAvailableBooks() {
		return this.availableBooks;
	}
}
