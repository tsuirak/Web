var config = {
	basePath: $("#ctx").text()
}

var opt = {
	alert : function(msg){
		layer.alert(msg);
	},
	
	load : function () {
        layer.load(1, {
            shade: [0.5,'#fff'] //0.1透明度的白色背景
        });
    },

	confirm : function(url, msg) {
		var msg = msg ? msg : "你确认删除该记录吗？";
		layer.confirm(msg,function(index){
            opt.load();
			window.location = url;
		});
	},
    updateConfirm : function(url, msg) {
        var msg = msg ? msg : "你确认更新排序吗？";
        layer.confirm(msg,function(index){
            window.location = url;
        });
    },
	
	dialog : function(message, messageType) {
		if(message != '' && message != null) {
			if(messageType == '1') {
				layer.msg(message, {icon: 1});
			} else {
				layer.alert(message, {icon: 2});
			}
		}
	},
	
	openWin : function(url,title, width,height) {
		var title = title ? title : false;
		layer.open({
	        type: 2,
	        title: title,
            zIndex:10000,
            anim: -1,
	        maxmin: true,
            aini:2,
	        shadeClose: false, //点击遮罩关闭层
	        area: [width+"px", height+"px"],
	        content: url
	    });
	},
	openWindow:function(url,title,width,heigth,call){
		var title = title ?title:false;
		layer.open({
	        type: 2,
	        title: title,
	        maxmin: true,
	        shadeClose: true, //点击遮罩关闭层
	        area: [width+"px", heigth+"px"],
	        content: url,
	        btn:['确认','取消'],
	        yes:function(index,layero){
	        	call(index,layero);
	        },
	        no:function(index){
	        	layer.close(index);
	        }
	    });
	},
	openWork:function(option){
		var url = option.url;
		var title = option.title
		var area = option.area;
		var btn = option.btn;
		var fn = option.fn;
		layer.open({
	        type: 2,
	        title: title,
	        maxmin: true,
	        shadeClose: true, //点击遮罩关闭层
	        area: area,
	        content: url,
	        btn:btn,
	        closeBtn:0,
	        yes:function(index,layero){
	        	fn(index,layero);
	        },
	        no:function(index){
	        	layer.close(index);
	        }
	    });
	},
	closeWin : function(refresh,call) {
		var index = parent.layer.getFrameIndex(window.name);
		if(refresh) {
			parent.location.reload();
		}
        if(call) {
            parent.init();
        }
		parent.layer.close(index); //执行关闭
	}
}

var ajax = {
	get : function(url,param,successCallback,errorCallback){
		if(typeof param == 'function'){
			errorCallback = successCallback;
			successCallback = param;
			param= {};
		};
		
		$.ajax({
			url : url,
			contentType : "application/json; charset=utf-8",
			type : "GET",
			timeout : 120000,//超时时间设定
			data : param,
			dataType : "json",
			success : function(data) {
				console.log(data);
				var status = data.status;
				var status = data.status;
				if(status === 500){alert(data.message);}
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
			url : url,
			contentType : "application/json; charset=utf-8",
			type : "POST",
			timeout : 20000,//超时时间设定
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				console.log(data);
				var status = data.status;
				if(status === 500){
					console.log(data.message);
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




