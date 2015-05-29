package com.essence_network.www.ChallengeDescriptor.servlet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.json.simple.parser.JSONParser;
import com.essence_network.com.ChallengeDescriptor.dao.ChallengeEntry;
import com.essence_network.com.ChallengeDescriptor.dao.EntriesReader;

/**
 * Servlet implementation class FileCounter
 */

@WebServlet("/Entries")

public class Entries extends HttpServlet {
  private static final long serialVersionUID = 1L;

  //int count;
  ArrayList<ChallengeEntry> entries;
  private EntriesReader er;
  JSONParser parser = new JSONParser();

@SuppressWarnings("unchecked")
@Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // Set a cookie for the user, so that the counter does not increase
    // every time the user press refresh
	  
	 int serie_number; 
	 Set<Serie> memorised_series;
	 Serie current_serie;
	 
	 HttpSession session = request.getSession(true);
	 String answer=request.getParameter("answer");
	 
	 if(request.getCharacterEncoding() == null)
		 request.setCharacterEncoding("UTF-8");
	 
	 PrintWriter out = response.getWriter();
	 
	 session.setMaxInactiveInterval(60);
	
	 if (session.isNew()) {
		 
		serie_number = entries.size()-1;
	   	memorised_series = new HashSet<>();
	   	current_serie = new Serie(entries.get(serie_number));
	    	
	    session.setAttribute("serie_number", serie_number);
	    session.setAttribute("memorised_series", memorised_series);
	    session.setAttribute("current_serie", current_serie);

	 }
	 
	 else{
		 
		 serie_number = (Integer) session.getAttribute("serie_number");
		 memorised_series = (HashSet<Serie>) session.getAttribute("memorised_series");
		 current_serie = (Serie) session.getAttribute("current_serie");
	 }
	 
	 if(serie_number>-1){
		 if(current_serie.isEmpty())
			 current_serie.add_trial();
		 if(answer != null){
			 if(current_serie.test_trial(answer) || !current_serie.still_pending()){
				 memorised_series.add(current_serie);
				 serie_number += -1;
				 if(serie_number>-1){
					 current_serie = new Serie(entries.get(serie_number));
				 }
			 }
			 else
				 current_serie.add_trial();
		 }
		 out.println(current_serie.toString(0));
		 session.setAttribute("serie_number", serie_number);
		 session.setAttribute("memorised_series", memorised_series);
		 session.setAttribute("current_serie", current_serie);
	 }
	  
	 else{
		 out.println("Game finished");
		 InputStream in = this.getClass().getClassLoader().getResourceAsStream("Save.json");
		 //FileReader in = new FileReader("Save.json");
		 Object input = new Object();
		 try {input = parser.parse(new InputStreamReader(in));} catch (ParseException e) {e.printStackTrace();}
		 in.close();
		 
		 JSONObject save = new JSONObject();
		 JSONArray a = new JSONArray();
		 JSONArray output = (JSONArray)input;
		 
		 for(Serie s : memorised_series){
			 a.add(s.toJSON());
		 }
		 save.put("series", a);
		 save.put("sessionID", session.getId());
		 output.add(save);
		 
		 File test = new File("Save.json");
		 FileWriter f = new FileWriter(test);
		 BufferedWriter bf = new BufferedWriter(f);
		 for(int i=0;i<output.size();i++){
			 JSONObject j = (JSONObject) output.get(i);
			 bf.append(j.toJSONString());
		 }
		 bf.flush();
		 bf.close();
		 System.out.println(test.exists());
		 System.out.println(test.getAbsolutePath());
		 session.invalidate();

	 }
 	
	response.setContentType("text/plain");
	  
  }

  
  @Override
  public void init() throws ServletException {
    er = new EntriesReader();
    try {
      //entries = er.getContent();
    	entries = new ArrayList<>();
    	entries.add(er.getContent().get(1));
    } catch (Exception e) {
      getServletContext().log("An exception occurred in EntriesReader", e);
      throw new ServletException("An exception occurred in EntriesReader"
          + e.getMessage());
    }
  }
  
  public void destroy() {
	
    super.destroy();
  }

} 