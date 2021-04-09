package com.abstudios.graficos;

 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import com.abstudios.main.Game;

	
public class Ui {
	
	public  int x, y;
	public static double quantidadeCoracao;
	public  BufferedImage sprite ;
	
	public static int frames = 0, maxframes = 8, index = 0, maxIndex = 3;
	
	public Ui(BufferedImage sprite) {
		
		this.sprite = sprite;
	}
	
	public void tick() {
		
		//cora��o
		quantidadeCoracao = Game.player.life/10;
		
	}
	
	public int getX() {
		
		return this.x;
	}
	
	public int getY() {
		
		return this.y;
	}
	
	public static int primeiraDecimal(double valor) {
	    return ((int)(valor * 10)) % 10;
	}
	
	public void render(Graphics g, double x, int y) {
		
		g.drawImage(sprite, (int)x, y, null);

		g.setFont(new Font("arial", Font.BOLD, 18));
		g.setColor(Color.WHITE);
		g.drawString("Score: " + Game.score, 400, 20);
	}
	
	
}

