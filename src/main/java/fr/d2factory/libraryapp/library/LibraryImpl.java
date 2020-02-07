package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.entity.member.Member;
import fr.d2factory.libraryapp.entity.member.Resident;
import fr.d2factory.libraryapp.entity.member.Student;
import fr.d2factory.libraryapp.entity.member.Year;
import fr.d2factory.libraryapp.service.BookService;

public class LibraryImpl implements Library {

	/**
	 * Service to interact with the book repository
	 */
	private BookService bookService;

	public LibraryImpl(BookService bookService) {
		this.bookService = bookService;
	}
	
	public BookService getBookService() {
		return bookService;
	}

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {

		Book book = null;

		if (member == null || borrowedAt == null) {
			System.out.println("Invalid parameter member or borrowing date.");
			return null;
		}

		if (member instanceof Student || member instanceof Resident) {

			// has member already borrowed a book ?
			if (member.hasBorrowedBooks()) {
				if (hasLateBooks(member)) {
					throw new HasLateBooksException("Membre " + member.getFirstName() + " " + member.getLastName()
							+ " possède des livres à rendre!");
				}
			}

			// borrow the book
			// get the book by its isbn code
			book = bookService.findBook(isbnCode);

			// only if the book is available
			if(book != null) {
				// add to the member's list of borrowed book
				member.addBorrowedBook(book);
	
				// book is borrowed, Register the operation
				bookService.saveBookBorrow(book, borrowedAt);
			} else {
				System.out.println("The book is not available");
			}

		} else {
			System.out.println("You have to be a student or a resident to borrow a book.");
		}

		return book;
	}

	@Override
	public void returnBook(Book book, Member member) {

		if (member != null && book != null) {

			// remove the book from member's list
			member.removeBook(book);

			if (member instanceof Student) {

				calculateStudentFees((Student) member, book);

			} else if (member instanceof Resident) {

				calculateResidentFees((Resident) member, book);
			}
		} else {
			System.out.println("Invalid parameter member or book.");
		}
	}

	/**
	 * Calculate which price apply to student
	 * @param student student
	 * @param book the borrowed book 
	 */
	private void calculateStudentFees(Student student, Book book) {

		if (student != null && book != null) {

			LocalDate rentDate = bookService.findBorrowedBookDate(book);
			long nbDays = ChronoUnit.DAYS.between(rentDate, LocalDate.now());

			// check if first year
			if (Year.L1.equals(student.getYear())) {
				if (nbDays >= 15) {
					// deduct 15 days because its free
					student.payBook((int) nbDays - 15);
				} else {
					System.out.println("Free for 15 days for 1st year student.");
				}
			} else {
				// normal fees
				student.payBook((int) nbDays);
			}

			// register operation, book is returned and available
			bookService.restituteBook(book);

		} else {
			System.out.println("Invalid parameter member or book.");
		}
	}

	/**
	 * Calculate which price apply for resident
	 * @param resident the resident
	 * @param book the borrowed book
	 */
	private void calculateResidentFees(Resident resident, Book book) {

		if (resident != null && book != null) {

			LocalDate rentDate = bookService.findBorrowedBookDate(book);
			long nbDays = (int) ChronoUnit.DAYS.between(rentDate, LocalDate.now());

			// calculation
			resident.payBook((int) nbDays);

			// register operation, book is returned and available
			bookService.restituteBook(book);

		} else {
			System.out.println("Invalid parameter member or book.");
		}
	}

	/**
	 * Calculate if a member has at least one book late to return
	 * @param member the member
	 * @return true if he does, else false 
	 */
	private boolean hasLateBooks(Member member) {

		boolean res = false;
		long rentLimit = 0;

		if (member == null) {
			return false;
		}

		if (member instanceof Student) {

			rentLimit = Student.getRentLimit();

		} else if (member instanceof Resident) {

			rentLimit = Resident.getRentLimit();

		}

		// for each books, is there at least one book that must be returned ?
		for (Book book : member.getBorrowedBooks()) {

			LocalDate borrowDate = bookService.findBorrowedBookDate(book);
			long borrowedDuration = ChronoUnit.DAYS.between(borrowDate, LocalDate.now());

			if (borrowedDuration > rentLimit) {
				res = true;
				break;
			}
		}

		return res;
	}

}
