package com.essence_network.www.ChallengeDescriptor.servlet;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.parser.JSONParser;

import com.essence_network.com.ChallengeDescriptor.dao.ChallengeEntry;

/**
 * Servlet implementation class ChallengeInstance
 */
@WebServlet("/ChallengeInstance")
public class ChallengeInstance extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<ChallengeEntry> entries = new ArrayList<>(); 
	
	public void load_entries(String direction){
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(new FileReader(direction));
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeInstance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
