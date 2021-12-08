package projectCosmosBot.controller;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import projectCosmosBot.DataBase.*;
import projectCosmosBot.model.Account;
import projectCosmosBot.model.Post;

public class Commands {
	
	private botCRUD postDB;
	private teamCRUD teamDB;
	
	public Commands() {
		
		//this.postDB = new botCRUD();
		//this.teamDB = new teamCRUD();
		}
	
	 //------------------POST METHODS-------------------------
	 public boolean isPost(String text){
			if(text.contains("<")) {
				return true;
			}
			return false;
		}
		
		public String addPost(String text) {
			if(text.contains("<") && !(text.contains("name(or leave it blank if you don't want to give a name)")) ) {
				String[] parts = text.split("<");
				String name = parts[0];
				String content = parts[1];
				
				Post post = new Post(name, content);
				post.checkAuthor();
				
				postDB = new botCRUD();
				postDB.addNewPost(post,"posts");
	            postDB.closebotCRUD();
	            return "Thanks for your tale!";
			}
			return "Have a good day!";
		}
		
		public String showPublishedEntries() {
	        ResultSet table;
	        postDB = new botCRUD();
	        postDB.getPostByCriteria("posts",1);
	        table = postDB.getresultsetQuery();
	        
	        try {
	        	StringBuilder entries = new StringBuilder();
	            while(table.next()){
	            String word = "Author: " +    
	            table.getString("name") + "\n"+
	            "Content: "+
	            table.getString("content")+"\n\n";
	            
	            entries.append(word);
	            }
	            return entries.toString();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error retrieving data";
	        } finally {
	        	postDB.closebotCRUD();
	        }
		}
		
		public String showSubmitedEntries() {
			ResultSet table;
	        postDB = new botCRUD();
	        postDB.retrievePostTable("posts");
	        table = postDB.getresultsetQuery();
	        try {
	        	StringBuilder entries = new StringBuilder();
	            while(table.next()){
	            String word = 
	            "Post Id: " + table.getString("id")+"\n"+
	            "Author: " + table.getString("name") + "\n"+
	            "Content: "+ table.getString("content") + "\n"+
	            "Times reviewed: " + table.getString("timesReviewed") + "\n" +
	            "Score: " + table.getString("score") + "\n" +
	            "Status: ";
	            
	            if(table.getString("status").equals("1")) {
	            	word = word + "Accepted \n\n";
	            } else if(table.getString("status").equals("0")) {
	            	word = word + "Pending review \n\n";
	            } else {
	            	word = word + "Rejected \n\n";
	            }
	            
	            entries.append(word);
	            }
	            return entries.toString();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error retrieving data";
	        } finally {
	        	postDB.closebotCRUD();
	        }
		}
		
		public String printSpecificEntry(int id) {
			ResultSet table;
	        postDB = new botCRUD();
	        postDB.getPostById("posts",id);
	        table = postDB.getresultsetQuery();
	        try {
	        	StringBuilder entries = new StringBuilder();
	            while(table.next()){
	            	if(table.getInt("id")==id) {
	            		String word = 
	            	            "Post Id: " + table.getString("id")+"\n"+
	            	            "Author: " + table.getString("name") + "\n"+
	            	            "Content: "+ table.getString("content") + "\n"+
	            	            "Times reviewed: " + table.getString("timesReviewed") + "\n" +
	            	            "Score: " + table.getString("score") + "\n" +
	            	            "Status: " + table.getString("status") + "\n\n";
	            	            entries.append(word);
	            	            
	            	}
	            }
	            
	            return entries.toString();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error retrieving data";
	        } finally {
	        	postDB.closebotCRUD();
	        }
		}
		
	//--------------POINTS METHODS---------------
		public boolean isPoints(String text) {
			if(text.contains("/") && !(text.contains("postID")) ) {
				
				return true;
			}
			return false;
		}
		
		public String givePostPoints(String text) {
			if(text.contains("/") && !(text.contains("postID")) ) {
				String[] parts = text.split("/");
				
				//Getting id
				int postID = Integer.valueOf(parts[0]);
				
				//Getting value
				int postValue = Integer.valueOf(parts[1]);
				
				Post post = new Post();
				
		        postDB = new botCRUD();
		        postDB.getPostById("posts",postID);
		        ResultSet table = postDB.getresultsetQuery();
		        
		        try {
			        while(table.next()) {
			        	post.setTimesReviewed(table.getInt("timesReviewed"));
			        	post.setAcceptanceStatus(table.getInt("status"));
			        	post.setScore(table.getDouble("score"));
			        }
		        	     	
		        	post.updateScore(postValue);
		        	postDB.closebotCRUD();
		        	postDB.gradePostUpdate(post,"posts",postID);
		        	return "The points were updated succesfully";
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return "Error retrieving data";
		        } finally {
		        	postDB.closebotCRUD();
		        }
		       
			}
			return "The syntax is incorrect";
		}
		
		
		 //------------------ACCOUNT METHODS-------------------------
		public boolean isAccount(String text) {
			if(text.contains("#") || text.contains("=")) {
				return true;
			}
			return false;
		}
	 
		public String createAccount(String profile) {//Analize why it doesn't return the string message when an account is delete (moderator) 
			Account person = null;
			if(profile.contains("#") && !(profile.contains("name") )){ //add a new ContentModerator
				//add a new ContentModerator
				String[] parts = profile.split("#");
				String name = parts[0];
				String password = parts[1];
				person = new ContentModerator(name,password);
			}
			if(profile.contains("=") && !(profile.contains("name"))){
				//add a new ContentManager
				String[] parts = profile.split("=");
				String name = parts[0];
				String password = parts[1];
				person = new ContentManager(name,password);
			}
			
			if(person!=null) {
				teamDB = new teamCRUD();
				teamDB.addTeamMemeber(person,"accounts");
				teamDB.closeTeamCRUD();
				return "The account was created succesfully";
			}
			
			return "Insert profile according to the previous syntax";
		}
		
		public String deleteAccount(int iD){
			teamDB = new teamCRUD();
			teamDB.deleteByIdentifier(iD,"accounts");
			teamDB.closeTeamCRUD();
			return "The account was deleted succesfully";
		}
		
		public String getExistingAccounts() {
			ResultSet table;
			teamDB = new teamCRUD();
			teamDB.retrieveAccountsTable("accounts");
	        table = teamDB.getresultsetQuery();
	        try {
	        	StringBuilder entries = new StringBuilder();
	            while(table.next()){
	            String word = 
	            "Staff Id: " + table.getString("id")+"\n"+
	            "Role: " + table.getString("rol") + "\n"+
	            "Username: "+ table.getString("username") + "\n"+
	            "Password: " + table.getString("password") + "\n\n";
	            entries.append(word);
	            }
	            return entries.toString();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error retrieving data";
	        } finally {
	        	teamDB.closeTeamCRUD();
	        }
		}
		
		
		 //------------------INSTRUCTION METHODS-------------------------
		
		public boolean isAccountInstruction(String text) {
			if(text.contains("%") && !(text.contains("password")) 
					  && !(text.contains("username")) )  {			
				return true;
			}
			return false;
		}
		
		public String isInstruction(String text) {
			if(text.contains("%") && !(text.contains("password")) 
								  && !(text.contains("username")))  {
			
				String[] parts = text.split("%");
				
				//Getting credentials
				String username = parts[0];
				String password= parts[1];
				
				//getting instruction
				String theInstruction = parts[2];
				
				ResultSet table;
		        postDB = new botCRUD();
		        postDB.retrievePostTable("accounts");
		        table = postDB.getresultsetQuery();
		        Account account=null;
		        try {
		            while(table.next()){
		            String getUsernameInDataBase = table.getString("username");
		            String getPasswordInDataBase = table.getString("password");
		            String getRolInDataBase = table.getString("rol");
		            
		            
			            if(username.equals(getUsernameInDataBase) && password.equals(getPasswordInDataBase)) {
			            	account = new Account(getUsernameInDataBase,getPasswordInDataBase,getRolInDataBase);
			            }
			            
		            }
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return "Error retrieving data";
		        } finally {
		        	postDB.closebotCRUD();
		        }
				
				if(account != null){
					if(account.getRole().equals("Manager")) {
						//ContentManager manager = (ContentManager) account;
						
						//+ContentTeam: edit database of staff memebers
						
						if(theInstruction.equalsIgnoreCase("delete")) {
							postDB.deleteRejectedPosts("posts");
							
							return "The submitions with status REJECTED were succesfully deleted";
						}
						
						if(theInstruction.equalsIgnoreCase("display")) {
							return showSubmitedEntries();
						}
						
						if(theInstruction.equalsIgnoreCase("displayAccounts")) {
							return getExistingAccounts();
						}
						
						if(theInstruction.contains(":")) {
							String [] part = theInstruction.split(":") ;
							
							if(part[0].equalsIgnoreCase("createAccount")) {
								return createAccount(part[1]);
							}
							
							if(part[0].equalsIgnoreCase("deleteAccount")) {
								return deleteAccount(Integer.valueOf(part[1]));
							}
							
						}
						
						if(theInstruction.contains("_")) {
							String [] part = theInstruction.split("_") ;
							
							if(part[0].equalsIgnoreCase("particular")) {
								if(entryExists(Integer.valueOf(part[1]))) {
									return printSpecificEntry(Integer.valueOf(part[1])); 
								}
								
								return "The post with id: "+part[1]+" doesn't exists";
							}
						}
						
						return "The Manager account: " + account + " has no access to this funtion";
					}else
					if(account.getRole().equals("Moderator")) {
						//ContentModerator moderator = (ContentModerator) account;
						
						if(isPoints(theInstruction)) {
							givePostPoints(theInstruction);
							return "The points have been granted";
						}
						
						if(theInstruction.contains("_")) {
							String [] part = theInstruction.split("_") ;
							
							if(part[0].equalsIgnoreCase("particular")) {
								if(entryExists(Integer.valueOf(part[1]))) {
									return printSpecificEntry(Integer.valueOf(part[1])); 
								}
								
								return "The post with id: "+part[1]+" doesn't exists";
							}
						}
						return "The Moderator account: " + account + " has no access to this funtion";
					}
					return "Invalid account";
				}
			}
			return "Insert a valid account according to the previous syntax";
		}
		
		//------------------CreateNewList METHOD-------------------------
	
		public boolean entryExists(int id) {
			boolean existance=false;
			ResultSet table;
	        postDB = new botCRUD();
	        postDB.retrievePostTable("posts");
	        table = postDB.getresultsetQuery();
	        
	        try {
	            while(table.next()){
	            	if(id ==table.getInt("id")){
	            		existance=true;
	            	}
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	postDB.closebotCRUD();
	        }
	        
	        return existance;
		}
	
}
