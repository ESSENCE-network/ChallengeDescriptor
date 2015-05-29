package com.essence_network.www.ChallengeDescriptor.servlet;

import java.util.List;

import org.json.simple.JSONObject;

import com.essence_network.com.ChallengeDescriptor.dao.ChallengeEntry;

public class Trial {
	
	private String valid_answer;
	private List<String> hints;
	private String hint;
	private String answer;
	private boolean valid;
	private int sizeof_hints;
	
	public Trial(Trial t){
		this.valid_answer = t.valid_answer;
		this.answer = null;
		this.valid = false;
		this.sizeof_hints = t.sizeof_hints -1;
		this.hints = t.hints;
		this.hint = hints.get(sizeof_hints-1);
	}
	
	public Trial(ChallengeEntry c){
		this.valid_answer = c.get_answer();
		this.answer = null;
		this.valid = false;
		this.sizeof_hints = c.get_hints().size();
		this.hints = c.get_hints();
		this.hint = hints.get(sizeof_hints-1);
	}
	
	public boolean test_answer(String answer){
		this.answer = answer;
		System.out.println(this.answer);
		System.out.println(this.valid_answer);
		if(this.valid_answer.equals(this.answer)){
			this.valid = true;
			return this.valid;
		}
		return false;
	}
	
	public boolean last_trial(){
		if(this.sizeof_hints == 1){
			return true;
		}
		return false;
	}
	
	public String get_answer(){
		return this.answer;
	}
	
	public String get_hint(){
		return this.hint;
	}
	
	public boolean is_valid(){
		return this.valid;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		JSONObject out = new JSONObject();
		out.put("hint", this.hint);
		out.put("answer", this.answer);
		out.put("right_answer", this.valid_answer);
		out.put("valid", this.valid);
		return out;
	}

}
