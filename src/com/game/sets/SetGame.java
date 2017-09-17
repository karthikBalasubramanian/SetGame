package com.game.sets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * SetGame.java
 * Purpose: to simulate a SetGame : http://www.setgame.com/​ 
 * 			This program performs two functions 
 * 			1. finds and prints the number of possible SETs of three cards in the input, and
 * 			2. find and print the largest disjoint collection of SETs in the input 
 * 			
 *
 * @author Karthik Balasubramanian
 * @version 1.0 09/16/17
 */



public class SetGame {

	ArrayList<Card> listOfCards;
	HashMap<Object, Integer> map;
	
	/**
	 * Constructor 
	 * 				1. to create a default map of objects to convert the input into Card object format 
	 * 				2. initializes the list of Card objects. Input is loaded into this list.
	 * @param value, Integer.
	 */
	
	
	public SetGame() {
		listOfCards = new ArrayList<Card>();
		map = new HashMap<Object, Integer>();
		map.put("blue", 1);
		map.put("green", 2);
		map.put("yellow", 3);
		map.put(65, 1);
		map.put(83, 2);
		map.put(72, 3);
		map.put(97, 1);
		map.put(115, 2);
		map.put(104, 3);
		map.put(64, 1);
		map.put(36, 2);
		map.put(35, 3);

	}
	
	/**
	 * This method is used to find all possible three card set based on the criteria specified by isSet method.
	 * 
	 * @param listOfCards ArrayList<Card> - list object loaded from input. 
	 * @return allSets, ArrayList<ArrayList<Card>>  - list of all possible sets computed from input.
	 */

	public ArrayList<ArrayList<Card>> findSets(ArrayList<Card> listOfCards) {

		ArrayList<ArrayList<Card>> allSets = new ArrayList<ArrayList<Card>>();
		ArrayList<Card> currentSet = null;
		for (int i = 0; i < listOfCards.size(); i++) {
			Card one = listOfCards.get(i);
			for (int j = i + 1; j < listOfCards.size(); j++) {
				Card two = listOfCards.get(j);
				for (int k = j + 1; k < listOfCards.size(); k++) {
					Card three = listOfCards.get(k);
					if (isSet(one, two, three)) {
						currentSet = new ArrayList<Card>();
						currentSet.add(one);
						currentSet.add(two);
						currentSet.add(three);
						allSets.add(currentSet);

					}
				}
			}
		}

		System.out.println(allSets.size());

		return allSets;

	}

	/**
	 * This method is used to find all maximum possible disjoint set. 
	 * maximum possible disjoint set is a list of sets with same number of distinct disjoint sets. 
	 * function prints the # of such sets and it also prints the cards in those sets one by one. 
	 * 
	 * @param allSets ArrayList<ArrayList<Card>> - list of sets. 
	 * @return None
	 */
	public void disJointSets(ArrayList<ArrayList<Card>> allSets) {

		HashMap<Integer, ArrayList<Integer>> mapOfSetAndItsDisjoints = getMapOfSetAndItsDisJoints(allSets);

		HashMap<Integer, ArrayList<Integer>> sameDisjointSetsCount = new HashMap<Integer, ArrayList<Integer>>();

		for (Map.Entry<Integer, ArrayList<Integer>> entry : mapOfSetAndItsDisjoints.entrySet()) {

			int setIndex = entry.getKey();
			int countOfDisjointSets = entry.getValue().size();
			if (sameDisjointSetsCount.containsKey(countOfDisjointSets))
				sameDisjointSetsCount.get(countOfDisjointSets).add(setIndex);
			else {
				ArrayList<Integer> listOfSets = new ArrayList<Integer>();
				listOfSets.add(setIndex);
				sameDisjointSetsCount.put(countOfDisjointSets, listOfSets);
			}

		}

		int maxdisJointSetsCount = 0;
		int maxNumberOfKeysWithDisJointSetsCount = 0;

		for (Map.Entry<Integer, ArrayList<Integer>> entry : sameDisjointSetsCount.entrySet()) {
			int disJointSetsCount = entry.getKey();
			int numberOfKeysWithDisJointSetsCount = entry.getValue().size();
			if (numberOfKeysWithDisJointSetsCount > maxNumberOfKeysWithDisJointSetsCount) {
				maxdisJointSetsCount = disJointSetsCount;
				maxNumberOfKeysWithDisJointSetsCount = numberOfKeysWithDisJointSetsCount;
			}

		}

		System.out.println(maxNumberOfKeysWithDisJointSetsCount);
		System.out.println();

		for (int i : sameDisjointSetsCount.get(maxdisJointSetsCount)) {
			ArrayList<Card> getSet = allSets.get(i);
			for (Card card : getSet) {
				System.out.println(card.toString());
			}
			System.out.println();
		}

	}

	/**
	 * This method is used to get a map of a given set and its disjoint sets. 
	 *  
	 * 
	 * @param allSets ArrayList<ArrayList<Card>> - list of sets. 
	 * @return mapOfSetAndItsDisjoints, HashMap<Integer, ArrayList<Integer>>
	 */
	private HashMap<Integer, ArrayList<Integer>> getMapOfSetAndItsDisJoints(ArrayList<ArrayList<Card>> allSets) {
		HashMap<Integer, ArrayList<Integer>> mapOfSetAndItsDisjoints = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < allSets.size(); i++) {
			for (int j = 0; j < allSets.size(); j++) {
				if (i != j) {
					ArrayList<Card> currentSet = allSets.get(i);
					ArrayList<Card> comparedSet = allSets.get(j);

					HashSet<String> getObjectNames = new HashSet<String>();
					for (Card c : currentSet) {
						getObjectNames.add(c.toString());
					}
					for (Card c2 : comparedSet) {
						getObjectNames.add(c2.toString());
					}

					if (getObjectNames.size() == 6) {
						if (mapOfSetAndItsDisjoints.containsKey(i))
							mapOfSetAndItsDisjoints.get(i).add(j);
						else {
							ArrayList<Integer> disJointSets = new ArrayList<Integer>();
							disJointSets.add(j);
							mapOfSetAndItsDisjoints.put(i, disJointSets);
						}
					}

				}

			}
		}
		return mapOfSetAndItsDisjoints;
	}

	/**
	 * This method is used find if the triplet cards form a set 
	 *  
	 * 
	 * @param one, Card
	 * @param two, Card
	 * @param three, Card 
	 * @return true/false, boolean
	 */
	private boolean isSet(Card one, Card two, Card three) {

		if (!((one.number == two.number) && (two.number == three.number)
				|| (one.number != two.number) && (one.number != three.number) && (two.number != three.number))) {
			return false;
		}
		if (!((one.symbol == two.symbol) && (two.symbol == three.symbol)
				|| (one.symbol != two.symbol) && (one.symbol != three.symbol) && (two.symbol != three.symbol))) {
			return false;
		}
		if (!((one.shading == two.shading) && (two.shading == three.shading)
				|| (one.shading != two.shading) && (one.shading != three.shading) && (two.shading != three.shading))) {
			return false;
		}
		if (!((one.color == two.color) && (two.color == three.color)
				|| (one.color != two.color) && (one.color != three.color) && (two.color != three.color))) {
			return false;
		}
		return true;
	}

	/**
	 * This method is convert a line of card detail to Card Object and stores it in a list 
	 *  
	 * 
	 * @param line, String
	 * @return None
	 */
	public void loadCardIntoList(String line) {
		String[] cardAttributes = line.split("\\s");

		if (cardAttributes.length > 1) {
			int color = map.get(cardAttributes[0]);
			int number = cardAttributes[1].length();
			int ch = (int) cardAttributes[1].charAt(0);
			int symbol = map.get(ch);
			int shading;
			if (ch < 65) {
				shading = 3;
			} else if (ch >= 65 && ch < 97) {
				shading = 2;
			} else {
				shading = 1;
			}

			listOfCards.add(new Card(color, symbol, shading, number, line));
		}

	}
	
	/**
	 * This is the main method. Here is where program execution starts. 
	 * Input is read using Buffered read and each line is fed
	 * to loadCardIntoList  
	 * 
	 * @param args, String[]
	 * @return None
	 */

	public static void main(String[] args) {

		SetGame setGame = new SetGame();

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while ((line = br.readLine()) != null) {
				setGame.loadCardIntoList(line.trim());
			}

			
			ArrayList<ArrayList<Card>> allSets = setGame.findSets(setGame.listOfCards);
			setGame.disJointSets(allSets);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
