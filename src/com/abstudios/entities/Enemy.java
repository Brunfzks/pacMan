package com.abstudios.entities;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.abstudios.main.Game;
import com.abstudios.world.AStar;
import com.abstudios.world.Node;
import com.abstudios.world.Vector2i;
import com.abstudios.world.World;

public class Enemy extends Entity {

    public int rightDir = 0, leftDir = 1;
    public int dir = rightDir;
    public Random rand = new Random();
    
    public boolean scater;
    public int scaterTime = 0;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        // TODO Auto-generated constructor stub
    }

    public void moveBlink(){
        Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
		Vector2i end = new Vector2i((int)(Game.player.x/32), (int)(Game.player.y/32));

		patch = AStar.findPath(Game.world, start, end);

		followPatch(patch, this.speed);
    } 

    public void movePinky(){
        Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
        Vector2i end = new Vector2i((int)(Game.player.posHuntx/32), (int)(Game.player.posHunty/32));

        patch = AStar.findPath(Game.world, start, end);

        followPatch(patch, this.speed);
    }

    public void moveOrange(){
        if(scater == false){
            Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
            Vector2i end = new Vector2i((int)(Game.player.x/32), (int)(Game.player.y/32));
            if(AStar.getDistance(start, end) > 2){
                patch = AStar.findPath(Game.world, start, end);
            }
            else{
                System.out.println("testeScater");
                 scatter(448, 448);
            } 
        }
        if(scaterTime == 60*3){
            scater = false;
            scaterTime = 0;
        }   
        else
            scaterTime++;

        followPatch(patch, this.speed);
    }

    public void moveBlue(){
        if(patch == null || patch.size() ==0){
            Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
            Vector2i end = new Vector2i((int)(Game.player.x/32), (int)(Game.player.y/32));

            patch = AStar.findPath(Game.world, start, end);

            followPatch(patch, this.speed);
        }
    }

    public void scatter(int xScater, int yScater){
        System.out.println(x/32 + " " + y/32);
        Vector2i start = new Vector2i((int)(this.x/32), (int)(this.y/32));
		Vector2i end = new Vector2i(xScater/32, yScater/32);
        System.out.println(xScater/32 + " " + yScater/32);
		patch = AStar.findPath(Game.world, start, end);

        followPatch(patch, this.speed);
        scater = true;
    }
}
