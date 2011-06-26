package by.bsu.scheduler.models;

import java.sql.Time;

public class Transport{

	private int id;
	private Place from_place;
	private Place to_place;
	private int number;
	private Time start;
	private Time end;
	private Time spend;
	private Time period;
	private String image;
	private TransportType transport_type;

	//constructor

	public Transport() {
	}

	public Transport(int id, int number, Place from_place, Place to_place, Time start, Time end, Time spend, Time period, TransportType transport_type) {
		this.id = id;
		this.number = number;
		this.from_place = from_place;
		this.to_place = to_place;
		this.start = start;
		this.end = end;
		this.spend = spend;
		this.period = period;
		this.transport_type = transport_type;
	}

	public Transport(int id, int number, Place from_place, Place to_place, Time start, Time end, Time spend, Time period, TransportType transport_type, String image) {
		this.id = id;
		this.number = number;
		this.from_place = from_place;
		this.to_place = to_place;
		this.start = start;
		this.end = end;
		this.spend = spend;
		this.period = period;
		this.transport_type = transport_type;
		this.image = image;
	}

	//Setters

	public void setId(int id) {
		this.id = id;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public void setSpend(Time spend) {
		this.spend = spend;
	}

	public void setPeriod(Time period) {
		this.period = period;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setFrom_place(Place from_place) {
		this.from_place = from_place;
	}

	public void setTo_place(Place to_place) {
		this.to_place = to_place;
	}

	public void setTransport_type(TransportType transport_type) {
		this.transport_type = transport_type;
	}

	//Getters

	public int getId() {
		return this.id;
	}

	public int getNumber() {
		return this.number;
	}

	public Time getEnd() {
		return this.end;
	}

	public Place getFrom_place() {
		return this.from_place;
	}

	public String getImage() {
		return this.image;
	}

	public Time getPeriod() {
		return this.period;
	}

	public Time getSpend() {
		return this.spend;
	}

	public Time getStart() {
		return this.start;
	}

	public Place getTo_place() {
		return this.to_place;
	}

	public TransportType getTransport_type() {
		return this.transport_type;
	}

	//Overrides

	@Override
	public boolean equals(Object obj) {
        if(obj instanceof Transport && this.id != 0) {
			return this.id == ((Transport) obj).id;
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
        return String.format("Transport: {id: %d, number: '%d', from_place: '%s', to_place: '%s', start: '%s', end: '%s', spend: '%s', period: '%s', transport_type: '%s', image: '%s'}",
            this.id, this.number, this.from_place.toString(), this.to_place.toString(), this.start.toString(), this.end.toString(), this.spend.toString(), this.period.toString(), this.transport_type.toString(), this.image);
    }
}