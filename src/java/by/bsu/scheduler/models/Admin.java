package by.bsu.scheduler.models;

public class Admin{

	private int id;
	private String email;
	private String name;
	private String login;
	private String password;
	private String salt;

	//constructors

	public Admin() {
	}

	public Admin(int id, String email, String name, String login, String password) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.login = login;
		this.password = password;
	}

	public Admin(int id, String email, String name, String login, String password, String salt) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.login = login;
		this.password = password;
		this.salt = salt;
	}

	//setters

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	//getters

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	//overrides

	@Override
	public boolean equals(Object obj) {
        if(obj instanceof Admin && this.id != 0) {
			return this.id == ((Admin) obj).id;
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
        return String.format("Admin: {id: %d, email: '%s', name: '%s', login: '%s', password: '%s', salt: '%s'}",
            this.id, this.email, this.name, this.login, this.password, this.salt);
    }
}