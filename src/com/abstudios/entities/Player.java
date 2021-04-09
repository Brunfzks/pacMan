package com.abstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.abstudios.main.Game;
import com.abstudios.main.Sound;
import com.abstudios.world.Camera;
import com.abstudios.world.World;


public class Player extends Entity {

	public boolean right = false, left = false, up = false, down = false;
	public int rightDir = 0, leftDir = 1, upDir = 3, downDir =4;
	public double posHuntx, posHunty;
	public int dir = rightDir;

	public Random rand = new Random();
	
	public int frames = 0, maxframes = 4;
	public int damageFrame = 0;
	public int index = 0;
	public int maxIndex = 8;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	public double life = 30;
	public int maxLife = 30;
	public int mx, my;
	
	public boolean takeDamage = false;
	
	private boolean moved = false;

	public boolean getMoved(){
		return this.moved;
	}
	
	public Player(double x, double y, int width, int height, double speed, BufferedImage sprite, int depth) {
		super(x, y, width, height, speed, sprite);

		this.depth = depth;
		
		rightPlayer = new BufferedImage[9];
		leftPlayer = new BufferedImage[9];
		upPlayer = new BufferedImage[9];
		downPlayer = new BufferedImage[9];
		
		for(int i = 0; i < maxIndex; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + i * 32, 80, 32, 32);
			leftPlayer[i] = Game.spritesheet.getSprite(0 + i * 32, 120, 32, 32);
			upPlayer[i]  = Game.spritesheet.getSprite(0 + i * 32, 160, 32, 32);
			downPlayer[i]  = Game.spritesheet.getSprite(0 + i * 32, 200, 32, 32);
		}
	}

	public void tick() {

		moved = false;
		if(right && World.isFreeDynamic( (int)(x + speed),this.getY(), 31, 31)) {
			 
			moved = true;
			dir = rightDir;
			x += speed;
			posHuntx = x + 80;
			posHunty = y;
			System.out.println(x/32);
		}else if(left && World.isFreeDynamic((int)(x - speed),this.getY(), 31, 31)) {
			
			moved = true;
			dir = leftDir;
			x -= speed;
			posHuntx = x - 80;
			posHunty = y;
		}else if(up && World.isFreeDynamic(this.getX(),(int)(y - speed), 31, 31)) {
			
			moved = true;
			y-= speed;
			dir = upDir;
			posHunty = y - 80;
			posHuntx = x;
		}else if(down && World.isFreeDynamic(this.getX(),(int)(y + speed), 31, 31)) {
			
			moved = true;
			y+= speed;
			dir = downDir;
			posHunty = y + 80;
			posHuntx = x;
			System.out.println(y/32);
		}
		
		frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index >= maxIndex) {
				index = 0;
			}
		}
		moedaPega();	

		updateCamera();

		if(Game.moedasAtual == Game.moedasContagem){
			System.out.println("Game Over");
		}
	}

	public void moedaPega(){
		for(int i = 0; i < Game.entities.size(); i++){
			Entity current = Game.entities.get(i);
			if(current instanceof Moeda){
				if(isColliding(this, current)){
					Game.entities.remove(i);
					Game.score += 20;
					// Sound.coin.play();
					Game.moedasAtual++;
				}	
			}
		}
	}
	
	public void moved(Graphics g) {
		if(dir == rightDir){
			g.drawImage(rightPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y,null);
		}else if(dir == leftDir){
			g.drawImage(leftPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y,null);
		}else if(dir == upDir){
			g.drawImage(upPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y,null);
		}else if(dir == downDir){
			g.drawImage(downPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y,null);
		}
	}
	
	
	
	public void render(Graphics g) {
		moved(g);
	}
	
}
