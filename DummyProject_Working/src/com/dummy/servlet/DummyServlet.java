package com.dummy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dummy.test.TestClass;

/**
 * Servlet implementation class DummyServlet
 */
@WebServlet(asyncSupported = true, 
			urlPatterns = { "/runDummy" },
			loadOnStartup = 1
)
public class DummyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DummyServlet() {
    }

    List<TestClass> list = new ArrayList<TestClass>();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(
		HttpServletRequest request
	, 	HttpServletResponse response
	) throws ServletException, IOException {
		response.setContentType("application/json");
		for(int i=0; i< 10; i++) {
			list.add(new TestClass("Task_No_" + (i+1)));
		}
		
		list.forEach((l_obj)->{
			Thread l_thread = new Thread(l_obj, l_obj.getName());
			l_thread.start();
		});
		
		JSONObject l_json = new JSONObject();
		l_json.put("message", "All Tasks started. Please check the logs for progress.");
		
		response.getOutputStream().write(l_json.toString().getBytes("UTF-8"));
	}
}
