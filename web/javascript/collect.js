


//记录客户端脚本错误 
//_VI.syserror = [];
//window.onerror = function(error) {
//	try { 
//		var msg; 
//		for (var i = 0; i < arguments.length; i++) { 
//			if (i == 0 || i == 2) { 
//				msg += " | " + arguments[i]; 
//			} 
//		} 
//		if (msg.length > 0 ) { 
//			_VI.syserror.push('syserror:'+msg);
//		} 
//		return true; 
//	} catch (e) { }; 
//};  

$(document).ready(

	function(windows) {
	   'use strict';
	     
	    //扩展帮助方法
	    var helper = {};

	    // 唯一标示 uuid
	    helper.uuid = function() {
	      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
	        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
	        return v.toString(16);
	      });
	    };

	    // 得到cookie
	    helper.getCookie = function(name) {
	      var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	      if(arr= document.cookie.match(reg)){
	        return unescape(arr[2]);
	      } else {
	        return null;
	      }
	    };

	    helper.setCookie = function(name, value, time) {
	      if(time) {
	        document.cookie = name + "=" + escape(value) + ";expires=" + time;
	      } else {
	        document.cookie = name + "=" + escape(value) + ";";
	      }
	    };

	    //遍历
	    /**
	     * @method each
	     * @parame loopable 要遍历的对象
	     * @parame callback 回调函数
	     * @parame self 上下文
	     **/
	     helper.each = function(loopable, callback, self) {
	       	var additionalArgs = Array.prototype.slice.call(arguments,3);
	       	if(loopable) {	
	       	  if(loopable.length === +loopable.length) {
	       	  	var i;
	       	  	for(var i=0; i<loopable.length; i++) {
	       	  	  callback.apply(self, [loopable[i],i].concat(additionalArgs));
	       	  	}
	       	  } else {
	       	  	  for(var item in loopable) {
	       	  	  	callback.apply(self, [loopable[item], item].concat(additionalArgs));
	       	  	  }
	       	  }
	       	}
	     }; 

	   //扩展
	   /**
	    *@method extend
	    *@parame base 要扩展的对象
	    *@return base  返回扩展后的对象
	    **/
	    helper.extend = function(base) {
	      helper.each(Array.prototype.slice.call(arguments, 1), function(extensionObject) {
	      	helper.each(extensionObject, function(value, key) {
	      	  if(extensionObject.hasOwnPrototype(key)) {
	      	  	base[key] = value;
	      	  }
	      	});
	      });
	      return base;	
	    };

	   //返回数组元素所在的位置，确定是否包含在里面
	    /**
	     *@method indexOf
	     *@parame arrayToSearch 查找的对象
	     *@parame item 查找的元素
	     *@return args  返回位置
	     **/
	     helper.indexOf = function(arrayToSearch,item){
	       	if(Array.prototype.indexOf){
	       		return arrayToSearch.indexOf(item);
	       	} else {
	       		for(var i=0; i< arrayToSearch.length; i++){
	       			if(arrayToSearch[i] === item) return i;
	       		}
	       		return -1;
	       	}
	     }; 

	    // 绑定事件
	    helper.on = function(target, type, handler) {
	       if(target.addEventListener) {
	       	  target.addEventListener(type, handler, false);
	       } else {
	       	  target.attachEvent("on" + type, 
	       	  	function(event) {
	       	  	   return handler.call(target, event);
	       	  	}, false);
	       }	
	    };
	    
	    // 请求，保存数据
	    helper.send = function(obj, url) {
			$.ajax({
			    type: "post",
			    dataType: "",
			    url: url,
			    data: obj,
			    success: function (data) {
			    	
			    }
			});
	    };

	  	helper.changeJSON2Query =  function (jsonObj) {
	  		var args = '';
	  		for (var i in jsonObj) {
	  			if (args != '') {
	  				args += '&';
	  			}
	  			args += i + '=' + encodeURIComponent(jsonObj[i]);
	  		}
	  		return args;
	  	};


	    var collect = {};
	    var host = window.location.host;
	    
	    collect.parmas = {};

	    collect.url = '127.0.0.1:3000/users?';

	    collect.uuid = helper.uuid();

	    collect.setParames = function() {
	       if(document) {
	       	  this.parmas.domain = document.domain || '';
	       	  this.parmas.url = document.URL || '';
	       	  this.parmas.title = document.title || '';
	       	  this.parmas.referrer = document.referrer;
	       }
	       if(window && window.screen) {
	       	  this.parmas.sh = window.screen.height || 0;
	       	  this.parmas.sw = window.screen.width || 0;
	       	  this.parmas.cd = window.screen.colorDepth || 0;
	       }
	  	   if (navigator) {
	  		   this.parmas.lang = navigator.language || '';
	  		   this.parmas.userAgent = navigator.userAgent || '';
	  	   }
	  	   if(document && document.cookie) {
	  	   	   this.parmas.cookie = document.cookie;
	  	   }
	  	   if(sessionId){
	  		   this.parmas.sessionid = sessionId
	  	   }
	  	   if(!this.parmas.systemName) {
	  	   	   this.parmas.systemName = "default";
	  	   }
	       this.parmas.target = [];
	       //解析 配置项
	       if(typeof _VI != "undefined") {
	       	  for(var i in _VI) {
	       	  	switch(_VI[i][0]) {
	       	  		case '_setAccount':
	       	  		   this.parmas.accout = _VI[i][1];
	       	  		   break;
	       	  		case 'Action':
	       	  		   this.parmas.action = _VI[i].slice(1);
	       	  		   break;
	       	  		case 'Target':
	       	  		   for(var j = 1;j < _VI[i].length;j++){
	       	  			  this.parmas.target += "<" + _VI[i][j] + ">";
	       	  		   }
	       	  		   break;
	       	  		case 'Url':
	       	  		   collect.url = _VI[i][1];
	       	  		   break;
	       	  		case 'CookieBool':
	       	  		   if(_VI[i][1] == 'false') {
	       	  		   	 delete this.parmas.cookie;
	       	  		   } 
	       	  		   break;   
	       	  		case 'systemName': // 指定哪个平台下的数据标记
	       	  		   this.parmas.systemName = _VI[i][1];   
	       	  		   break;      
	       	  		default:
	       	  		   break;       
	       	  	}
	       	  }
	          if(_VI.syserror && _VI.syserror.length) {
	            this.parmas.syserror = _VI.syserror;
	            _VI.syserror = [];
	          } else {
	            delete this.parmas.syserror;
	          }

	          // 用户自定义字段
	          if(_VI.userConfig) {
	             for(var k in _VI.userConfig) {
	                 if(_VI.userConfig.hasOwnProperty(k)) {
	                     this.parmas[k] = _VI.userConfig[k]
	                 }
	             }
	          }
	       } else {
	       	  throw "必须定义全局配置变量 _VI，配置指定的请求Url。示例： var _VI = _VI || []; _VI.push(['Url','127.0.0.1/users?']);";
	       }
	    };

	    collect.getParames = function() {
	        return this.parmas;
	    };

	    // 用户的停留时间
	    collect.timer = function() {
	    	this.disp = new Date().getTime();
	    };

	    collect.event = function() {
	      	var that = this;
	      	helper.on(document.getElementsByTagName("body")[0],'click', function(e) {
	      		var $target = e.target || e.srcElement;
	            var currtagName = $target.nodeName.toLowerCase();
	            if(currtagName == "body" || currtagName == "html" || currtagName == "") {
	               return 0;
	            }
	      		if(helper.indexOf(that.parmas.target, currtagName) > -1 || $target.getAttribute('collect')) {
		             if(!helper.getCookie('_VI_userAccect')) {
		                helper.setCookie('_VI_userAccect', that.uuid);
		                // 初次进入网站，返回用户凭证。
		                that.parmas.cookie = '_VI_userAccect='+that.uuid + ';'+ that.parmas.cookie;
		             }
		             var attrCo = $target.getAttribute('collect');
		             if(attrCo) {
		               that.parmas.collectMark = attrCo;
		             } else {
		               delete that.parmas.collectMark; 
		             }

	                that.parmas.clickElement = '{nodeName:'+$target.nodeName + ',id:' + $target.id +
	                   ',title:' + $target.title + ',text:' +$target.innerHTML + '}';
	      		    that.setParames();	
	      		    that.parmas.clicktime = new Date().getTime() - that.disp;
	      		    helper.send(that.getParames(), that.url);
	      		}
	      	});
	        
	        // 用户离开页面时返回逗留时间
	        window.onbeforeunload = function(evt){
	           that.parmas.disp = new Date().getTime() - that.disp;
	           if(!helper.getCookie('_VI_userAccect')) {
	              helper.setCookie('_VI_userAccect', that.uuid);
	           }
	           delete that.parmas.collectMark;
	           delete that.parmas.clickElement;
	    	   that.setParames();	
	    	   helper.send(that.getParames(), that.url);
	        }; 
	    };

	    collect.init = function() {
	       var that = this;
	       that.timer();  
	       that.event();
	       if(!helper.getCookie('_VI_userAccect')) {
	          helper.setCookie('_VI_userAccect', that.uuid);
	       }
	       that.setParames();
	       that.parmas.initime = new Date();
	       helper.send(that.getParames(), that.url);
	       delete that.parmas.syserror;
	    };

	    collect.init();

	}
);
