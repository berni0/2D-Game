package game.entities;

import game.gfx.Animation;
import game.gfx.Assets;
import game.main.Game;
import game.utilities.Vector2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	private Game game;
	private static int width = 64;
	private static int height = 64;
	private final int jumpHeight;
	private Vector2D vel;
	private Vector2D directionChange = new Vector2D(0, 0, true);
	private boolean jumping = false, scheduledJump = false;
	private double maxSpeedXDir = 110;
	
	
	private Animation animRight = new Animation(100, Assets.playerRight);
	private Animation animLeft = new Animation(100, Assets.playerLeft);
	private Animation animIdle = new Animation(100, Assets.playerStand);
	private Animation animJLeft = new Animation(100, Assets.playerLeft);
	private Animation animJRight = new Animation(100, Assets.playerRight);


	public Player(Game g, double x, double y, Vector2D vel, int jumpHeight) {
		super(x, y, width, height);
		this.vel = vel;
		this.jumpHeight = jumpHeight;
		game = g;
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(double dX, double dY) {	
		x += dX;
		y += dY;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector2D getVelocity() {
		return vel;
	}

	public void setVelocity(Vector2D vel) {
		this.vel = vel;
	}

	public void jump() {
		if (!jumping) {
			vel = Vector2D.add(vel, new Vector2D(jumpHeight, 90, false));
			jumping = true;
		} else if (!scheduledJump && vel.getY() < 0) {
			scheduledJump = true;
		}
	}

	public void moveLeft() {
		directionChange.setX(-1.8);
	}

	public void moveRight() {
		directionChange.setX(1.8);
	}

	public void stopX() {
		directionChange.setX(0);
	}

	@Override
	public void tick() {
		animLeft.tick();
		animRight.tick();
		animIdle.tick();
		keyHandle();
		movement();
		}
	
	private void movement(){
		// apply Direction change
				vel = Vector2D.add(vel, directionChange);
				if (Math.abs(vel.getX()) >= maxSpeedXDir) {
					if (vel.getX() > 0) {
						vel.setX(maxSpeedXDir);
					} else {
						vel.setX(-maxSpeedXDir);
					}
				}
				// apply Gravity and Friction
				if (!hasGround()) {
					vel = Vector2D.add(vel, Game.G);
				} else {
					if (vel.getX() > 0) {
						vel = Vector2D.add(vel, Game.F);
						if (vel.getX() < 0) {
							vel.setX(0);
						}
					}

					if (vel.getX() < 0) {
						vel = Vector2D.add(vel, new Vector2D(-Game.F.getX(), 0, true));
						if (vel.getX() > 0) {
							vel.setX(0);
						}
					}
					vel.setY(0);
				}
				
				move(vel.getX() / 100, vel.getY() / 100);
	}
	
	public void keyHandle(){
		// handle keyPresses
		if (game.getKey().up) {
			jump();
		}
		if (game.getKey().left) {
			moveLeft();
		} else if (game.getKey().right) {
			moveRight();
		} else {
			stopX();
		}
	}

	private boolean collision(Vector2D vel) {
		if(x+vel.getX() >= game.getWidth()|| x+vel.getX() < 0)
			return true;
		return false;
	}

	
	private boolean hasGround() {
		if (y <= 0 && vel.getY() <= 0) {
			jumping = false;
			if (scheduledJump) {
				scheduledJump = false;
				vel.setY(0);
				jump();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void render(Graphics g) {
			g.drawImage(getCurrentAnimationFrame(), (int) this.x,
					(int) (this.game.getHeight() - this.y - this.height - Game.topBarHeight), width, height, null);

	}

	private BufferedImage getCurrentAnimationFrame() {
			if(vel.getX()<0){
				if(!hasGround()) return animJLeft.getCurrentFrame();
				return animLeft.getCurrentFrame();
			}else if(vel.getX()>0){
				if(!hasGround()) return animJRight.getCurrentFrame();
				return animRight.getCurrentFrame();
			}else{
				return animIdle.getCurrentFrame();
			}
		
	}
}
