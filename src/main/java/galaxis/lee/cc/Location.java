package galaxis.lee.cc;

public class Location {
	// int id;
	private int level;
	private int location;
	private int pos;
	private int aisle;
	private int state;
	private String box_number;
	private int priority;
	private String area;
	private int weight;
	private int type;
	//////////////
	private String lift_area;
	private int Container_status;;


	public Location() {
	}

	@Override
	public String toString() {
		return "Location{" +
				"level=" + level +
				", location=" + location +
				", pos=" + pos +
				", aisle=" + aisle +
				", state=" + state +
				", box_number='" + box_number + '\'' +
				", priority=" + priority +
				", area='" + area + '\'' +
				", weight=" + weight +
				", type=" + type +
				", lift_area='" + lift_area + '\'' +
				", Container_status=" + Container_status +
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

	public int getLocation() {
		return location;
	}

	public int getPos() {
		return pos;
	}

	public int getAisle() {
		return aisle;
	}

	public int getState() {
		return state;
	}

	public String getBox_number() {
		return box_number;
	}

	public int getPriority() {
		return priority;
	}

	public String getArea() {
		return area;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void setAisle(int aisle) {
		this.aisle = aisle;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setBox_number(String box_number) {
		this.box_number = box_number;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getContainer_status() {
		return Container_status;
	}

	public void setContainer_status(int container_status) {
		Container_status = container_status;
	}
	
}
