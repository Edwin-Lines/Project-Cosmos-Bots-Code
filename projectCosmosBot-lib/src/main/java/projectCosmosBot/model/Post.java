package projectCosmosBot.model;

public class Post {
	private String name;
	private String content;
	private int acceptanceStatus;
	private int timesReviewed;
	private double score;
	
	public Post(String name, String content) {
		this.name = name;
		this.content = content;
		this.acceptanceStatus = 0;
		this.timesReviewed = 0;
		this.score = 0;
	}
	
	public Post() {
		
	}
	
	public void checkAuthor() {
		if(this.name.equals("")) {
			setName("Anonymous");
		}
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setTimesReviewed(int timesReviewed) {
		this.timesReviewed = timesReviewed;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setAcceptanceStatus(int acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getStatus() {
		//return this.accepted;
		return this.acceptanceStatus;
	}
	
	public int getTimesReviewed() {
		return this.timesReviewed;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public String reviewStatus() {
		if(score == 0) {
			return "To be review";
		}
		
		String s = "s";
		if(this.timesReviewed == 1) {
			s = "";
		}
		
		return "This post has been reviewed by " + this.timesReviewed + " moderator" + s;
	}
	
	public void updateScore(int newGrade) {
		this.score = (score * timesReviewed + newGrade)/ (timesReviewed+1);
		this.timesReviewed++;
		updateStatus();
	}
	
	public void updateStatus() {
		if(score>4 && timesReviewed>1) {
			acceptanceStatus=-1;
		}else if(score<4 && timesReviewed>1) {
			acceptanceStatus=1;
		}else {
			acceptanceStatus=0;
		}
	}
	
	
	public String postStatus() {
		if(this.timesReviewed <=1) {
			String s = "s";
			if(this.timesReviewed ==1) {
				s = "";
			}
			return "Not enought reviews to change status (This post has been reviewed only "+ this.timesReviewed + " time" + s + ")";
		}
		
		
		String acceptance = "";
		if(acceptanceStatus==1) {
			acceptance = "ACCEPTED";
		}else if(acceptanceStatus==-1) {
			acceptance = "REJECTED";
		}
		
		return "The status of this post is: [" + acceptance + "]";
	}
	
	public String toString() {
		if(this.name.equals("")) {
			this.name = "Anonymous";
		}
		return "Author: " + this.name + "\nContent: " + this.content + "\n";
	}
	
}
