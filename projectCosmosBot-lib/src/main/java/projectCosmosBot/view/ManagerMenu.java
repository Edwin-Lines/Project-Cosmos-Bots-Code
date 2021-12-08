package projectCosmosBot.view;

import java.util.ArrayList;

public class ManagerMenu implements Menu{
	private ArrayList<String> allCommands;

	public ManagerMenu() {
		this.allCommands = new ArrayList<>();
		this.setCommands();

	}
	
	public void setCommands() {
		String [] commands = {"+contentTeam", "+allEntries", "+delete","+specificEntry",">displayAccounts", ">addModer", ">addAdmin", ">deleteAccount"};
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
		 if (command.startsWith("+")) {
			//Is a !admin submenu command
			if(command.equalsIgnoreCase("+contentTeam")) {
				StringBuilder menu = new StringBuilder();
				menu.append("Content Team Database Options\n\n");
				menu.append(">displayAccounts: Displays all accounts in the database\n");
				menu.append(">addModer: Add a new Moderator\n");
				menu.append(">addAdmin: Add a new Manager\n");
				menu.append(">deleteAccount: Deletes an existing Team Member\n");
				return menu.toString();
			}
			if(command.equalsIgnoreCase("+delete")) {
				return "To delete all entries with the status REJECTED, insert the next syntax: username%password%delete";
			}
			if(command.equalsIgnoreCase("+allEntries")) {
				return "To see all submitted entries, insert the next syntax: username%password%display";
			}
			if(command.equalsIgnoreCase("+specificEntry")) {
				return "To see a specific entry, insert the next syntax: username%password%particular_id";
			}
			
			return "It doesn't start with + at all!";
			
		} else if (command.startsWith(">")) {
			//Is a +contenTeam submenu command
			if(command.equalsIgnoreCase(">displayAccounts")) {
				return "Insert Manager account with the next syntax:\nusername%password%displayAccounts";
			}
			
			if(command.equalsIgnoreCase(">addModer")) {
				return "Insert Moderator account with the next syntax:\nusername%password%createAccount:name#password";
			}
			if(command.equalsIgnoreCase(">addAdmin")) {
				return "Insert Manager account with the next syntax:\nusername%password%createAccount:name=password";
			}
			
			if(command.equalsIgnoreCase(">deleteAccount")) {
				return "Delete Manager account with next Syntax:\nusername%password%deleteAccount:Id";
			}
			return "Please use the exact command code";
		}
		
		return "It actually isn't a Subcommand or it isn't recognized";
	}
}
