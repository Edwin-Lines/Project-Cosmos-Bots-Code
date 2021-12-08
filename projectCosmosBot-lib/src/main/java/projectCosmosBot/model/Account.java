package projectCosmosBot.model;

public class Account {
	private String user;
	private String password;
	private String role;
	
	public Account(String user, String password, String role) {
		this.user = user;
		this.password = password;
		this.role = role;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setPassword(String newPassword) {
		this.password  = newPassword;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public String toString() {
		return this.user +" [" + this.role + "]";
	}
}
