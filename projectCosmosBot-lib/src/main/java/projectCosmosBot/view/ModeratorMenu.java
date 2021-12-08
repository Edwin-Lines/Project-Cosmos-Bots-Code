package projectCosmosBot.view;

import java.util.ArrayList;

public class ModeratorMenu implements Menu{
	private ArrayList<String> allCommands;

	public ModeratorMenu() {
		this.allCommands = new ArrayList<>();
		this.setCommands();

	}
	
	
	public void setCommands() {
		String [] commands = {"-addReview","-specificEntry"};
		for(String value:commands) {
			this.allCommands.add(value);
		}
	}
	
	public boolean isCommand(String text) {
		for(String value: this.allCommands) {
			if(text.equalsIgnoreCase(value)) {
				return true;
			}	
		}
		return false;
	}
	
	
	public String getCommand(String command) {
		if(command.equalsIgnoreCase("-addReview")) {
			StringBuilder menu = new StringBuilder();
			menu.append("To evaluate a specific post, insert the next syntax: username%password%postID/pointsAchieved");
			return menu.toString();
		}
		if(command.equalsIgnoreCase("-specificEntry")) {
			return "To see a specific entry, insert the next syntax: username%password%particular_id";
		}
		return "No ["+ command + "] exists in the commands instructions";
	}
}