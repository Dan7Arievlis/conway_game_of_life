package com.danielneves.grid;

import com.danielneves.main.Game;
import com.danielneves.main.InputHandler;

public class EditMode {

	public Game game;
	public InputHandler input;
	
	public int delay;
	
	public int mX , mY;
	
	public EditMode(Game game , InputHandler input) {
		this.game = game;
		this.input = input;
	}
	
	public void tick() {
		delay++; 
		
		if(input.button1.down) {
			mX = (input.mx/(Game.SCALE))/Game.grid.resolution;
			mY = (input.my/(Game.SCALE))/Game.grid.resolution;
			
			if(!Game.grid.grid[mX][mY].state) {
				Game.grid.grid[mX][mY].state = !Game.grid.grid[mX][mY].state;
			}
		}
		
		if(delay > 30 && input.start.down) {
			delay = 0;
			Game.gameState = "GRID";
		}
		if(delay > 1000) delay = 100;
	}
}
