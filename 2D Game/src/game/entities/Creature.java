package game.entities;


public abstract class Creature extends Entity{

	private final int DEFAULT_HEALTH = 20;
	private final int DEFAULT_SPEED = 2;

	protected int health;
	protected double speed;
	protected double xMove, yMove;
	
	public Creature(double x, double y, int width, int heigth) {
		super(x, y, width, heigth);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}
	
	public void move(){
		x += xMove;
		y += yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getxMove() {
		return xMove;
	}

	public void setxMove(double xMove) {
		this.xMove = xMove;
	}

	public double getyMove() {
		return yMove;
	}

	public void setyMove(double yMove) {
		this.yMove = yMove;
	}
	 
}
