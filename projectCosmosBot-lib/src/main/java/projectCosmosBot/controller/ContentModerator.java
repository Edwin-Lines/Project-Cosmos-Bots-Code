package projectCosmosBot.controller;
import projectCosmosBot.model.Account;

public class ContentModerator extends Account {

	public ContentModerator(String user, String password) {
		super(user, password, "Moderator");
		
	}
	
}