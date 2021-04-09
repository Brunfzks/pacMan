package com.abstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;
import com.abstudios.world.World;


public class EnemyBlue extends Enemy{

	public static int frames = 0, maxframes = 10, index = 0, maxIndex = 6;
	
	public int rightDir = 0, leftDir = 1;
	public int dir = rightDir;
	public BufferedImage[] leftGhost;
	public BufferedImage[] rightGhost;
	public BufferedImage teste;
	public static int moveBlueTime = 0;


	public EnemyBlue(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		leftGhost = new BufferedImage[6];
		rightGhost = new BufferedImage[6];

		for(int i = 0; i < maxIndex; i++){
			rightGhost[i] = Game.spritesheet.getSprite(0 + i * 16, 240, 15, 20);
			leftGhost[i] = Game.spritesheet.getSprite(0 + i * 16, 260, 15, 20);
		}
	}
	
	
	public void tick() {
		
		movePinky();
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
            
            this.dir = this.rightDir;
        }else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
            
            this.dir = this.leftDir;
        }

		depth = 0;

		frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index >= maxIndex) {
				index = 0;
			}
		}	
	}
	
	public void destroySelf() {
		
	}
	
	public void render(Graphics g) {
		if(rightDir == dir){
			g.drawImage(rightGhost[index], this.getX() + 8, this.getY() + 8, null);
		}else if(leftDir == dir){
			g.drawImage(leftGhost[index], this.getX() + 8, this.getY() + 8, null);
		}
			
	}

}
