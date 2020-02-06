package fr.d2factory.libraryapp.service;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;

public class BookServiceImpl implements BookService {

	@Override
	public void addBooks(List<Book> books) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Book findBook(long isbnCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalDate findBorrowedBookDate(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

}
