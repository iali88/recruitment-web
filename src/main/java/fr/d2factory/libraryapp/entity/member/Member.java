package fr.d2factory.libraryapp.entity.member;

import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    
	private String firstName ;
	
	private String lastName;
	
	private List<Book> borrowedBooks;
	
	private static final float price = 0.10F;
	
	public Member(String firstName, String lastName, List<Book> borrowedBooks, float wallet) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.borrowedBooks = borrowedBooks;
		this.wallet = wallet;
	}
	
	/**
     * An initial sum of money the member has
     */
    private float wallet;

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(List<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
	
	public static float getPrice() {
		return price;
	}
    
}
