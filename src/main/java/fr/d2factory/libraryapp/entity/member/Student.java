package fr.d2factory.libraryapp.entity.member;

import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;

public class Student extends Member {

	private static final long rentLimit = 30;
	
	private Year year;
	
	public Student(String firstName, String lastName, List<Book> borrowedBooks, float wallet, Year year) {
		super(firstName, lastName, borrowedBooks, wallet);
		this.year = year;
	}
	
	public static long getRentLimit() {
		return rentLimit;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	@Override
	public void payBook(int numberOfDays) {
		// TODO Auto-generated method stub
		
	}
	
	
}
