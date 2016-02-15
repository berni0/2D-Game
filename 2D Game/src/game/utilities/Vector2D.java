package game.utilities;

public class Vector2D {

	private double x, y;
	private double magnitude;
	private double angle;
	
	public Vector2D(double XorMag, double YorAng, boolean isXY) {
		// TODO Auto-generated constructor stub
		if(isXY){
			x = XorMag;
			y = YorAng;
			updateMagAng();
		}
		else{
			magnitude = XorMag;
			angle = Math.toRadians(YorAng);
			updateXY();
		}
	}

	private void updateMagAng(){
		magnitude = Math.sqrt(x*x+y*y);
		angle = Math.atan2(y, x);
	}
	
	private void updateXY(){
		x = Math.cos(angle)*magnitude;
		y = Math.sin(angle)*magnitude;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		updateMagAng();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		updateMagAng();
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
		updateXY();
	}

	public double getAngleRadians(){
		return angle;
	}
	
	public double getAngleDegrees() {
		return Math.toDegrees(angle);
	}

	public void setAngleDegrees(double angle) {
		this.angle = Math.toRadians(angle);
	}
	
	public void setAngleRadians(double angle){
		this.angle = angle;
	}
	
	public void negate(){
		x = -x;
		y = -y;
		updateMagAng();
	}
	
	public Vector2D negated(){
		x = -x;
		y = -y;
		updateMagAng();
		return new Vector2D(x, y, true);
	}
	
	public static Vector2D add(Vector2D a, Vector2D b){
		double x = a.getX() + b.getX();
		double y = a.getY() + b.getY();
		return new Vector2D(x, y, true);
	}

	public static double dotProduct(Vector2D a, Vector2D b){
		return a.getX()*b.getX() + a.getY()*b.getY();
	}
}
