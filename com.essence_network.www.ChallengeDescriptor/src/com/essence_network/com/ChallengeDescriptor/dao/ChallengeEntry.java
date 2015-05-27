package com.essence_network.com.ChallengeDescriptor.dao;

import java.util.List;

public class ChallengeEntry {
	
	private String freebase_id;
	private String answer;
	private String type;
	private List<String> hints;
	private List<Double> location;
	private Integer ID;
	
	public ChallengeEntry(){
		
	}
	
	public ChallengeEntry(String fr_id, String an, String t, List<String> h, List<Double> lo, Integer id){
		this.freebase_id = fr_id;
		this.answer = an;
		this.type = t;
		this.hints = h;
		this.location = lo;
		this.ID = id;
	}
	
	public String get_freebase_id(){
		return this.freebase_id;
	}
	
	public String get_answer(){
		return this.answer;
	}
	
	public String get_type(){
		return this.type;
	}
	
	public List<String> get_hints(){
		return this.hints;
	}
	
	public List<Double> get_location(){
		return this.location;
	}
	
	public Integer get_ID(){
		return this.ID;
	}
	
	public String toString(){
		return new StringBuffer("FB_id: ").append(this.freebase_id)
				.append(" Answer: ").append(this.answer)
				.append(" Type: ").append(this.type)
				.append(" Hints: ").append(this.hints)
				.append(" Location: ").append(this.location)
				.append(" ID: ").append(this.ID)
				.toString();
	}
	

}
