package by.bsu.scheduler.models;

public class Place{

	private int id;
	private String name;
	private String image;
	private Double latitude;
	private Double longitude;

	//constructors

	public Place() {
	}

	public Place(int id, String name, Double latitude, Double longitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Place(int id, String name, Double latitude, Double longitude, String image) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
	}

	//setters

	public void setId(int id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	//getters

	public int getId() {
		return this.id;
	}

	public String getImage() {
		return this.image;
	}

	public String getName() {
		return this.name;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	//overrides

	@Override
	public boolean equals(Object obj) {
        if(obj instanceof Place && this.id != 0) {
			return this.id == ((Place) obj).id;
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
        return String.format("Place: {id: %d, name: '%s', latitude: '%s', longitude: '%s', image: '%s'}",
            this.id, this.name, this.latitude, this.longitude, this.image);
    }
}