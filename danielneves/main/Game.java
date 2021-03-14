package com.danielneves.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import com.danielneves.grid.EditMode;
import com.danielneves.grid.Grid;

/**
 *Início do jogo do Conway's Game of Life
 * 
 * @author Daniel Neves
 * @version 1.0
 *
 */

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static Random rand;
	private InputHandler input = new InputHandler(this);
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
	
	private BufferedImage image;
	
	public static String gameState;
	
	public static EditMode editMode;
	public static Grid grid;
	/**
	 * Construtor do Jogo: 
	 * inicializa variáveis e a tela da aplicação
	 * @author Daniel Neves
	 */
	public Game() {
		rand = new Random();
//		input = new InputHandler(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		gameState = "EDIT";
		editMode = new EditMode(this , this.input);
		grid = new Grid(this , input);
	}
	
	public void initFrame(){
		frame = new JFrame("Conway's Game of Life");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		switch(gameState) {
		case "EDIT":
			editMode.tick();
			break;
		case "GRID":
			grid.tick();
			break;
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0 , 0 , 0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		grid.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}
}
