package projectCosmosBot.view;

import com.google.common.base.FinalizablePhantomReference;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import projectCosmosBot.controller.Commands;
import reactor.core.publisher.Mono;

public class CosmosBot {

	public static void main(String[] args) {
		Commands unifiedDatabase = new Commands();
		DefaultUserMenu menu = new DefaultUserMenu();
		ManagerMenu managerMenu = new ManagerMenu();
		ModeratorMenu moderatorMenu = new ModeratorMenu();
		final String tokenString = ""; // Bot Token ID

		// Connects discord server with the bot
		DiscordClient.create(tokenString) // Bot Token ID
				.withGateway(client -> client.on(MessageCreateEvent.class, event -> {

					// A message is created
					Message message = event.getMessage();
					String textInput = message.getContent();

					//If is a menu command
					if (menu.isCommand(textInput)) {
						if(textInput.equals("!show")) {
							String textOutput = unifiedDatabase.showPublishedEntries();
							return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));
						} 			
						String textOutput = menu.getCommand(textInput);
						return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));

						//If is a manager menu command
					} else if (managerMenu.isCommand(textInput)) {
						 
						String textOutput = managerMenu.getCommand(textInput);
						return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));

						//if is an moderator menu command
					} else if (moderatorMenu.isCommand(textInput)) {
						String textOutput = moderatorMenu.getCommand(textInput);
						return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));
						
						//command to see if its a post
					}  else if(unifiedDatabase.isPost(textInput)) {
						String textOutput = unifiedDatabase.addPost(textInput);
						return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));
					}
						//if are account instructions
					else if(unifiedDatabase.isAccountInstruction(textInput)){
						String textOutput = unifiedDatabase.isInstruction(textInput);
						return message.getChannel().flatMap(channel -> channel.createMessage(textOutput));
					}
					

					return Mono.empty();
				}))
				
				.block();
	}
}
