/**
 * 工具类
 */
var util ={

	array : {
		isInArr:function(arr,val){
			if(arr){
				var index = 0;
				$.each(arr,function(i,obj){
					if(obj==val){
						index=1;
						return false;
					}
				})
				 if(index>0){
					 return true;
				 }
			}
			return false;
		},
		remove : function(arr , val) {    
            var index = arr.indexOf(val);
            if (index > -1) {
                arr.splice(index, 1);
            }
        }
    },

	//验证手机
	checkMobile :function(v){

		return /^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/.test(v);

	},
	//处理手机号 加星号
	dealMobile : function(phone){
		if(phone.length != 11){
			return phone;
		}
		return phone.substr(0, 3)+'*****'+phone.substr(8); 
	},
	//邮箱
	checkMail:function(v){
		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(v);
	},
	//特殊字符
	checkStrValid: function(str) {
		var pattern = new RegExp("[%#$^&*|\\[\\]<>/]");

		return pattern.test(str);
	},
	//文本域替换回车
	replace : function(v){
		return v.replace(/(\r|\n)/g,'<br/>');
	},
	//随机串
	nonceStr:function(){

		var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

	    var maxPos = $chars.length;

	    var noceStr = "";
	    for (i = 0; i < 32; i++) {
    		noceStr += $chars.charAt(Math.floor(Math.random() * maxPos));
	    }

	    return noceStr;
	},
	//时间戳
	timeStamp:function(){
	    var timestamp=new Date().getTime();
	    var times1 = parseInt(parseInt(timestamp));
	    var timestampstring = times1.toString();//一定要转换字符串
	    return timestampstring;
	},
	jsonToString: function(O) {
		var string = [];
		var isArray = function(a) {
			var string = [];
			for (var i = 0; i < a.length; i++) {
				string.push(util.jsonToString(a[i]));
			}
			return string.join(",");
		};
		var isObject = function(obj) {
			var string = [];
			for (var p in obj) {
				if (obj.hasOwnProperty(p) && p != "prototype") {
					string.push("\"" + p + "\":" + util.jsonToString(obj[p]));
				}
			}
			return string.join(",");
		};

		if (O instanceof Function) {
			string.push(O);
		} else {
			if (O instanceof Array) {
				string.push("[" + isArray(O) + "]");
			} else {
				if (typeof O == "object") {
					if (O == null)
						string.push('null');
					else if (O == '')
						string.push('');
					else if (!O)
						string.push('false');
					else
						string.push("{" + isObject(O) + "}");
				} else if (typeof O == "string") {
						string.push("\"" + O + "\"");
				} else 	if (typeof O == "number" && isFinite(O)) {
						string.push(O);
				} else if (typeof O == 'boolean') {
					if (!O)
						string.push('false');
					else
						string.push('true');
				}else if(typeof O == 'undefined'){
					string.push("\"\"");
				}
			}
		}
		return string.join(",");
	},
	loadImg : function (url, callback, error) {
	    var width, height, intervalId, check, div,
		    img = new Image(),
		    body = document.body;
    	img.src = url;
	    // 从缓存中读取
	    if (img.complete) {
    		return callback(img.width, img.height);
	    };
	    // 加载完毕后方式获取
	    img.onload = function () {
		    callback(img.width, img.height);
		    img.onload = img.onerror = null;
	    };
	    // 图片加载错误
	    img.onerror = function () {
		    error && error();
	    };
	},
	//验证字符串是否为空
	isEmpty:function(str){
		return (typeof str== 'undefined' || str==null || (typeof str=='string' && str.replace(/^\s|\s$/g,'') =='') || str == 'null');
	},

	isNotEmpty:function(v){
		return !util.isEmpty(v);
	},
	//持久存储
	store :(function(){
		try{
			window.localStorage.setItem('_localStorage_support_',1);
			return  window.localStorage;
		}catch(e){
			
			return {
			    getItem: function (sKey) {
			      if (!sKey || !this.hasOwnProperty(sKey)) { return null; }
			      return unescape(document.cookie.replace(new RegExp("(?:^|.*;\\s*)" + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*((?:[^;](?!;))*[^;]?).*"), "$1"));
			    },
			    key: function (nKeyId) {
			      return unescape(document.cookie.replace(/\s*\=(?:.(?!;))*$/, "").split(/\s*\=(?:[^;](?!;))*[^;]?;\s*/)[nKeyId]);
			    },
			    setItem: function (sKey, sValue) {
			      if(!sKey) { return; }
			      document.cookie = escape(sKey) + "=" + escape(sValue) + "; expires=Tue, 19 Jan 2038 03:14:07 GMT;path=/";
			      this.length = document.cookie?document.cookie.match(/\=/g).length:0;
			    },
			    length: 0,
			    removeItem: function (sKey) {
			      if (!sKey || !this.hasOwnProperty(sKey)) { return; }
			      document.cookie = escape(sKey) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
			      this.length--;
			    },
			    hasOwnProperty: function (sKey) {
			      return (new RegExp("(?:^|;\\s*)" + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=")).test(document.cookie);
			    }
			};
		}
	})(),

	sessionStore : (function(){
		try{
			window.sessionStorage.setItem('_sessionStorage_support_',1);
			return  window.sessionStorage;
		}catch(e){
			return {
			    getItem: function (sKey) {
			      if (!sKey || !this.hasOwnProperty(sKey)) { return null; }
			      return unescape(document.cookie.replace(new RegExp("(?:^|.*;\\s*)" + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*((?:[^;](?!;))*[^;]?).*"), "$1"));
			    },
			    key: function (nKeyId) {
			      return unescape(document.cookie.replace(/\s*\=(?:.(?!;))*$/, "").split(/\s*\=(?:[^;](?!;))*[^;]?;\s*/)[nKeyId]);
			    },
			    setItem: function (sKey, sValue) {
			      if(!sKey) { return; }
			      document.cookie = escape(sKey) + "=" + escape(sValue) + "; expires=Tue, 19 Jan 2038 03:14:07 GMT;path=/";
			      this.length = document.cookie?document.cookie.match(/\=/g).length:0;
			    },
			    length: 0,
			    removeItem: function (sKey) {
			      if (!sKey || !this.hasOwnProperty(sKey)) { return; }
			      document.cookie = escape(sKey) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
			      this.length--;
			    },
			    hasOwnProperty: function (sKey) {
			      return (new RegExp("(?:^|;\\s*)" + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=")).test(document.cookie);
			    }
			};
		}
	})(),

	//取cookie
	getCookie:function(objName){//获取指定名称的cookie的值
	    var arrStr = document.cookie.split("; ");
	    for(var i = 0;i < arrStr.length;i ++){
			var temp = arrStr[i].split("=");
			if(temp[0] == objName) {
				return unescape(temp[1]);
			}
		}
		return undefined;
	},
	setCookie:function (name,value){  
        var Days = 7;  
        var exp  = new Date();  
        exp.setTime(exp.getTime() + Days*24*60*60*1000);  
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+';path=/;';  
    } ,
    //删除cookie
    delCookie:function(name){//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	   var date = new Date();
	   date.setTime(date.getTime() - 10000);
	   document.cookie = name + "=0;  path=/;expires=" + date.toGMTString();
	},
	//url 参数
	getUrlParams: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return r[2];
		return null;
	},
	
	//比较两个日期大小
	compareDate : function(startTime,endTime){  
		if(typeof startTime == 'object'){
			startTime = util.formatDate('YYYY-MM-DD hh:mm:ss');
		}
		if(typeof endTime == 'object'){
			endTime = util.formatDate('YYYY-MM-DD hh:mm:ss');
		}
	    var start = new Date(startTime.replace("-", "/").replace("-", "/"));  
	    var end=new Date(endTime.replace("-", "/").replace("-", "/"));      
	    if(end<start){  
	        return false;  
	    }  
	    return true;  
	},
	//校验密码
	checkPwd : function(pwd){
		if(/^[a-zA-Z]+$/g.test(pwd)){
			return '不能为全字母'; //全字母
		}else if(/^\d+$/g.test(pwd)){
			return '不能为全数字'; //全数字
		}else{
			return 1; //正确
		}
	},
	//渲染模板 
	render: function(id, data, type) {
		$("#"+id).setTemplateElement(id+"-template");
		$("#"+id).processTemplate(data);
		
		if (typeof container == 'undefined') {
			$("#"+id+" li").insertBefore("#"+id);
		}
	},
	
	//判断是否为微信浏览器
	isWeixinBrowser : function() {
		var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
        	return true;
        } else {
        	return false;
        }
	}
}

var win = {
	confirm : function(url, msg) {
		var msg = msg ? msg : "你确认删除该记录吗？";
		layer.confirm(msg,function(index){
			window.location = url;
		});
	},

	dialog : function(message) {
		if(message != '') {
			layer.open({
			    content: message
			    ,skin: 'msg'
			    ,time: 1 //2秒后自动关闭
			});
		}
	},
	dialogSuccess : function() {
		win.dialog("操作成功", 1);
	},
	dialogFail : function() {
		win.dialog("操作失败", 0);
	}
}

ajax = {
	get : function(url,param,successCallback,errorCallback){
		if(typeof param == 'function'){
			errorCallback = successCallback;
			successCallback = param;
			param= {};
		}
		
		$.ajax({
			url : url+"?v="+util.nonceStr(),
			contentType : "application/json; charset=utf-8",
			type : "GET",
			timeout : 120000,//超时时间设定
			data : param,
			dataType : "json",
			success : function(data) {
				console.log(data);
				var status = data.status;
				var status = data.status;
				if(status === 500 && typeof errorCallback !== 'function'){opt.alert(data.message);}
				else if(status === 208){
					console.log('用户尚未登录，请重新登录后在操作.');
					//alert('用户尚未登录，请重新登录后在操作.');
					//如果是登录页面
					window.location.href = config.basePath+'/acl/login';
				}
				
				if(status === 200){
					if(typeof successCallback == 'function'){
						successCallback(data);
					}
				}

				if(typeof errorCallback == 'function' && status!=200){
					errorCallback(data);
				}

			},
			error : function(data) {
				console.log(data);
				console.log('服务器繁忙,请稍后!');
				if(typeof errorCallback == 'function'){
					errorCallback(data);
				}
			}
		});
	},

	post : function(url,param,successCallback,errorCallback){
		if(typeof param == 'function'){
			errorCallback = successCallback;
			successCallback = param;
			param= {};
		}
		
		$.ajax({
			url : url+"?v="+util.nonceStr(),
			contentType : "application/json; charset=utf-8",
			type : "POST",
			timeout : 20000,//超时时间设定
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				console.log(data);
				var status = data.status;
				if(status === 500){
                    opt.alert(data.message);
				} else if(status === 208){
					console.log('用户尚未登录，请重新登录后在操作.');
					window.location.href = config.basePath+'/acl/login';
				}
				
				if(status === 200){
					if(typeof successCallback == 'function'){
						successCallback(data);
					}
				}

				if(typeof errorCallback == 'function' && status!=200){
				
					errorCallback(data);
				}
			},
			error : function(data) {
				console.log(data);
				console.log('服务器繁忙,请稍后!');
				if(typeof errorCallback == 'function'){
					errorCallback(data);
				}
			}
		});
	}
};


