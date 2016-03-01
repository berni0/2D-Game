package game.entities;

import game.utilities.Vector2D;

public abstract class Creature extends Entity{

	private final int jumpHeight;
	private boolean hasGround = false;
	
	public boolean hasGround() {
		return hasGround;
	}

	public void setHasGround(boolean hasGround) {
		this.hasGround = hasGround;
	}


	protected Vector2D vel;
	protected Vector2D directionChange = new Vector2D(0, 0, true);
	protected boolean jumping = false, scheduledJump = false;

	
	public boolean isScheduledJump() {
		return scheduledJump;
	}

	public void setScheduledJump(boolean scheduledJump) {
		this.scheduledJump = scheduledJump;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public Creature(double x, double y, Vector2D vel, int width, int height, int jumpHeight) {
		super(x, y, width, height);
		this.vel = vel;
		this.jumpHeight = jumpHeight;
	}
	
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(double dX, double dY) {	
		x += dX;
		y += dY;
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
	
	public abstract void collision(Creature c, boolean invokedByCreature);
	
}
