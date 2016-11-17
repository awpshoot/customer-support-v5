<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="com.wrox.Ticket"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <a href="<c:url value="/login?logout" />">Logout</a>
        <h2>Ticket #${ticketId}: <c:out value="${ticket.subject}" /></h2>
        <i>Customer Name - <c:out value="${ticket.customerName}" /></i><br /><br />
        <c:out value="${ticket.body}" /><br /><br />
        <c:if test="${ticket.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${ticket.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/tickets">
                    <c:param name="action" value="download" />
                    <c:param name="ticketId" value="${ticketId}" />
                    <c:param name="attachment" value="${attachment.name}" />
                </c:url>"><c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/tickets" />">Return to list tickets</a>
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
