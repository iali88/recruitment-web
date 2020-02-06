package fr.d2factory.libraryapp.entity.member;

import java.util.List;

import fr.d2factory.libraryapp.entity.book.Book;

public class Resident extends Member {

	/**
	 * Renting limit (60 days)
	 */
	private static final long rentLimit = 60;
	
	/**
	 * Penalty cost if late
	 */
	private static final float latePenalty = 0.20F;
	
	public Resident(String firstName, String lastName, List<Book> borrowedBooks, float wallet) {
		super(firstName, lastName, borrowedBooks, wallet);
	}

	/**
	 * Calculate the fees and set the member's balance 
	 */
	@Override
	public void payBook(int numberOfDays) {
		
		float total = calculateFees((float)numberOfDays);
		this.setWallet(getWallet() - total);
	}
	
	/**
	 * Calculation of the fees charged to the member
	 * @param numberOfDays number of days of the location
	 * @return total total price
	 */
	private float calculateFees(float numberOfDays) {
		
		float nbDaysLate;
		float nbDaysAllowed;
		float total;

		if (numberOfDays <= 60) {
			
			//nbDaysLate == 0 and nbDaysAllowed == numberOfDays;
			total = numberOfDays * getPrice();
			
		} else {
			
			nbDaysLate = numberOfDays - rentLimit;
			nbDaysAllowed = rentLimit;

			total = nbDaysAllowed * getPrice() + (nbDaysLate * latePenalty);
		}

		return total;
	}
	
	public static long getRentLimit() {
		return rentLimit;
	}

	public static float getLatePenalty() {
		return latePenalty;
	}

}
