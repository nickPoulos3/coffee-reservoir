import java.util.*;

public class Deck {
	protected ArrayList<Card> cards = new ArrayList<Card>();

	public Deck(boolean fresh) {
		if (fresh) {
			for (int i = 0; i < Card.allSuits.length; i++) {
				for (int j = 1; j < 14; j++) {
					cards.add(new Card(Card.allSuits[i], j));
				}
			}
		}
	}
	public Deck(Card c) {
		cards.add(c);
	}

	public void addAnotherDeck() {
		for (int i = 0; i < Card.allSuits.length; i++) {
			for (int j = 1; j < 14; j++) {
				cards.add(new Card(Card.allSuits[i], j));
			}
		}
		shuffle();
	}
	public ArrayList<Card> getDeck() {
		return cards;
	}
	public int getSize() {
		return cards.size();
	}
	public void addCard(Card c) {
		cards.add(c);
	}
	public void burnTopCard() {
		cards.remove(cards.size()-1);
	}
	public void removeCard(Card c) {
		cards.remove(c);
	}
	public Card takeCard() {
		return cards.remove(cards.size()-1);
	}
	public void shuffle() {
		for (int i = 0; i < 1000; i++) {
			Card c = cards.remove((int)(Math.random()*52));
			cards.add(c);
		}
	}
	public void printCards() {
		for (int i = 0; i < getDeck().size(); i++) {
			Card c = getDeck().get(i);
			System.out.println(c.getName());
		}
	}
	public void clear() {
		cards.clear();
	}
}