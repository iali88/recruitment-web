package fr.d2factory.libraryapp.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import fr.d2factory.libraryapp.dao.BookRepository;
import fr.d2factory.libraryapp.dao.BookRepositoryImpl;
import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.book.ISBN;
import fr.d2factory.libraryapp.entity.member.Member;
import fr.d2factory.libraryapp.entity.member.Resident;
import fr.d2factory.libraryapp.library.HasLateBooksException;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.library.LibraryImpl;
import fr.d2factory.libraryapp.service.BookService;
import fr.d2factory.libraryapp.service.BookServiceImpl;

public class Main {

	public static void main(String[] args) {

		BookRepository bookRepository = new BookRepositoryImpl();
		BookService bookService = new BookServiceImpl(bookRepository);
		Library lib = new LibraryImpl(bookService);

		//Student bob = new Student("Bob", "Smith", new ArrayList<Book>(), 100, Year.L1);
		Member john = new Resident("John", "Silva", new ArrayList<Book>(), 100);
		
		ArrayList<Book> books = new ArrayList<Book>();
		books.add(new Book("Harry Potter", "J.K. Rowling", new ISBN(465789645L)));
		books.add(new Book("Around the world in 80 days", "Jules Verne", new ISBN(123456789L)));
		books.add(new Book("Catch 22", "Joseph Heller", new ISBN(332645646L)));

		bookService.addBooks(books);

		afficherAvailableBooks(bookService.getAvailableBooks());

		LocalDate date1 = LocalDate.now();
		date1 = date1.minusDays(70);
		Book b = lib.borrowBook(123456789L, john, date1);

		System.out.println("Emprunt succes : " + b.getTitle() + " a été emprunté");
		System.out.println("Wallet = " + john.getWallet());

		afficherAvailableBooks(bookService.getAvailableBooks());
		afficherBorrowedBooks(bookService.getBorrowedBooks());

		System.out.println("Wallet = " + john.getWallet());

		System.out.println("Tentative de location d'un deuxieme livre");
		
		try {
		
			lib.borrowBook(332645646L, john, LocalDate.now());
		
		} catch (HasLateBooksException e) {
			System.out.println("Impossible le membre possède des livres à rendre.");
		}
		
		afficherBorrowedBooks(bookService.getBorrowedBooks());
		
		lib.returnBook(b, john);
		System.out.println("Wallet = " + john.getWallet());
		lib.borrowBook(332645646L, john, LocalDate.now());
		
		afficherBorrowedBooks(bookService.getBorrowedBooks());
		afficherAvailableBooks(bookService.getAvailableBooks());

	}

	private static void afficherBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		if(borrowedBooks != null) {
			System.out.println("Liste de livres empruntés : ");
			for (Entry<Book, LocalDate> b : borrowedBooks.entrySet()) {
				System.out.println("\t" + ((Book) b.getKey()).getTitle());
			}
		}

	}

	private static void afficherAvailableBooks(Map<ISBN, Book> availableBooks) {
		if(availableBooks != null) {
			System.out.println("Liste de livres disponible : ");
			for (Entry<ISBN, Book> isbn : availableBooks.entrySet()) {
				System.out.println("\t" + ((Book) isbn.getValue()).getTitle());
			}
		}

	}

}
