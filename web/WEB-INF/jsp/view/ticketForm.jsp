<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <a href="<c:url value="/login?logout" />">Logout</a>
        <h2>Create a Ticket</h2>
        <form method="POST" action="tickets" enctype="multipart/form-data">
            <input type="hidden" name="action" value="create"/>
            Subject<br/>
            <input type="text" name="subject"><br/><br/>
            Body<br/>
            <textarea name="body" rows="5" cols="30"></textarea><br/><br/>
            <b>Attachments</b><br/>
            <input type="file" name="file1"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
        <script language="javascript" src="<%=request.getContextPath() %>/javascript/jquery.js" 
        type="text/javascript"></script>
        <script language="javascript" type="text/javascript">
    	var sessionId = "${pageContext.session.id}";
    	var _VI = _VI || [];
    	//后台VisitInfo，收集的地址平台 host，默认不需要配置
    	_VI.push(['Url','http://127.0.0.1:8082/support/visitinfo']);

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
