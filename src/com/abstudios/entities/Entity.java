package com.abstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.abstudios.world.Camera;
import com.abstudios.world.Node;
import com.abstudios.world.Vector2i;
import com.abstudios.world.World;
import com.abstudios.entities.Entity;
import com.abstudios.main.Game;


public class Entity {

	public static BufferedImage MOEDA_SPRITE = Game.spritesheet.getSprite(0, 320, 10, 11);
	public static BufferedImage ENEMY_BLUE_SPRITE = Game.spritesheet.getSprite(0, 240, 16, 20);
	public static BufferedImage ENEMY_PINK_SPRITE = Game.spritesheet.getSprite(112, 240, 16, 20);
	public static BufferedImage ENEMY_ORANGE_SPRITE = Game.spritesheet.getSprite(224, 240, 15, 20);
	public static BufferedImage ENEMY_RED_SPRITE = Game.spritesheet.getSprite(336, 240, 15, 20);
	
	protected double speed;

	protected List<Node> patch;
	
	protected double x;
	protected double y;
	protected int z = 0;;
	protected int width;
	protected int height;

	public int frames = 0, maxframes = 0;
	public int damageFrame = 0;
	public int index = 0;
	public int maxIndex = 0;
	
	public int depth;
	
	private BufferedImage sprite;
	
	public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
	
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.speed = speed;

	}
	
	public void tick() {
		
	}

	public static Comparator<Entity> nodeSorter = new Comparator<Entity>(){
        @Override
        public int compare(Entity n0, Entity n1){
            if(n1.depth < n0.depth) 
                return + 1;
            if(n1.depth > n0.depth)
                return  - 1;
            return 0;
        }
    };

	public void followPatch(List<Node> patch, double speed){
		if(patch != null){
			if(patch.size() > 0){
				Vector2i target = patch.get(patch.size() - 1).tile;

				if(x < target.x * 32){
					x+= speed;
				}else if(x > target.x * 32){
					x-= speed;
				} 
				if(y < target.y * 32){
					y+= speed;
				}else if(y > target.y * 32){
					y-= speed;
				}

				if(x == target.x * 32 && y == target.y * 32){
					patch.remove(patch.size() - 1);
				}
			}
		}
	}

	public double calculateDistance(int x1, int y1, int x2, int y2 ){

		return Math.sqrt((x1 -x2) * (x1 - x2) + (y1 - y2) * (y1- y2));
	}

	public void updateCamera(){
		Camera.x =   Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y =   Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z){
			return true;
		}
		return false;
	}

	public static boolean isCollidingPerfect(int x1, int y1, int x2, int y2, int[] pixels1, int[] pixels2, BufferedImage sprite1, BufferedImage sprite2){

		for(int xx1 = 0; xx1 < sprite1.getWidth(); xx1++){
			for(int yy1 = 0; yy1 < sprite1.getHeight(); yy1++){
				for(int xx2 = 0; xx2 < sprite2.getWidth(); xx2++){
					for(int yy2 = 0; yy2 < sprite2.getHeight(); yy2++){
						int pixelAtual1 = pixels1[xx1 + yy1 *sprite1.getWidth()];
						int pixelAtual2 = pixels2[xx2 + yy2 *sprite2.getWidth()];

						if(pixelAtual1 == 0x00ffffff || pixelAtual2 == 0x00ffffff){
							continue;
						}

						if(xx1 + x1 == xx2 + x2 && yy1 + y1 == yy2 + y2){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void setX(int x) {
		
		this.x = x;
	}
	
	public void setY(int y) {
		
		this.y = y;
	}
	
	
	public int getX() {
		
		return (int) this.x;
	}
	
	public int getY() {
		
		return (int) this.y;
	}
	public int getZ() {
		
		return (int) this.z;
	}
	
	public int setZ(int z) {
		
		return this.z = z;
	}
	
	public int getWidth() {
		
		return this.width;
	}
	
	public int getHeight() {
		
		return this.height;
	}
	
	public void destroySelf() {
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y, null);
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y,maskw, maskh);
	}
}
