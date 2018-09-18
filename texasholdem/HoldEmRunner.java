import java.util.*;

public class HoldEmRunner {
	private static ArrayList<Player> table = new ArrayList<Player>();
	private static int pot;
	private static String input;
    private static Hand community = new Hand();

	public static void main(String[] args) {
		Player p1 = new Player("alfonzo");
		Player p2 = new Player("bugsy");
		Player p3 = new Player("charlie");
		Player p4 = new Player("don");
		Player p5 = new Player("ernesto");
		table.add(p1); table.add(p2); table.add(p3); table.add(p4); table.add(p5);
		Deck d1 = new Deck(true);
		d1.shuffle(); d1.burnTopCard();
		deal(d1, 2);
        dealWide(d1, "flop");
        dealWide(d1, "turn");
        dealWide(d1, "river");
        System.out.println("Community cards are:");
        for (Card c : community.cards) {
            System.out.println(c.getName());
        }
        for (Player p : table) {
            p.bet(p.decideBet());
        }
		/*p1.getHand().addCard(new Card("Spades", 4));
		p1.getHand().addCard(new Card("Spades", 6));
		p1.getHand().addCard(new Card("Diamonds", 8));
		p1.getHand().addCard(new Card("Spades", 9));
		p1.getHand().addCard(new Card("Hearts", 10));
		p1.getHand().addCard(new Card("Hearts", 12));
		p1.getHand().addCard(new Card("Diamonds", 13));

		p2.getHand().addCard(new Card("Spades", 2));
		p2.getHand().addCard(new Card("Spades", 3));
		p2.getHand().addCard(new Card("Spades", 8));
		p2.getHand().addCard(new Card("Clubs", 9));
		p2.getHand().addCard(new Card("Clubs", 10));
		p2.getHand().addCard(new Card("Diamonds", 12));
		p2.getHand().addCard(new Card("Spades", 13));
		for (Player p : table) {
			System.out.print(p.getHand().highestCard(new ArrayList<Integer>()) + " ");
			System.out.print(p.getHand().maxSameRank() + " ");
			System.out.print(p.getHand().winningRank() + " ");
			System.out.print(p.getHand().secondMax() + " ");
			System.out.println(p.getHand().kickerRank());
		}*/
		for (Player p : table) {
            p.shoutBestHand();
        }
		decideWinner();
        for (Player p : table) {
            for (int i = p.getHand().cards.size() - 1; i >= 0; i--) {
                if (community.cards.contains(p.getHand().cards.get(i))) p.getHand().removeCard(p.getHand().cards.get(i));
            }
        }
		System.out.println("See cards? Type \"Show.\" Otherwise type \"Exit.\"");
		Scanner s1 = new Scanner(System.in);
		input = s1.nextLine();
		if (input.trim().toLowerCase().equals("show")) {
            p1.showHand(); p2.showHand(); p3.showHand(); p4.showHand(); p5.showHand();
        } else if (input.trim().toLowerCase().equals("exit")) {
            System.exit(0);
        }
		s1.close();
	}

	public static void deal(Deck d, int n) {
        System.out.printf("Dealing %d cards to each player...%n", n);
		for (Player p : table) {
			for (int i = 0; i < n; i++) {

				if (d.getSize() == 0) d.addAnotherDeck();
				p.drawCard(d);
			}
		p.getHand().sortHandByRank();
		}
	}
    public static void dealWide(Deck d, String s) {
        System.out.printf("Dealing the %s...%n", s);
        Card compare;
        switch (s) {
        case "flop":
            for (int i = 0; i < 3; i++) {
                compare = d.takeCard();
                community.addCard(compare);
                for (Player p: table) {
                    p.getHand().addCard(compare);
                }
            }
            break;
        case "turn":
            compare = d.takeCard();
            community.addCard(compare);
            for (Player p : table) {
                p.getHand().addCard(compare);
            }
            break;
        case "river":
            compare = d.takeCard();
            community.addCard(compare);
            for (Player p : table) {
                p.getHand().addCard(compare);
            }
            break;
        default:
            System.out.println("Huh?");
            break;
        }
    }

	public static void printPot() {
		System.out.println("Pot is " + pot + " chips");
	}
	public static void addPot(int num) {
		pot += num;
	}
	public static void awardPot(Player p, int chips) {
		p.earnChips(chips);
		pot = 0;
	}
	public static void decideWinner() {
		int bestValue = 11; int temp; int totalPot = pot;
		Player winner = table.get(0); ArrayList<Player> winners = new ArrayList<Player>();
		String hand = "High Card"; String bestHand = "High Card";
		ArrayList<Integer> listOfTies = new ArrayList<Integer>(21);
		for (Player p : table) {
			temp = bestValue;
			hand = p.getHand().getBestHand();
			bestValue = Math.min(bestValue, Arrays.asList(Hand.WINNING_HANDS).indexOf(hand));
			if (bestValue != temp) {
				winner = p;
				bestHand = p.getHand().getBestHand();
			}
		}
		winners.add(winner);
		for (Player p : table) {
			if (p.getHand().getBestHand().equals(bestHand) && !p.getName().equals(winner.getName())) {
				compare(bestValue, winners.get(0), p, winners, listOfTies);
			}
		}
		if (winners.size() > 1) System.out.println("Even tie: " + pot/winners.size() + " chips to each winner");
		for (Player p : winners) {
			System.out.println(p.getName() + " wins with " + bestHand /* + ": " + someMethodInHandThatGivesHandDataAsString*/);
			awardPot(p, totalPot/winners.size());
		}
	}
	public static void compare(int hand, Player winner, Player check, ArrayList<Player> winners, ArrayList<Integer> skipTies) {
		switch (hand) {
			case 0:
				winners.add(check);
				break;
			case 1:
				if (winner.getHand().straightHigh() > check.getHand().straightHigh());
				else if (winner.getHand().straightHigh() < check.getHand().straightHigh()) {winners.remove(winner); winners.add(check);}
				else winners.add(check);
				break;
			case 2:
				if (winner.getHand().winningRank() > check.getHand().winningRank());
				else if (winner.getHand().winningRank() < check.getHand().winningRank()) {winners.remove(winner); winners.add(check);}
				else {
					if (winner.getHand().kickerRank() > check.getHand().kickerRank());
					else if (winner.getHand().kickerRank() < check.getHand().kickerRank()) {winners.remove(winner); winners.add(check);}
					else winners.add(check);
				}
				break;
			case 3:
				if (winner.getHand().winningRank() > check.getHand().winningRank());
				else if (winner.getHand().winningRank() < check.getHand().winningRank()) {winners.remove(winner); winners.add(check);}
				else {
					if (winner.getHand().kickerRank() > check.getHand().kickerRank());
					else if (winner.getHand().kickerRank() < check.getHand().kickerRank()) {winners.remove(winner); winners.add(check);}
					else winners.add(check);
				}
				break;
			case 4:
				if (skipTies.size() == 5) {winners.add(check); skipTies.clear();}
				else if (winner.getHand().highestSuited(skipTies, winner.getHand().flushSuit()) > check.getHand().highestSuited(skipTies, check.getHand().flushSuit())) skipTies.clear();
				else if (winner.getHand().highestSuited(skipTies, winner.getHand().flushSuit()) < check.getHand().highestSuited(skipTies, check.getHand().flushSuit())) {winners.remove(winner); winners.add(check); skipTies.clear();}
				else {
					skipTies.add(winner.getHand().highestSuited(skipTies, winner.getHand().flushSuit()));
					compare(hand, winner, check, winners, skipTies);
				}
				break;
			case 5:
				if (winner.getHand().straightHigh() > check.getHand().straightHigh());
				else if (winner.getHand().straightHigh() < check.getHand().straightHigh()) {winners.remove(winner); winners.add(check);}
				else winners.add(check);
				break;
			case 6:
				if (winner.getHand().winningRank() > check.getHand().winningRank()) skipTies.clear();
				else if (winner.getHand().winningRank() < check.getHand().winningRank()) {winners.remove(winner); winners.add(check);}
				else if (skipTies.size() == 3) {winners.add(check); skipTies.clear();}
				else {
					if (!skipTies.contains(winner.getHand().winningRank())) skipTies.add(winner.getHand().winningRank());
					if (winner.getHand().highestCard(skipTies) > check.getHand().highestCard(skipTies)) skipTies.clear();
					else if (winner.getHand().highestCard(skipTies) < check.getHand().highestCard(skipTies)) {winners.remove(winner); winners.add(check); skipTies.clear();}
					else {
						skipTies.add(winner.getHand().highestCard(skipTies));
						compare(hand, winner, check, winners, skipTies);
					}
				}
				break;
			case 7:
				if (winner.getHand().winningRank() > check.getHand().winningRank());
				else if (winner.getHand().winningRank() < check.getHand().winningRank()) {winners.remove(winner); winners.add(check);}
				else {
					skipTies.add(winner.getHand().winningRank());
					if (winner.getHand().kickerRank() > check.getHand().kickerRank()) skipTies.clear();
					else if (winner.getHand().kickerRank() < check.getHand().kickerRank()) {winners.remove(winner); winners.add(check); skipTies.clear();}
					else {
						skipTies.add(winner.getHand().kickerRank());
						if (winner.getHand().highestCard(skipTies) > check.getHand().highestCard(skipTies)) skipTies.clear();
						else if (winner.getHand().highestCard(skipTies) < check.getHand().highestCard(skipTies)) {winners.remove(winner); winners.add(check); skipTies.clear();}
						else {winners.add(check); skipTies.clear();}
					}
				}
				break;
			case 8:
				if (winner.getHand().winningRank() > check.getHand().winningRank()) skipTies.clear();
				else if (winner.getHand().winningRank() < check.getHand().winningRank()) {winners.remove(winner); winners.add(check);}
				else if (skipTies.size() == 3) {winners.add(check); skipTies.clear();}
				else {
					if (!skipTies.contains(winner.getHand().winningRank())) skipTies.add(winner.getHand().winningRank());
					if (winner.getHand().highestCard(skipTies) > check.getHand().highestCard(skipTies)) skipTies.clear();
					else if (winner.getHand().highestCard(skipTies) < check.getHand().highestCard(skipTies)) {winners.remove(winner); winners.add(check); skipTies.clear();}
					else {
						skipTies.add(winner.getHand().highestCard(skipTies));
						compare(hand, winner, check, winners, skipTies);
					}
				}
				break;
			case 9:
				if (skipTies.size() == 5) {winners.add(check); skipTies.clear();}
				else if (winner.getHand().highestCard(skipTies) > check.getHand().highestCard(skipTies)) skipTies.clear();
				else if (winner.getHand().highestCard(skipTies) < check.getHand().highestCard(skipTies)) {winners.remove(winner); winners.add(check); skipTies.clear();}
				else {
					skipTies.add(winner.getHand().highestCard(skipTies));
					compare(hand, winner, check, winners, skipTies);
				}
				break;
			default:
				winners.add(check);
				break;
		}
	}
}
/*
 * 0 Royal Flush---
 * 1 Straight Flush---
 * 2 Four of a Kind---
 * 3 Full House---
 * 4 Flush---
 * 5 Straight
 * 6 Three of a Kind---
 * 7 Two Pair---
 * 8 One Pair---
 * 9 High Card---
 */