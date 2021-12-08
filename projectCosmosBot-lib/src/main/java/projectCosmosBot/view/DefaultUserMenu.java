package projectCosmosBot.view;

import java.util.ArrayList;

public class DefaultUserMenu implements Menu{
	private ArrayList<String> allCommands;

	public DefaultUserMenu() {
		this.allCommands = new ArrayList<>();
		this.setCommands();

	}
	
	public void setCommands() {
		String [] commands = {"!commands", "!insert", "!show", "!manager", "!moderator"};
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
		if(command.equalsIgnoreCase("!commands")) {
			StringBuilder menu = new StringBuilder();
			menu.append("Displaying commands!\n");
			for(String value: this.allCommands) {
				value += "\n";
				menu.append(value);
			}
	        return menu.toString();
		}
		if(command.equalsIgnoreCase("!insert")) {
			StringBuilder instructions = new StringBuilder();
			instructions.append("To upload an entry, make sure it follows the next points:\n");
			instructions.append("1.The content doesn't contain or makes use of topics like crime, any type of discrimination or mental ilnesses [Please be respectful!]\n");
			instructions.append("2.The content in the post doesn't include any type of attack or is not used to make fun of other people (directly or indirectly)\n");
			instructions.append("If it contain one of the previous points, the post is REJECTED and will not be published\n\n");
			instructions.append("In case it contains any of the following points, the post will be review and then will get the status of ACCEPTED/REJECTED according to the content of the post itself\n");
			instructions.append("Contains any kind of personal information apart from a name to be used as the post author's name [Be aware of your online presence]\n");
			instructions.append("The story is told from the point of view of other person and not the writer [We want to hear your story not someone's else tale!]\n");
			instructions.append("Contains coursing of any kind\n\n");
			instructions.append("To uploud the entry, use the next syntax: name(or leave it blank if you don't want to give a name)<content of the post");
			
			return instructions.toString();
		}
		if(command.equalsIgnoreCase("!manager")) {
			StringBuilder menu = new StringBuilder();
			menu.append("Manager Menu\n\n");
			menu.append("+contentTeam: Edit the database of the content team\n");
			menu.append("+allEntries: Displays all entries in the database and its status\n");
			menu.append("+delete: Deletes all entries with the status REJECTED\n");
			menu.append("+specificEntry: Display a specific entry and its status\n");
			return menu.toString();
		}
		if(command.equalsIgnoreCase("!moderator")) {
			StringBuilder menu = new StringBuilder();
			menu.append("Moderator Menu\n\n");
			menu.append("-addReview: Add points to one of the posts\n");
			menu.append("-specificEntry: Display a specific entry and its status\n");
			return menu.toString();
		}
		return "No ["+ command + "] exists in the commands instructions";
	}
}
