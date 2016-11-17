<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Login</h2>
        You must log in to access the customer support site.<br /><br />
        <c:if test="${loginFailed}">
            <b>The username and password you entered are not correct. Please try
                again.</b><br /><br />
        </c:if>
        <form id="login_form" method="POST" action="<c:url value="/login" />">
            Username<br />
            <input type="text" name="username" /><br /><br />
            Password<br />
            <input type="password" name="password" /><br /><br />
            <input type="submit" value="Log In" />
        </form>
        <br/><br/><br/><br/>
        <div>lalala<a href="###"  title="提交啦" id="submit" collect="pay">click</a></div>
        <div>div_test</div><br/>
        <a>a_test</a><br/>
        <input collect="input_collect"></input>
        <button>button_test</button><br/>
        <br/><br/><br/><br/>
        <input></input>
    </body>
    <script language="javascript" type="text/javascript">
    	
    </script>
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
