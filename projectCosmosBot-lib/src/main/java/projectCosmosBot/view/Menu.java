package projectCosmosBot.view;

public interface Menu {
	void setCommands();
	boolean isCommand(String text);
	String getCommand(String command);
}
