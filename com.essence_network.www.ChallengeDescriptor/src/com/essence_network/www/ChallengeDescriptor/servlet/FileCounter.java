package com.essence_network.www.ChallengeDescriptor.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;

import com.essence_network.com.ChallengeDescriptor.dao.FileDao;

/**
 * Servlet implementation class FileCounter
 */

@WebServlet("/FileCounter")
public class FileCounter extends HttpServlet {
  private static final long serialVersionUID = 1L;

  int count;
  private FileDao dao;

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // Set a cookie for the user, so that the counter does not increate
    // every time the user press refresh
    HttpSession session = request.getSession(true);
    // Set the session valid for 2 secs
    session.setMaxInactiveInterval(2);
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    if (session.isNew()) {
      count++;
    } 
    
    JSONParser parser = new JSONParser();
    
    
    out.println("This site has been accessed " + count + " times." + System.getProperty("catalina.base"));
    
    // saving file at each request
    try {
        dao.save(count);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  
  @Override
  public void init() throws ServletException {
    dao = new FileDao();
    try {
      count = dao.getCount();
    } catch (Exception e) {
      getServletContext().log("An exception occurred in FileCounter", e);
      throw new ServletException("An exception occurred in FileCounter"
          + e.getMessage());
    }
  }
  
  public void destroy() {
    super.destroy();
    try {
      dao.save(count);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

} 