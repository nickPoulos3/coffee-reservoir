import java.util.*;

public class Player {
	private String name;
	private Hand hisHand;
	private int chips;

	public Player(String s) {
		name = s.toUpperCase();
		hisHand = new Hand();
		chips = 2000;
	}

	public String getName() {
		return name;
	}
	public int getChips() {
		return chips;
	}
	public Hand getHand() {
		return hisHand;
	}
	public void drawCard(Deck d) {
		getHand().addCard(d.takeCard());
	}
	public void showHand() {
		System.out.println(name + " reveals...");
		getHand().printCards();
	}
	public void shoutBestHand() {
		System.out.println(name + " shows " + expandHand());
	}
	public String expandHand() {
		int hand = Arrays.asList(Hand.winningHands).indexOf(getHand().getBestHand());
		Card c = new Card();
		ArrayList<Integer> dropSomething = new ArrayList<Integer>();
		String expanded = getHand().getBestHand();
		switch (hand) {
			case 0:
				expanded += " of " + getHand().flushSuit();
				break;
			case 1:
				c.setRank(getHand().straightHigh());
				expanded += " of " + getHand().flushSuit() + ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 2:
				c.setRank(getHand().winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 3:
				c.setRank(getHand().winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s") + " Full of ";
				c.setRank(getHand().kickerRank());
				expanded += c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 4:
				c.setRank(getHand().highestSuited(dropSomething, getHand().flushSuit()));
				expanded += " of " + getHand().flushSuit() + ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 5:
				c.setRank(getHand().straightHigh());
				expanded += ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 6:
				c.setRank(getHand().winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 7:
				c.setRank(getHand().winningRank());
				expanded += " of " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s") + " and ";
				c.setRank(getHand().kickerRank());
				expanded += c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 8:
				c.setRank(getHand().winningRank());
				expanded += " of " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 9:
				c.setRank(getHand().highestCard(dropSomething));
				expanded = c.writeRank(c.getRank()) + " High";
				break;
			default:
				expanded = "his cards...?";
				break;
		}
		return expanded;
	}
	public void bet(int num) {
		chips -= num;
		System.out.println(name + " bets " + num + " chips");
		HoldEmRunner.addPot(num);
	}
	public void earnChips(int num) {
		System.out.println(name + " rakes in " + num + " chips");
		chips += num;
	}
}