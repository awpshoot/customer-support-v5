package com.wrox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VisitInfoServlet
 */
@WebServlet(
        name = "VisitInfoServlet",
        urlPatterns = "/visitinfo"
)
public class VisitInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final List<Map<String,String[]>> visitDatabase = new Vector<Map<String,String[]>>();
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
		parameterMap.put("clientip", new String[]{getIpAddr(request)});
		if(parameterMap.get("clickElement") instanceof String[]&&parameterMap.get("clickElement").length > 0){
			parameterMap.put("operateid", new String[]{"2"});
			parameterMap.put("operatetype", new String[]{"点击"});
		}else if(parameterMap.get("clientime") instanceof String[]&&parameterMap.get("clientime").length > 0){
			parameterMap.put("operateid", new String[]{"1"});
			parameterMap.put("operatetype", new String[]{"进入"});
		}else if(parameterMap.get("disp") instanceof String[]&&parameterMap.get("disp").length > 0){
			parameterMap.put("operateid", new String[]{"0"});
			parameterMap.put("operatetype", new String[]{"离开"});
		}
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

    private final static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
