package com.resume.dto;

public class ResumeResponse {
	
	private int score;
	private int matchedSkills;
	private String missingSkills;
	private String suggestions;
	
	public ResumeResponse() {
		
	}
	
	public ResumeResponse(int score,
            int matchedSkills,
            String missingSkills,
            String suggestions) {
this.score = score;
this.matchedSkills = matchedSkills;
this.missingSkills = missingSkills;
this.suggestions = suggestions;
}
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score=score;
	}
	
	public int getMatchedSkills() {
		return matchedSkills;
	}
	
	public void setMatchedSkills(int matchedSkills) {
		this.matchedSkills=matchedSkills;
	}
	public String getMissingSkills() {
	    return missingSkills;
	}

	public void setMissingSkills(String missingSkills) {
	    this.missingSkills = missingSkills;
	}

	public String getSuggestions() {
	    return suggestions;
	}

	public void setSuggestions(String suggestions) {
	    this.suggestions = suggestions;
	}

}
