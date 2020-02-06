package fr.d2factory.libraryapp.entity.member;

import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;

public class Resident extends Member {

	private static final long rentLimit = 60;
	
	private static final float latePenalty = 0.20F;
	
	public Resident(String firstName, String lastName, List<Book> borrowedBooks, float wallet) {
		super(firstName, lastName, borrowedBooks, wallet);
	}

	@Override
	public void payBook(int numberOfDays) {
		// TODO Auto-generated method stub
		
	}
	
	public static long getRentLimit() {
		return rentLimit;
	}

	public static float getLatePenalty() {
		return latePenalty;
	}

}
