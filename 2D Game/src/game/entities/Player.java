package game.entities;

import game.gfx.Animation;
import game.gfx.Assets;
import game.main.Game;
import game.states.GameState;
import game.states.State;
import game.utilities.Vector2D;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Creature {

	private final int jumpHeight;
	private double maxSpeedXDir = 200;
	private double maxSpeedYDir = 300;
	private Game game;

	private Animation animRight = new Animation(100, Assets.playerRight);
	private Animation animLeft = new Animation(100, Assets.playerLeft);
	private Animation animIdle = new Animation(100, Assets.playerStand);
	private Animation animJLeft = new Animation(100, Assets.playerLeft);
	private Animation animJRight = new Animation(100, Assets.playerRight);

	public Player(Game g, double x, double y, Vector2D vel, int jumpHeight) {
		super(x, y, vel, 64, 64, jumpHeight);
		this.jumpHeight = jumpHeight;
		isStatic = false;
		this.game = g;
	}

	public void jump() {
		if (!jumping && hasGround()) {
			vel = Vector2D.add(vel, new Vector2D(jumpHeight, 90, false));
			jumping = true;
		} else if (!scheduledJump && vel.getY() < 0) {
			// scheduledJump = true;
		}
	}
	
	public void forceJump(){
		vel.setY(0);
		vel = Vector2D.add(vel, new Vector2D(jumpHeight, 90, false));
		jumping = true;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x + 5, (int) y, width - 10, height);
	}

	public void moveLeft() {
		directionChange.setX(-2.5);
	}

	public void moveRight() {
		directionChange.setX(2.5);
	}

	public void stopX() {
		directionChange.setX(0);
	}

	@Override
	public void tick() {
		keyHandle();
		movement();
		animLeft.tick();
		animRight.tick();
		animIdle.tick();
	}

	private void movement() {
		// apply Direction change
		vel = Vector2D.add(vel, directionChange);
		if (Math.abs(vel.getX()) >= maxSpeedXDir) {
			if (vel.getX() > 0) {
				vel.setX(maxSpeedXDir);
			} else {
				vel.setX(-maxSpeedXDir);
			}
		}
		if (Math.abs(vel.getY()) >= maxSpeedYDir) {
			if (vel.getY() > 0) {
				vel.setY(maxSpeedYDir);
			} else {
				vel.setY(-maxSpeedYDir);
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
		}

		move(vel.getX() / 100, vel.getY() / 100);
	}

	public void keyHandle() {
		// handle keyPresses
		if (game.getKey().up) {
			jump();
		}
		if (game.getKey().left) {
			moveLeft();
		} else if (game.getKey().right) {
			moveRight();
		} else if (game.getKey().goToSpawn) {
			this.setX(GameState.getWorld().getSpawnX());
			this.setY(GameState.getWorld().getSpawnY());
			vel.setX(0);
			vel.setY(0);
			GameState.getWorld().setOffset(0);
		} else {
			stopX();
		}
	}

	@Override
	public void render(Graphics g, int gameHeight, double offset) {
		g.drawImage(getCurrentAnimationFrame(), (int) this.x - (int) offset, (int) (gameHeight - Game.topBarHeight - height - y), width, height, null);
		g.drawRect((int) this.x + 5 - (int) offset, (int) (gameHeight - Game.topBarHeight - height - y), width - 10, height);
	}

	private BufferedImage getCurrentAnimationFrame() {
		if (vel.getX() < 0) {
			if (!hasGround())
				return animJLeft.getCurrentFrame();
			return animLeft.getCurrentFrame();
		} else if (vel.getX() > 0) {
			if (!hasGround())
				return animJRight.getCurrentFrame();
			return animRight.getCurrentFrame();
		} else {
			return animIdle.getCurrentFrame();
		}

	}
	/*
	 * private boolean hasGround() { if (y <= 0 && vel.getY() <= 0) { jumping =
	 * false; if (scheduledJump) { scheduledJump = false; vel.setY(0); jump();
	 * return false; } return true; } else { return false; } }
	 */

	@Override
	public void collision(Entity e, boolean invokedByCreature, int direction) {
		// TODO Auto-generated method stub
		switch (e.getClass().getName()) {
		case "game.entities.Obstacle":
			switch (direction) {
			case -1:
				if (vel.getY() < 0) {
					vel.setY(0);
					setHasGround(true);
					setJumping(false);
				}
				moveTo(getX(), e.getBounds().getMaxY() - 0.1);
				break;
			case 1:
				moveTo(getX(), e.getBounds().getMinY() - getHeight());
				if (vel.getY() > 0)
					vel.setY(0);
				break;
			case -2:
				vel.setX(0);
				moveTo(e.getBounds().getMaxX() - 5, getY());
				break;
			case 2:
				vel.setX(0);
				moveTo(e.getBounds().getMinX() + 5 - getWidth(), getY());
				break;
			case 0:
				System.out.println("PANIC");
			default:
				System.out.println("Wie bist du hier hin gekommen?");
			}
			break;
		case "game.entities.Goomba":
			if (direction == -1) {
				forceJump();
			} else {
				moveTo(game.world.getSpawnX(), game.world.getSpawnY());
				System.out.println("I died");
			}
			break;
		case "game.entities.Player":
			System.out.println("How the f*** did you manage to put two players into one game??");
			break;
		default:
			System.out.println("class unknown");
			break;

		}
		if (!invokedByCreature) {
			e.collision(this, true, -direction);
		}
	}

}
