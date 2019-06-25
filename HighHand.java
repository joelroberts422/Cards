package Cards;

import java.util.ArrayList;
import java.util.List;

public class HighHand {

	private List<Card> highCard;
	
	public static void main(String[] args) {
		ArrayList<Card> test = new ArrayList<Card>();
		test.add(new Card("clubs", 3));
		test.add(new Card("clubs", 4));
		test.add(new Card("clubs", 5));
		test.add(new Card("clubs", 6));
		test.add(new Card("clubs", 7));
		
		System.out.println(test);
		System.out.println(isStraight(test));
		
	}
	
	public static int[] multiples(ArrayList<Card> c) {
		
		// size of ret may vary
		int[] ret = new int[5];
		ret[0] = -1;
		
		// find derivative of sorted hand
		boolean[] type = new boolean[4];
		for (int i = 0; i < 4; ++i) {
			if (c.get(i).getValue() == c.get(i+1).getValue()) type[i] = true;
			else type[i] = false;
		}
		
		// count trues
		int tcount = 0;
		for (boolean b : type) if (b == true) ++tcount;
		
		// No multiples, return flagged 
		if (tcount == 0) return ret;
		
		// Pair
		if (tcount == 1) {
			ret[0] = 7;
			int loc = -1;
			for (int i = 0; i < 4; ++i) {
				if (type[i]) {
					loc = i;
					break;
				}
			}
			ret[1] = c.get(loc).getValue();
			int index = 4;
			for (int i = 0; i < 5; ++i) {
				if (loc != i && loc+1 != i) {
					ret[index] = c.get(i).getValue();
					--index;
				}
			}
			
			return ret;
		}
		
		// Trips or two pair
		if (tcount == 2) {
			int loc = -1;
			for (int i = 0; i < 4; ++i) {
				if(type[i]) {
					loc = i;
					// trips
					if (type[i+1]) ret[0] = 5;
					// two pair
					else ret[0] = 6;
					break;
				}
			}
			
			if (ret[0] == 5) ret[1] = c.get(loc).getValue();
			else {
				ret[1] = c.get(3).getValue();
				ret[2] = c.get(1).getValue();
				if (!type[0]) ret[3] = c.get(0).getValue();
				else if (!type[3]) ret[3] = c.get(4).getValue();
				else ret[3] = c.get(2).getValue();
			}
			return ret;
		}
		
		// Four of a kind or full house
		if (tcount == 3) {
			// four of a kind
			if (!type[0] || !type[3]) {
				ret[0] = 1;
				ret[1] = c.get(0).getValue();
			} else { // full house
				ret[0] = 2;
				ret[1] = c.get(2).getValue();
			}
			return ret;
		}
		
		return ret;
	}
	
	// Straight of flush
	public static int[] sflush(ArrayList<Card> c) {
		int[] ret = new int[6];
		
		if (isFlush(c)) {
			if (isStraight(c)) {
				ret[0] = 0;
				ret[1] = c.get(0).getValue();
				return ret;
			} else {
				for (int i = 0; i < 5 ; ++i)
					ret[i+2] = c.get(4-i).getValue();
				return ret;
			}
		}
		
		if (isStraight(c)) ;
		return ret;
		
		
	}
	
	private static boolean isStraight(ArrayList<Card> c) {
		for (int i = 0; i < 3; ++i) 
			if (c.get(i).getValue() != c.get(i+1).getValue() - 1) return false;
		return true;
	}
	
	private static boolean isFlush(ArrayList<Card> c) {
		for (int i = 0; i < 3; ++i) 
			if (!c.get(i).getSuit().equals(c.get(i+1).getSuit())) return false;
		return true;
	}
}
