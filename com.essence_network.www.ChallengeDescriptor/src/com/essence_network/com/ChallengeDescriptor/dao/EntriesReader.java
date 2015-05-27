package com.essence_network.com.ChallengeDescriptor.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EntriesReader {

  public ArrayList<ChallengeEntry> getContent() throws FileNotFoundException, IOException, ParseException {
	 
	  ArrayList<ChallengeEntry> list = new ArrayList<>();

	  JSONParser parser = new JSONParser();
	  InputStream in = this.getClass().getClassLoader().getResourceAsStream("Data.json");
	  JSONArray a = (JSONArray) parser.parse(new InputStreamReader(in));

	  while(!a.isEmpty()){
		  JSONObject obj = (JSONObject)a.remove(0);
		  String Freebase_id = obj.get("Freebase_id").toString();
		  String Answer = obj.get("Answer").toString();
		  String Type = obj.get("Type").toString();
		  Integer Id = Integer.parseInt(obj.get("Id").toString());
		  List<String> Hints = (List<String>) obj.get("Hints");
		  Double l = Double.parseDouble(obj.get("Location_longitude").toString());
		  Double L = Double.parseDouble(obj.get("Location_latitude").toString());
		  List<Double> Location = new ArrayList<>();
		  Location.add(L);
		  Location.add(l);
		  list.add(new ChallengeEntry(Freebase_id, Answer, Type, Hints, Location, Id));
	  }

//	  for(ChallengeEntry c : list){
//		  System.out.println(c.toString());
//	  }


	  
	  
    return list;
  }

//  public void save(int count) throws Exception {
//    FileWriter fileWriter = null;
//    PrintWriter printWriter = null;
//    fileWriter = new FileWriter("/Users/niki/FileCounter.initial");
//    printWriter = new PrintWriter(fileWriter);
//    printWriter.println(count);
//
//    // make sure to close the file
//    if (printWriter != null) {
//      printWriter.close();
//    }
//  }
  
//  private String readFile(String path, Charset encoding) 
//		  throws IOException 
//  {
//	  byte[] encoded = Files.readAllBytes(Paths.get(path));
//	  return new String(encoded, encoding);
//  }
} 


