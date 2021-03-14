package com.danielneves.grid;

import java.awt.Color;
import java.awt.Graphics;

import com.danielneves.main.Game;
import com.danielneves.main.InputHandler;

/**
 * Local de maior interação do programa
 * @author Daniel Neves
 *
 */
public class Grid {
	public Game game;
	public InputHandler input;
	
	public int delay;
	
	public int resolution = 8;

	public int cols = Game.WIDTH / resolution;
	public int rows = Game.HEIGHT / resolution;
	
	public Cell[][] grid = new Cell[cols][rows];
	
	/**
	 * Recebe os parâmetros para se conectar com o input do mouse e teclado
	 * @param game
	 * @param input
	 */
	public Grid(Game game , InputHandler input) {
		for(int i = 0 ; i < cols ; i++) {
			for(int j = 0 ; j < rows ; j++) {
				grid[i][j] = new Cell(i , j);
			}
		}
		
		this.game = game;
		this.input = input;
	}
	/**
	 * Corre por todo array {@code grid} de tipo {@link Cell} e verifica as condições de cada célula e atualiza
	 * o array na variável {@code next} 
	 */
	
	public void tick() {
		delay++;
		
		Cell[][] next = new Cell[cols][rows];

		for(int i = 0 ; i < cols ; i++) {
			for(int j = 0 ; j < rows ; j++) {
				next[i][j] = new Cell(i , j);
			}
		}
		
		for(int i = 0 ; i < cols ; i++) {
			for(int j = 0 ; j < rows ; j++) {
				boolean state = grid[i][j].state;
				
				int neighbors = countNeighbors(grid, i, j);
				
				if(!grid[i][j].state && neighbors == 3) {
					next[i][j].state = !state;
				}else if(grid[i][j].state && (neighbors < 2 || neighbors > 3)) {
					next[i][j].state = !state;
				}else {
					next[i][j].state = state;
				}
			}
		}
		
		grid = next;
		
		if(delay > 30 && input.start.down) {
			delay = 0;
			Game.gameState = "EDIT";
		}
		if(delay > 1000) delay = 100;
	}
	
	public int countNeighbors(Cell[][] grid , int x , int y) {
		int sum = 0;
		for(int i = -1 ; i < 2 ; i++) {
			for(int j = -1 ; j < 2 ; j++) {
				
				int col = (x + i + cols) % cols;
				int row = (y + j + rows) % rows;
				
				if(i == 0 && j == 0) {
					continue;
				}else {
					if(grid[col][row].state) {
						sum++;
					}
				}
			}
		}
		
		return sum;
	}
	
	public void render(Graphics g) {
		for(int i = 0 ; i < cols ; i++) {
			for(int j = 0 ; j < rows ; j++) {
				int x = i * resolution;
				int y = j * resolution;
				
				g.setColor(Color.darkGray);
				g.drawRect(x , y , resolution , resolution);
				
				if(grid[i][j].state) {
					g.setColor(Color.white);
					g.fillRect(x, y, resolution, resolution);
				}
			}
		}
	}
}
