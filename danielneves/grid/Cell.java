package com.danielneves.grid;

import com.danielneves.main.Game;

/**
 * Cuida de cada c�luca individual do grid
 * @author Daniel Neves
 *
 */
public class Cell {

	public boolean state = false;
	/**
	 * Inicia a c�lula com um valor booleano
	 * @param x
	 * @param y
	 */
	
	public Cell(int x , int y) {
		if(Game.rand.nextInt(100) < 10) {
			state = true;
		}
	}
}
