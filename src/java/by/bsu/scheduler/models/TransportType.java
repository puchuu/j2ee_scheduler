package by.bsu.scheduler.models;

public class TransportType{

	private int id;
	private String name;
	private String image;
	private int speed;

	//constructors

	public TransportType() {
	}

	public TransportType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public TransportType(int id, String name, String image, int speed) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.speed = speed;
	}

	//Setters

	public void setImage(String image) {
		this.image = image;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	//Getters

	public String getImage() {
		return image;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getSpeed() {
		return this.speed;
	}

	//Overrides

	@Override
	public boolean equals(Object obj) {
        if(obj instanceof TransportType && this.id != 0) {
			return this.id == ((TransportType) obj).id;
		} else {
			return obj == this;
		}
    }

	@Override
    public int hashCode() {
        if(this.id != 0) {
			return super.hashCode() + this.id;
		} else {
			return super.hashCode();
		}
    }

	@Override
    public String toString() {
        return String.format("TransportType: {id: %d, name: '%s', image: '%s'}",
            this.id, this.name, this.speed, this.image);
    }
}