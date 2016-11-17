<%--@elvariable id="numberOfSessions" type="java.lang.Integer"--%>
<%@ page import="java.util.List" %>
<%!
    private static String toString(long timeInterval)
    {
        if(timeInterval < 1_000)
            return "less than one second";
        if(timeInterval < 60_000)
            return (timeInterval / 1_000) + " seconds";
        return "about " + (timeInterval / 60_000) + " minutes";
    }
%>
<%
    @SuppressWarnings("unchecked")
    List<HttpSession> sessions =
            (List<HttpSession>)request.getAttribute("sessionList");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <a href="<c:url value="/login?logout" />">Logout</a>
        <h2>Sessions</h2>
        There are a total of ${numberOfSessions} active sessions in this
        application.<br /><br />
        <%
            long timestamp = System.currentTimeMillis();
            for(HttpSession aSession : sessions)
            {
                out.print(aSession.getId() + " - " +
                        aSession.getAttribute("username"));
                if(aSession.getId().equals(session.getId()))
                    out.print(" (you)");
                out.print(" - last active " +
                        toString(timestamp - aSession.getLastAccessedTime()));
                out.println(" ago<br />");
            }
        %>
    </body>
        <script language="javascript" src="<%=request.getContextPath() %>/javascript/jquery.js" 
        type="text/javascript"></script>
        <script language="javascript" type="text/javascript">
    	var sessionId = "${pageContext.session.id}";
    	var _VI = _VI || [];
    	//后台VisitInfo，收集的地址平台 host，默认不需要配置
    	_VI.push(['Url','<%=request.getScheme() + "://"  + request.getServerName() 
    	+ ":" + request.getServerPort()  + "/" + request.getContextPath()%>/visitinfo']);

    	// 此处可以记录系统
    	_VI.push(['systemname','知识库系统']);

    	//此处可以记录账户
    	_VI.push(['_setAccount', '奥巴马openid']);

    	//将要记录的标签元素类型名称写在Target后面，在另外需要记录点击的标签元素中加入collect属性
    	_VI.push(['Target','a','div','button']);

    	//是否收集Cookie
    	_VI.push(['CookieBool',true]);
    	
    	//用户自定义收集字段
    	//_VI.userConfig = {
    	//author: '白云飘飘'
    	//};
    </script>
    <script language="javascript" src="<%=request.getContextPath() %>/javascript/collect.js" 
        type="text/javascript"></script>
    
</html>
