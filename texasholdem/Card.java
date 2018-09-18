import java.util.*;

public class Card {
	private String suit;
	public static final String[] allSuits = {"Spades", "Hearts", "Diamonds", "Clubs"};
	private int rank;
	private String name;

	public Card(String s, int r) {
		if (Arrays.asList(allSuits).contains(s)) suit = s;
		rank = r;
		name = writeRank(rank) + " of " + suit;
	}
	public Card(String s) {
		if (Arrays.asList(allSuits).contains(s)) suit = s;
		else suit = Arrays.asList(allSuits).get((int)(Math.random()*4));
		rank  = (int)(1+Math.random()*13);
		name = writeRank(rank) + " of " + suit;
	}
	public Card(int r) {
		suit = Arrays.asList(allSuits).get((int)(Math.random()*4));
		rank = r;
		name = writeRank(rank) + " of " + suit;
	}
	public Card() {
		suit = Arrays.asList(allSuits).get((int)(Math.random()*4));
		rank = (int)(1+Math.random()*13);
		name = writeRank(rank) + " of " + suit;
	}

	public String getSuit() {
		return suit;
	}
	public int getRank() {
		return rank;
	}
	public void setSuit(String s) {
		if (Arrays.asList(allSuits).contains(s)) suit = s;
	}
	public void setRank(int r) {
		rank = r;
	}
	public String getName() {
		return name;
	}
	public String writeRank(int numerical) {
		String written;
		switch(numerical) {
			case 1: written = "Ace";
				break;
			case 2: written = "Two";
				break;
			case 3: written = "Three";
				break;
			case 4: written = "Four";
				break;
			case 5: written = "Five";
				break;
			case 6: written = "Six";
				break;
			case 7: written = "Seven";
				break;
			case 8: written = "Eight";
				break;
			case 9: written = "Nine";
				break;
			case 10: written = "Ten";
				break;
			case 11: written = "Jack";
				break;
			case 12: written = "Queen";
				break;
			case 13: written = "King";
				break;
			case 14: written = "Ace";
				break;
			default: written = Integer.toString(numerical);
				break;
		}
		return written;
	}
}