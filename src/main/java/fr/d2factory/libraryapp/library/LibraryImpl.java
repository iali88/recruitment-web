package fr.d2factory.libraryapp.library;

import java.time.LocalDate;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.member.Member;

public class LibraryImpl implements Library {

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnBook(Book book, Member member) {
		// TODO Auto-generated method stub
		
	}

}
