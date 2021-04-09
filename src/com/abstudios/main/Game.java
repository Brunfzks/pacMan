package com.abstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.abstudios.entities.EnemyBlue;
import com.abstudios.entities.EnemyPink;
import com.abstudios.entities.EnemyRed;
import com.abstudios.entities.EnemyOrange;
import com.abstudios.entities.Entity;
import com.abstudios.entities.Moeda;
import com.abstudios.entities.Player;
import com.abstudios.graficos.Spritesheet;
import com.abstudios.graficos.Ui;

import com.abstudios.world.World;


import java.awt.event.*;


public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 935073154186464789L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public final static int WIDTH = 513;
	public final static int HEIGHT = 513;
	public final static int SCALE = 2;

	public static int CUR_LEVEL = 1, MAX_CUR = 2;
	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Entity> enemis;
	public static Spritesheet spritesheet;
	public static World world;
	public static EnemyOrange yellow;
	public static EnemyBlue blue;
	public static EnemyRed red;
	public static EnemyPink green;

	public static int score = 0;
	public static int moedasContagem = 0;
	public static int moedasAtual = 0;

	public static Player player;

	public Ui[] ui;

	public static String gameState = "MENU";

	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;

	public Menu menu;

	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemis = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet40.png");
		elementsUi();
		player = new Player(0, 0, 32, 32, 1.2, spritesheet.getSprite(0, 80, 32, 32), 1);
		entities.add(player);
		world = new World("/level1.png");
		menu = new Menu();
	}
	
	public void initFrame() {
		
		frame = new JFrame("Meu jogo");
		frame.add(this);
		frame.setResizable(false);
		//frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	public void elementsUi() {
		ui = new Ui[2];
		ui[0] = new Ui(spritesheet.getSprite(960, 0, 20, 20));
		ui[1] = new Ui(spritesheet.getSprite(960, 40, 20, 20));
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		requestFocus();
		if(gameState == "NORMAL"){
			//Previne do jogar apertar enter e reiniciar o jogo no meio do jogo
			this.restartGame = false;

			for(int i = 0; i < ui.length;i++) {
				ui[i].tick();
			}
			
			for(int i = 0; i < entities.size(); i++) {
				
				Entity e = entities.get(i);
				e.tick();
			}
			
			// if(enemis.size() == 0) {
				
			// 	CUR_LEVEL++;
			// 	if(CUR_LEVEL > MAX_CUR) {
			// 		CUR_LEVEL = 1;
			// 	}

			// }
		}else if(gameState == "GAME_OVER"){
			this.framesGameOver++;
			if(this.framesGameOver == 30){
				this.framesGameOver = 0;
				if(this.showMessageGameOver){
					this.showMessageGameOver = false;
				}else{
					this.showMessageGameOver = true;
				}
				if(restartGame){
					this.restartGame = false;
					gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level"+CUR_LEVEL+".png";
					World.restartGame(newWorld);
				}
			}
		}else if(gameState =="MENU"){
			if(save.saveGame){
				save.saveGame = false;

				String[] opt1 = {
				"level",
				 "vida",
				 "ammo",
				 "playerX",
				 "playerY"};


				int[] opt2 = {
					 CUR_LEVEL,
					 (int)(player.life),
					 (int)player.getX(),
					 (int)player.getY()};

				save.saveGame(opt1, opt2, 0);
				System.out.println("Jogo Salvo");
			}
			player.updateCamera();
			menu.tick();
		}
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*Renderiza��o do Jogo*/
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);

		for(int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			e.render(g);
		}
		uiRender(g);

		g.dispose();	
		g = bs.getDrawGraphics();
		//
		g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		if(gameState== "GAME_OVER"){

		}else if(gameState == "MENU"){
			menu.render(g);
		}
		bs.show();
	}
	
	public void uiRender(Graphics g) {
		
		int i = 1, x = 0, y = 20;
		for(i = 1; i <= (int)Ui.quantidadeCoracao; i++) {
			
			
			ui[0].render(g, i*20, 4);
		}
	}

	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	
	public void keyTyped(KeyEvent e) {
		
	}


	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_SPACE){

		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||  e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
		
			player.up = true;
			if(gameState == "MENU"){
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {

			player.down = true;
			if(gameState == "MENU"){
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			
		
		}

		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			restartGame = true;
			if(gameState == "MENU"){
				menu.enter = true;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			gameState = "MENU";
			menu.pause = true;
		}

	}


	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||  e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = false;
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	

	}
}
