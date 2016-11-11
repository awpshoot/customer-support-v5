package com.wrox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class VisitInfoServlet
 */
@WebServlet(
        name = "VisitInfoServlet",
        urlPatterns = "/visitinfo"
)
public class VisitInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final List<Map<String,String[]>> visitDatabase = new ArrayList<Map<String,String[]>>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,String[]> parameterMap = new HashMap(request.getParameterMap());
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String servertime = sdf.format(date);
		String[] strs = new String[]{servertime};
		parameterMap.put("servertime", strs);
		for(Map.Entry<String, String[]> entry:parameterMap.entrySet()){
			String values = "";
			for(int i = 0;i < entry.getValue().length;i++){
				values += entry.getValue()[i];
			}
			System.out.println("Key = " + entry.getKey() + ", Value = " + values);
		}
		System.out.println("----------------------------------------------------");
		visitDatabase.add(parameterMap);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入dopost");
	}

}
