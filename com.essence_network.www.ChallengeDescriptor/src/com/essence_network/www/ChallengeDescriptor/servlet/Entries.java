package com.essence_network.www.ChallengeDescriptor.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import org.json.simple.JSONObject;


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

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // Set a cookie for the user, so that the counter does not increate
    // every time the user press refresh
	  
	int instanceNumber;
  	int hintNumber;
  	Random randomGenerator = new Random();
  	
    HttpSession session = request.getSession(true);
    String answer=request.getParameter("answer");
    JSONObject ret = new JSONObject();
    
    if(request.getCharacterEncoding() == null)
        request.setCharacterEncoding("UTF-8");
        
    if (session.isNew() || session.getAttribute("instanceNumber")==null) {
    	instanceNumber = randomGenerator.nextInt(entries.size());
    	hintNumber = -1;
    	
        session.setAttribute("instanceNumber", instanceNumber);
        session.setAttribute("hintNumber", hintNumber);
    } else {    	
    	instanceNumber = (Integer)session.getAttribute("instanceNumber");
    	hintNumber = (Integer)session.getAttribute("hintNumber");
    }
    
    if (instanceNumber >= entries.size()) {
    	instanceNumber = 0;
    }
    
    String hint = "";
    List<String> hints = entries.get(instanceNumber).get_hints();
    //out.println("Total size of hints: "+hints.size());
    
    if (entries.get(instanceNumber).get_answer().equals(answer)) {
		ret.put("success", "YES");
		ret.put("next_hint", "");
		ret.put("game_progress", "Over");
		
	} else {
	    if (hintNumber < hints.size()-1) {
	    	hintNumber++; hint = hints.get(hintNumber);  
	    	ret.put("success", "NO");
	    	ret.put("next_hint", hint);
	    	ret.put("game_progress", "Going");
	    } else {
	    	ret.put("success", "NO");
			ret.put("game_progress", "Over");
			ret.put("answer", entries.get(instanceNumber).get_answer());
	    }
	}

    // Set the session valid for 3600 secs
    session.setMaxInactiveInterval(3600);
	session.setAttribute("instanceNumber", instanceNumber);
    session.setAttribute("hintNumber", hintNumber);
    //response.setContentType("text/x-json");
    response.setContentType("application/json");
    response.getWriter().write(ret.toString());
  }

  
  @Override
  public void init() throws ServletException {
    er = new EntriesReader();
    try {
      entries = er.getContent();
    } catch (Exception e) {
      getServletContext().log("An exception occurred in EntriesReader", e);
      throw new ServletException("An exception occurred in EntriesReader"
          + e.getMessage());
    }
  }
  
  public void destroy() {
    super.destroy();
//    try {
//      er.save(count);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
  }

} 