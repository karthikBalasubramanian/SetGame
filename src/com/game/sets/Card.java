package com.game.sets;

/**
 * SetGame.java
 * Purpose: to create a Card object. 
 * 			
 *
 * @author Karthik Balasubramanian
 * @version 1.0 09/16/17
 */
public class Card {

    int color;
    int symbol;
    int shading;
    int number;
    String actualString;

    /**
	 * Constructor 
	 * 				1. to create a card object based on the arguments 
	 * 				
	 * @param color, Integer.
	 * @param symbol, Integer
	 * @param shading, Integer,
	 * @param number, Integer
	 * @param actualString, Integer
	 * @return None
	 */
	
    public Card(int color, int symbol, int shading, int number, String actualString){
        this.color =  color;
        this.symbol = symbol;
        this.shading =  shading;
        this.number = number;
        this.actualString = actualString;
    }
    
    /**
     *  toString method is overridden to return object's actualString ex : "green $$$"
     *  @param None
     *  @return  actualString, String
     */
    @Override
    public String toString(){
    	return actualString;
    }
}
