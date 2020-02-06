package fr.d2factory.libraryapp.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.d2factory.libraryapp.dao.BookRepository;
import fr.d2factory.libraryapp.dao.BookRepositoryImpl;
import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.book.ISBN;
import fr.d2factory.libraryapp.entity.member.Resident;
import fr.d2factory.libraryapp.entity.member.Student;
import fr.d2factory.libraryapp.entity.member.Year;
import fr.d2factory.libraryapp.service.BookService;
import fr.d2factory.libraryapp.service.BookServiceImpl;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {
    
	private BookRepository bookRepository;
	private BookService bookService;
	private Library lib;
    private static List<Book> books;


    @BeforeEach
    void setup() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        File booksJson = new File("src/test/resources/books.json");
        books = mapper.readValue(booksJson, new TypeReference<List<Book>>() {
        });
        
        bookRepository = new BookRepositoryImpl();
		bookService = new BookServiceImpl(bookRepository);
		lib = new LibraryImpl(bookService);
		lib.getBookService().addBooks(books);
    }

    @Test
    void member_can_borrow_a_book_if_book_is_available(){
    	
    	Student john = new Student("John", "Doe", new ArrayList<Book>(), 100, Year.L1);
		LocalDate date1 = LocalDate.now();
		Book book = lib.borrowBook(3326456467846L, john, date1);
		assertNotNull(book);
		assertEquals(book.getTitle(), "Around the world in 80 days");
		assertEquals(book.getIsbn().getIsbnCode(), 3326456467846L);
    }

    @Test
    void borrowed_book_is_no_longer_available(){
    	Student john = new Student("John", "Doe", new ArrayList<Book>(), 100, Year.L1);
		Map<ISBN, Book> availableBooks = bookService.getAvailableBooks();
		int initialBooksCount = availableBooks.size();
		Book book = lib.borrowBook(3326456467846L, john, LocalDate.now());
		Book book2 = lib.borrowBook(3326456467846L, john, LocalDate.now());
		Book book3 = availableBooks.get(new ISBN(3326456467846L));

		assertTrue(book3 == null);
		assertTrue(book2 == null);
		assertTrue(availableBooks.size() == initialBooksCount - 1);
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
    	
    	Resident john = new Resident("Johnny", "Cage", new ArrayList<Book>(), 100);
		LocalDate date = LocalDate.now().minusDays(10);
		float walletBefore = john.getWallet();
		Book book = lib.borrowBook(3326456467846L, john, date);
		lib.returnBook(book, john);
		float walletAfter = john.getWallet();

		assertEquals(walletAfter, walletBefore - 1.0F);
		assertEquals(99F, walletAfter);
    }

    @Test
    void students_pay_10_cents_the_first_30days(){
    	
    	Student john = new Student("John", "Doe", new ArrayList<Book>(), 100, Year.L2);
		LocalDate date = LocalDate.now().minusDays(30);
		float walletBefore = john.getWallet();
		Book book = lib.borrowBook(46578964513L, john, date);
		lib.returnBook(book, john);
		float walletAfter = john.getWallet();

		assertEquals(walletAfter, walletBefore - 3.0F);
		assertEquals(97F, walletAfter);
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days(){
    	
    	Student john = new Student("John", "Doe", new ArrayList<Book>(), 100, Year.L1);
		LocalDate date = LocalDate.now().minusDays(15);
		float walletBefore = john.getWallet();
		Book book = lib.borrowBook(3326456467846L, john, date);
		lib.returnBook(book, john);
		float walletAfter = john.getWallet();

		assertEquals(walletAfter, walletBefore);
		assertEquals(100F, walletAfter);
    }
    
    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
    	
    	Resident john = new Resident("Johnny", "Cage", new ArrayList<Book>(), 100);
		LocalDate date = LocalDate.now().minusDays(70);
		float walletBefore = john.getWallet();
		Book book = lib.borrowBook(3326456467846L, john, date);
		lib.returnBook(book, john);
		float walletAfter = john.getWallet();

		assertEquals(walletAfter, walletBefore - (60 * 0.10F) - 2.0F);
		assertEquals(92.0F, walletAfter);
    }

    @Test
    void members_cannot_borrow_book_if_they_have_late_books(){
    	
    	Student john = new Student("John", "Doe", new ArrayList<Book>(), 100, Year.L1);
		LocalDate date = LocalDate.now().minusDays(31);
		Book book = lib.borrowBook(3326456467846L, john, date);

		HasLateBooksException thrown = assertThrows(HasLateBooksException.class, () -> {
			lib.borrowBook(3326456467846L, john, date);
		});

		assertTrue(thrown.getMessage().equals("Membre John Doe poss�de des livres � rendre!"));
    }
}
