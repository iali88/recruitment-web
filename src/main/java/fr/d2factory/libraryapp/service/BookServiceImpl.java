package fr.d2factory.libraryapp.service;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.dao.BookRepository;
import fr.d2factory.libraryapp.entity.book.Book;

public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@Override
	public void addBooks(List<Book> books) {
		bookRepository.addBooks(books);
	}

	@Override
	public Book findBook(long isbnCode) {
		return bookRepository.findBook(isbnCode);
	}

	@Override
	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		bookRepository.saveBookBorrow(book, borrowedAt);
	}

	@Override
	public LocalDate findBorrowedBookDate(Book book) {
		return bookRepository.findBorrowedBookDate(book);
	}

}
