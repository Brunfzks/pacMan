package com.abstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.abstudios.entities.EnemyBlue;
import com.abstudios.entities.EnemyOrange;
import com.abstudios.entities.EnemyPink;
import com.abstudios.entities.EnemyRed;
import com.abstudios.entities.Entity;
import com.abstudios.entities.Moeda;
import com.abstudios.entities.Player;
import com.abstudios.graficos.Spritesheet;
import com.abstudios.main.Game;

import com.abstudios.world.World;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;

	public World(String path) {

		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			System.out.println(path);
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];

			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();

			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {

					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_LAVA_FLOOR);
					switch (pixels[xx + (yy * map.getWidth())]) {

						case 0xff000203:
							// Grama Comum

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_COMUM);
							break;
						case 0xff042f02:
							// Grama Horizontal Sup

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_HORIZONTAL_SUPERIOR);
							break;
						case 0xff0e8208:
							// Grama horizontal Inf

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_HORIZONTAL_INFERIOR);
							break;
						case 0xff074b03:
							// Grama Vertical Esquerda

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_VERTICAL_ESQUERDA);
							break;
						case 0xff0b6806:
							// Grama Vertical Direita

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_VERTICAL_DIREITA);
							break;
						case 0xff725be5:
							// Grama Canto superior esquerdo

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_CANTO_SUPERIOR_ESQUERDO);
							break;
						case 0xff564b90:
							// Grama Canto superior Direito

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_CANTO_SUPERIOR_DIREITO);
							break;
						case 0xff887dbb:
							// Grama Canto inferior Direito

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_CANTO_INFERIOR_DIREITO);
							break;
						case 0xffa89de3:
							// Grama Canto inferior esquerdo

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_GRAMA_CANTO_INFERIOR_ESQUERDO);
							break;
						case 0xff9bd5f7:
							// Parede

							tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_PAREDE_HORIZONTAL);
							break;
						case 0xff052f47:
							// LavaFloor

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_LAVA_FLOOR);
							break;
						case 0xffa0c8df:
							// LavaTile

							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE,
									Tile.TILE_LAVA_HORIZONTAL);
							break;
						case 0xfffcf007:
							// Player

							Game.player.setX(xx * TILE_SIZE);
							Game.player.setY(yy * TILE_SIZE);
							break;
						case 0xfff9dede:
							// Moeda
							Moeda moeda = new Moeda(xx * TILE_SIZE, yy * TILE_SIZE, 12, 12, 0, Entity.MOEDA_SPRITE);
							Game.entities.add(moeda);
							Game.moedasContagem++;
							break;
						case 0xff0600ff:
							// EnemyBlue
							EnemyBlue enemyBlue = new EnemyBlue(xx * TILE_SIZE, yy * TILE_SIZE, 12, 12, 1,
									Entity.ENEMY_BLUE_SPRITE);
							Game.entities.add(enemyBlue);
							Game.enemis.add(enemyBlue);
							break;
						case 0xff2aff00:
							// EnemyPink
							EnemyPink enemyGreen = new EnemyPink(xx * TILE_SIZE, yy * TILE_SIZE, 12, 12, 1,
									Entity.ENEMY_PINK_SPRITE);
							Game.entities.add(enemyGreen);
							Game.enemis.add(enemyGreen);
							break;
						case 0xffe0ed17:
							// EnemyPink
							EnemyOrange enemyOrange = new EnemyOrange(xx * TILE_SIZE, yy * TILE_SIZE, 12, 12, 1,
									Entity.ENEMY_ORANGE_SPRITE);
							Game.entities.add(enemyOrange);
							Game.enemis.add(enemyOrange);
						break;
						case 0xfff00707:
							// EnemyPink
							EnemyRed enemyRed = new EnemyRed(xx * TILE_SIZE, yy * TILE_SIZE, 12, 12, 1,
									Entity.ENEMY_RED_SPRITE);
							Game.entities.add(enemyRed);
							Game.enemis.add(enemyRed);
						break;
						default:
							//floor
							if(Game.CUR_LEVEL == 1){
								System.out.println("teste");
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_LAVA_FLOOR);
							}else{
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_COMUM);
							}
							break;
					}
	
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2 = (xnext + width -2)/TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext/TILE_SIZE;
		int y3 = (ynext + height -2)/TILE_SIZE;
		
		int x4 = (xnext + width -2)/TILE_SIZE;
		int y4 = (ynext + height -2)/TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}
	
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext/TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}	

	public static void restartGame(String level) {

		double life = 50;

		Game.entities = new ArrayList<Entity>();
		Game.enemis = new ArrayList<Entity>();
		Game.spritesheet = new Spritesheet("/spritesheet32.png");
		Game.player = new Player(0, 0, 32, 32,2, Game.spritesheet.getSprite(192, 0, 32, 32), 1);
		Game.player.life = life;
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x/TILE_SIZE;
		int ystart = Camera.y/TILE_SIZE;
		
		int xfinal = xstart + (Game.WIDTH/TILE_SIZE) + 1;
		int yfinal = ystart + (Game.HEIGHT/TILE_SIZE ) + 1;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}

