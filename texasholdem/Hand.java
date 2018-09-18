import java.util.*;

public class Hand extends Deck {
	public static final String[] WINNING_HANDS = {"Royal Flush", "Straight Flush", "Four of a Kind", "Full House", "Flush", "Straight", "Three of a Kind", "Two Pair", "One Pair", "High Card"};
	private String bestHand = "";

	public Hand() {
		super(false);
	}

	public String getBestHand() {
		checkForWins();
		return bestHand;
	}
	public void sortHandBySuit() {
		Card c;
		int compare;
		for (int i = 1; i < cards.size(); i++) {
			c = cards.get(i);
			compare = i-1;
			while (compare >= 0 && c.getSuit().compareTo(cards.get(compare).getSuit()) >= 0) compare--;
			cards.remove(i);
			cards.add(compare+1, c);
		}
		for (int i = 1; i < cards.size(); i++) {
			c = cards.get(i);
			compare = i;
			while (compare <= cards.size() && c.getSuit().equals(cards.get(compare).getSuit()) && c.getRank() < cards.get(compare).getRank()) {
				compare--;
			}
			cards.remove(i);
			cards.add(compare, c);
		}
	}
	public void sortHandByRank() {
		Card c;
		int compare;
		for (int i = cards.size()-2; i >= 0; i--) {
			c = cards.get(i);
			compare = i;
			while (compare != cards.size() && c.getRank() >= cards.get(compare).getRank()) compare++;
			cards.remove(i);
			cards.add(compare-1, c);
		}
	}
	public int highestCard(ArrayList<Integer> nopes) {
		int max = 0; boolean ace = false;
		for (int i = 0; i < cards.size(); i++) {
			if (!nopes.contains(cards.get(i).getRank())) {
				max = Math.max(max, cards.get(i).getRank());
				if (cards.get(i).getRank() == 1) ace = true;
			}
		}
		return ace? 14 : max;
	}
	private boolean checkFlush() {
		sortHandBySuit();
		int consecutive = 1;
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getSuit().equals(cards.get(i).getSuit())) consecutive++;
			else consecutive = 1;
			if (consecutive == 5) return true;
		}
		return false;
	}
	public String flushSuit() {
		sortHandBySuit();
		int consecutive = 1;
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getSuit().equals(cards.get(i).getSuit())) consecutive++;
			else consecutive = 1;
			if (consecutive == 5) return cards.get(i).getSuit();
			}
		return "";
	}
	public int highestSuited(ArrayList<Integer> nopes, String suit) {
		int max = 0; boolean ace = false;
		for (int i = 0; i < cards.size(); i++) {
			if (!nopes.contains(cards.get(i).getRank()) && cards.get(i).getSuit() == suit) {
				max = Math.max(max, cards.get(i).getRank());
				if (cards.get(i).getRank() == 1 &&  cards.get(i).getSuit() == suit) ace = true;
			}
		}
		return ace? 14 : max;
	}
	private boolean checkStraight() {
		int consecutive = 1;
		sortHandByRank();
		if (cards.get(0).getRank() == 1) addCard(new Card(14));
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getRank() - cards.get(i).getRank() == 1) consecutive++;
			else if (cards.get(i+1).getRank() - cards.get(i).getRank() != 0) consecutive = 1;
			if (consecutive == 5) {
				if (cards.get(0).getRank() == 1) cards.remove(cards.size()-1);
			return true;
			}
		}
		if (cards.get(0).getRank() == 1) cards.remove(cards.size()-1);
		return false;
	}
	public int straightHigh() {
		int consecutive = 1; int max = 0;
		sortHandByRank();
		if (cards.get(0).getRank() == 1) addCard(new Card(14));
		addCard(new Card(21));
		for (int i = cards.size()-1; i >= 1; i--) {
			if (cards.get(i).getRank() - cards.get(i-1).getRank() == 1) consecutive++;
			else if (cards.get(i).getRank() - cards.get(i-1).getRank() != 0) consecutive = 1;
			if (consecutive == 5) {
				takeCard();
				max = cards.get(i+3).getRank();
				if (cards.get(0).getRank() == 1) takeCard();
				return max;
			}
		}
		takeCard();
		if (cards.get(0).getRank() == 1) takeCard();
		return 0;
	}
	public int maxSameRank() {
		int consecutive = 1; int max = 0;
		addCard(new Card(21));
		sortHandByRank();
		for (int  i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getRank() == cards.get(i).getRank()) consecutive++;
			else {
				max = Math.max(max, consecutive);
				consecutive = 1;
			}
		}
		takeCard();
		return max;
	}
	public int secondMax() {
		int consecutive = 1; int max = 0;
		addCard(new Card(21));
		for (int i = 0; i < cards.size()-1; i++) {
			if (winningRank() == 14 && cards.get(i).getRank() == 1) consecutive = 1;
			else if (cards.get(i+1).getRank() == cards.get(i).getRank() && cards.get(i).getRank() != winningRank()) consecutive++;
			else {
				max = Math.max(max, consecutive);
				consecutive = 1;
			}
		}
		takeCard();
		return max;
	}
	public int winningRank() {
		int consecutive = 1; int rank = 0;
		addCard(new Card(21));
		sortHandByRank();
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getRank() == cards.get(i).getRank()) consecutive++;
			else {
				if (consecutive == maxSameRank()) rank = Math.max(rank, cards.get(i).getRank() == 1? 14 : cards.get(i).getRank());
				consecutive = 1;
			}
		}
		takeCard();
		return rank;
	}
	public int kickerRank() {
		int consecutive = 1; int rank = 0;
		addCard(new Card(21));
		sortHandByRank();
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i+1).getRank() == cards.get(i).getRank() && cards.get(i).getRank() != winningRank() && (winningRank() != 14 || cards.get(i).getRank() != 1)) consecutive++;
			else {
				if (consecutive == secondMax() && cards.get(i).getRank() != winningRank() && (winningRank() != 14 || cards.get(i).getRank() != 1)) rank = Math.max(rank, cards.get(i).getRank() == 1? 14 : cards.get(i).getRank());
				consecutive = 1;
			}
		}
		takeCard();
		return rank;
	}
	public void checkForWins() {
		if (checkFlush() && checkStraight() && straightHigh() == 14) bestHand = "Royal Flush";
		else if (checkFlush() && checkStraight()) bestHand = "Straight Flush";
		else if (maxSameRank() == 4) bestHand = "Four of a Kind";
		else if (maxSameRank() == 3 && secondMax() == 2) bestHand = "Full House";
		else if (checkFlush()) bestHand = "Flush";
		else if (checkStraight()) bestHand = "Straight";
		else if (maxSameRank() == 3) bestHand = "Three of a Kind";
		else if (maxSameRank() == 2 && secondMax() == 2) bestHand = "Two Pair";
		else if (maxSameRank() == 2) bestHand = "One Pair";
		else bestHand = "High Card";
	}
}