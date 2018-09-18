import java.util.*;

public class Player {
	private String name;
	private Hand hisHand;
	private int chips;
	private final double BLUFF_MOD = Math.random() / 4;

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
		hisHand.addCard(d.takeCard());
	}
	public void showHand() {
		System.out.println(name + " reveals...");
		hisHand.printCards();
	}
	public void shoutBestHand() {
		System.out.println(name + " shows " + expandHand());
	}
	public String expandHand() {
		int hand = Arrays.asList(Hand.WINNING_HANDS).indexOf(hisHand.getBestHand());
		Card c = new Card();
		ArrayList<Integer> dropSomething = new ArrayList<Integer>();
		String expanded = hisHand.getBestHand();
		switch (hand) {
			case 0:
				expanded += " of " + hisHand.flushSuit();
				break;
			case 1:
				c.setRank(hisHand.straightHigh());
				expanded += " of " + hisHand.flushSuit() + ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 2:
				c.setRank(hisHand.winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 3:
				c.setRank(hisHand.winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s") + " Full of ";
				c.setRank(hisHand.kickerRank());
				expanded += c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 4:
				c.setRank(hisHand.highestSuited(dropSomething, hisHand.flushSuit()));
				expanded += " of " + hisHand.flushSuit() + ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 5:
				c.setRank(hisHand.straightHigh());
				expanded += ", " + c.writeRank(c.getRank()) + " High";
				break;
			case 6:
				c.setRank(hisHand.winningRank());
				expanded += ", " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 7:
				c.setRank(hisHand.winningRank());
				expanded += " of " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s") + " and ";
				c.setRank(hisHand.kickerRank());
				expanded += c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 8:
				c.setRank(hisHand.winningRank());
				expanded += " of " + c.writeRank(c.getRank()) + (c.getRank() == 6? "es" : "s");
				break;
			case 9:
				c.setRank(hisHand.highestCard(dropSomething));
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
	public int decideBet() {
		double bluffCheck = Math.random();
		if (bluffCheck <= BLUFF_MOD) return chips;
		else return (10 - Arrays.asList(Hand.WINNING_HANDS).indexOf(hisHand.getBestHand())) * 100;
	}
	public void earnChips(int num) {
		System.out.println(name + " rakes in " + num + " chips");
		chips += num;
	}
}