package com.abstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;

public class Moeda extends Entity {

    public int frames = 0, maxframes = 12;
	public int damageFrame = 0;
	public int index = 0;
    public int maxIndex = 8;
    
    private BufferedImage[] anmFruta;

    public Moeda(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

        anmFruta = new BufferedImage[8];

        for(int i = 0; i < maxIndex; i++){
            anmFruta[i] = Game.spritesheet.getSprite(0 + (i * 16), 320, 10, 11);
        }
    }
    
    @Override
    public void tick() {
        frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index >= maxIndex) {
				index = 0;
			}
		}	
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(anmFruta[index], this.getX() + 15, this.getY() + 12, null);
    }
}
