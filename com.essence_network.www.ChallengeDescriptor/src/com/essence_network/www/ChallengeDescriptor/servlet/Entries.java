package com.essence_network.www.ChallengeDescriptor.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	  
    HttpSession session = request.getSession(true);
    if (session.isNew()) {
    	instanceNumber = 0;
    	hintNumber = 0;
    	
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
    if (hintNumber >= hints.size()) {
    	hint = hints.get(hintNumber);
    } else {
    	instanceNumber++;
    	hintNumber = 0;
    }
    
     
    
    

    
    // Set the session valid for 3600 secs
    session.setMaxInactiveInterval(3600);
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
//    if (session.isNew()) {
//      count++;
//    } 
    
    //JSONParser parser = new JSONParser();
    
    out.println(instanceNumber + " " + hintNumber + " " + hint);
    
	instanceNumber++;
	hintNumber++;
	session.setAttribute("instanceNumber", instanceNumber);
    session.setAttribute("hintNumber", hintNumber);

//    for(ChallengeEntry c : entries){
//    	out.println(c.toString());
//    }
    //out.println(content);
    //out.println("This site has been accessed " + count + " times." + System.getProperty("catalina.base"));
    
    // saving file at each request
//    try {
//        dao.save(count);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
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