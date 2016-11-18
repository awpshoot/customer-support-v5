package com.wrox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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
       
	private final List<Map<String,String>> visitDatabase = new Vector<Map<String,String>>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitInfoServlet() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 * init方法在servlet初始化时设置一个定时器，从启动后每隔五秒（可在程序中改）
	 * 将全局变量visitDatabase中的数据插入数据库中，
	 * 然后清空visitDatabase变量
	 */
	public void init(ServletConfig config) throws ServletException {
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				try {
					Connection conn = JdbcUtil.getConnectionForMySQL(); //获取cube mysql连接
					Statement stmt = conn.createStatement();
					String sql = "";
					for(int j = 0;j < visitDatabase.size();j++){
						Map<String,String> visitInfoMap = visitDatabase.get(j);
						sql = "INSERT INTO visitinfo(clientip ,sessionid ,cookie ,useragent ,account ,systemname ,"
								+ "title ,url ,referrer ,operateid ,operatetype ,target ,clickElement ,collectMark ,"
								+ "clientime ,disp ,servertime ,cd ,sw ,sh ,lang ) VALUES ('" 
								+visitInfoMap.get("clientip")+ "','" 
								+visitInfoMap.get("sessionid")+ "','"
								+visitInfoMap.get("cookie")+"','"
								+visitInfoMap.get("useragent")+"','"
								+visitInfoMap.get("account")+"','"
								+visitInfoMap.get("systemname")+"','"
								+visitInfoMap.get("title")+"','"
								+visitInfoMap.get("url")+"','"
								+visitInfoMap.get("referrer")+"',"
								+visitInfoMap.get("operateid")+",'"
								+visitInfoMap.get("operatetype")+"','"
								+visitInfoMap.get("target")+"','"
								+visitInfoMap.get("clickElement")+"','"
								+visitInfoMap.get("collectMark")+"','"
								+visitInfoMap.get("clientime")+"','"
								+visitInfoMap.get("disp")+"','"
								+visitInfoMap.get("servertime")+"',"
								+visitInfoMap.get("cd")+","
								+visitInfoMap.get("sw")+","
								+visitInfoMap.get("sh")+",'"
								+visitInfoMap.get("lang")+"')";
						//System.out.println(sql);
						stmt.execute(sql);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				visitDatabase.clear();
			}
		}, 1000*5l, 1000*5l);//此处设置几秒，10分钟将5l改为600l。结尾字母l。
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 * 此处接收每个进入，离开，点击事件并循环填充空值，存入visitDatabase全局变量中。
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,String[]> parameterMap = new HashMap(request.getParameterMap());
		//判断事件
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
		//循环输出可注释
		for(Map.Entry<String, String[]> entry:parameterMap.entrySet()){
			String values = "";
			for(int i = 0;i < entry.getValue().length;i++){
				values += entry.getValue()[i];
			}
			System.out.println("Key = " + entry.getKey() + ", Value = " + values);
		}
		System.out.println("----------------------------------------------------");
		Map<String, String> visitInfoMap = new HashMap();
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		visitInfoMap.put("servertime", sdf.format(date));
		visitInfoMap.put("clientip", getIpAddr(request));
		//填充空值
		if(parameterMap.get("sessionid") instanceof String[]&&parameterMap.get("sessionid").length > 0){
			visitInfoMap.put("sessionid", parameterMap.get("sessionid")[0]);
		}else{
			visitInfoMap.put("sessionid", "");
		}
		if(parameterMap.get("cookie") instanceof String[]&&parameterMap.get("cookie").length > 0){
			visitInfoMap.put("cookie", parameterMap.get("cookie")[0]);
		}else{
			visitInfoMap.put("cookie", "");
		}
		if(parameterMap.get("useragent") instanceof String[]&&parameterMap.get("useragent").length > 0){
			visitInfoMap.put("useragent", parameterMap.get("useragent")[0]);
		}else{
			visitInfoMap.put("useragent", "");
		}
		if(parameterMap.get("account") instanceof String[]&&parameterMap.get("account").length > 0){
			visitInfoMap.put("account", parameterMap.get("account")[0]);
		}else{
			visitInfoMap.put("account", "");
		}
		if(parameterMap.get("title") instanceof String[]&&parameterMap.get("title").length > 0){
			visitInfoMap.put("title", parameterMap.get("title")[0]);
		}else{
			visitInfoMap.put("title", "");
		}
		if(parameterMap.get("url") instanceof String[]&&parameterMap.get("url").length > 0){
			visitInfoMap.put("url", parameterMap.get("url")[0]);
		}else{
			visitInfoMap.put("url", "");
		}
		if(parameterMap.get("referrer") instanceof String[]&&parameterMap.get("referrer").length > 0){
			visitInfoMap.put("referrer", parameterMap.get("referrer")[0]);
		}else{
			visitInfoMap.put("referrer", "");
		}
		if(parameterMap.get("operateid") instanceof String[]&&parameterMap.get("operateid").length > 0){
			visitInfoMap.put("operateid", parameterMap.get("operateid")[0]);
		}else{
			visitInfoMap.put("operateid", "");
		}
		if(parameterMap.get("operatetype") instanceof String[]&&parameterMap.get("operatetype").length > 0){
			visitInfoMap.put("operatetype", parameterMap.get("operatetype")[0]);
		}else{
			visitInfoMap.put("operatetype", "");
		}
		if(parameterMap.get("target") instanceof String[]&&parameterMap.get("target").length > 0){
			visitInfoMap.put("target", parameterMap.get("target")[0]);
		}else{
			visitInfoMap.put("target", "");
		}
		if(parameterMap.get("clickElement") instanceof String[]&&parameterMap.get("clickElement").length > 0){
			visitInfoMap.put("clickElement", parameterMap.get("clickElement")[0]);
		}else{
			visitInfoMap.put("clickElement", "");
		}
		if(parameterMap.get("collectMark") instanceof String[]&&parameterMap.get("collectMark").length > 0){
			visitInfoMap.put("collectMark", parameterMap.get("collectMark")[0]);
		}else{
			visitInfoMap.put("collectMark", "");
		}
		if(parameterMap.get("clientime") instanceof String[]&&parameterMap.get("clientime").length > 0){
			visitInfoMap.put("clientime", parameterMap.get("clientime")[0]);
		}else{
			visitInfoMap.put("clientime", "0000-00-00 00:00:00");
		}
		if(parameterMap.get("disp") instanceof String[]&&parameterMap.get("disp").length > 0){
			visitInfoMap.put("disp", parameterMap.get("disp")[0]);
		}else{
			visitInfoMap.put("disp", "");
		}
		if(parameterMap.get("cd") instanceof String[]&&parameterMap.get("cd").length > 0){
			visitInfoMap.put("cd", parameterMap.get("cd")[0]);
		}else{
			visitInfoMap.put("cd", "");
		}
		if(parameterMap.get("sw") instanceof String[]&&parameterMap.get("sw").length > 0){
			visitInfoMap.put("sw", parameterMap.get("sw")[0]);
		}else{
			visitInfoMap.put("sw", "");
		}
		if(parameterMap.get("sh") instanceof String[]&&parameterMap.get("sh").length > 0){
			visitInfoMap.put("sh", parameterMap.get("sh")[0]);
		}else{
			visitInfoMap.put("sh", "");
		}
		if(parameterMap.get("lang") instanceof String[]&&parameterMap.get("lang").length > 0){
			visitInfoMap.put("lang", parameterMap.get("lang")[0]);
		}else{
			visitInfoMap.put("lang", "");
		}
		if(parameterMap.get("systemname") instanceof String[]&&parameterMap.get("systemname").length > 0){
			visitInfoMap.put("systemname", parameterMap.get("systemname")[0]);
		}else{
			visitInfoMap.put("systemname", "");
		}
		
		visitDatabase.add(visitInfoMap);
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
	/**
	 * 网上找的获取真实ip的方法
	 * @param request
	 * @return
	 */
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
