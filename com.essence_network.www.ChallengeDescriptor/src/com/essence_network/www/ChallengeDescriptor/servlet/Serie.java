package com.essence_network.www.ChallengeDescriptor.servlet;

import java.util.LinkedList;

import org.json.simple.JSONArray;

import com.essence_network.com.ChallengeDescriptor.dao.ChallengeEntry;

public class Serie {
	
	private ChallengeEntry entry;
	private LinkedList<Trial> serie;
	private boolean pending;
	private boolean success;
	
	public Serie(ChallengeEntry c){
		this.entry = c;
		this.serie = new LinkedList<>();
		this.pending = true;
		this.success = false;
	}
	
	public void add_trial(){
		if(serie.isEmpty()){
			serie.add(new Trial(this.entry));
		}
		else{
			serie.add(new Trial(serie.getLast()));
		}
	}
	
	public boolean test_trial(String answer){
		if(serie.isEmpty()){
			serie.add(new Trial(this.entry));
		}
		if(success = serie.getLast().test_answer(answer)){
			pending = false;
		}
		if(this.is_last_trial()){
			pending = false;
		}
		return success;
	}
	
	private boolean is_last_trial(){
		if(serie.isEmpty()){
			return false;
		}
		return serie.getLast().last_trial();
	}
	
	public boolean still_pending(){
		return this.pending;
	}
	
	public boolean isEmpty(){
		return serie.isEmpty();
	}
	
	public String toString(int debug){
		String out = "";
		for(int i=0; i < serie.size(); i++){
			out += "Describer: "+serie.get(i).get_hint()+"\n";
			if(serie.get(i).get_answer() != null){
				out += "Guesser: "+serie.get(i).get_answer()+"\n";
				if(serie.get(i).is_valid()){
					out += "Describer: YES! "+this.entry.get_answer();
					break;
				}
				else if(!serie.get(i).last_trial()){
					out += "Describer: No! \n";
				}
				else{
					out += "Describer: No!";
				}
			}
		}
		return out;
	}
	
	public JSONArray toJSON(){
		JSONArray out = new JSONArray();
		for(Trial t : serie){
			out.add(t.toJson());
		}
		return out;
	}

}
