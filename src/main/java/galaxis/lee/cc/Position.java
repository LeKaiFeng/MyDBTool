package galaxis.lee.cc;


public class Position {
	private int level;
	private int pos;
	private int junction;
	private int direction;
	private int distance;
	private int type;//0,pos;1,junction;2,charging;3,lift
	private int junction2;
	private int direction2;
	private int distance2;
	private int type2;

	private String lift_area;

	@Override
	public String toString() {
		return "Position{" +
				"level=" + level +
				", pos=" + pos +
				", junction=" + junction +
				", direction=" + direction +
				", distance=" + distance +
				", type=" + type +
				", junction2=" + junction2 +
				", direction2=" + direction2 +
				", distance2=" + distance2 +
				", type2=" + type2 +
				", lift_area='" + lift_area + '\'' +
				'}';
	}

	public String getLift_area() {
		return lift_area;
	}

	public void setLift_area(String lift_area) {
		this.lift_area = lift_area;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getJunction() {
		return junction;
	}
	public void setJunction(int junction) {
		this.junction = junction;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getJunction2() {
		return junction2;
	}
	public int getDirection2() {
		return direction2;
	}
	public int getDistance2() {
		return distance2;
	}
	public int getType2() {
		return type2;
	}
	public void setJunction2(int junction2) {
		this.junction2 = junction2;
	}
	public void setDirection2(int direction2) {
		this.direction2 = direction2;
	}
	public void setDistance2(int distance2) {
		this.distance2 = distance2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	
}
