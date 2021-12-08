package projectCosmosBot.controller;

import projectCosmosBot.model.Account;

public class ContentManager extends Account{
	
	public ContentManager(String user, String password) {
		super(user, password, "Manager");
	}
}
