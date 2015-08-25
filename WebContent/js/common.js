$(function(){

	//当滚动条的位置处于距顶部150像素以下时，跳转链接出现，否则消失
	$(window).scroll(function(){
        if ($(window).scrollTop()>150){
        	$("#js-scrolltop").css("left",document.body.clientWidth>1366?(document.body.clientWidth-(document.body.clientWidth-1200)/2):1366-(1366-1200)/2);
            $("#js-scrolltop").fadeIn(700);
        }
        else
        {
            $("#js-scrolltop").fadeOut(700);
        }
    });

    //当点击跳转链接后，回到页面顶部位置
    $("#js-scrolltop").click(function(){
        $('body,html').animate({scrollTop:0},500);
        return false;
    });
    
    //设置签到区日期
	$("#js-week").html(function(){
		var myDate = new Date();
		return myDate.toString(myDate);
	});
	
	//查询是否登录
	$.ajax({
		url:"checkLogin.json",
		error:function(){alert("请重新登录！");},
		success:function(data){
			var jsonObj = eval("("+data+")");
			$("#js-checkLogin ul").empty();
			if(jsonObj.success){
				$("#js-checkLogin ul").append("<li><a class='editPreview' id='js-editPreview' href='javascript:void(0);' onclick='editPreview()'>"+jsonObj.name+"</a></li>");
			}
			else{
				$("#js-checkLogin ul").append("<li><a href='login.html'>登录</a></li><li> | </li><li><a href='register.html'>注册</a></li>");
			}
			
			if(jsonObj.isAdmin){
				$("#js-manage").show();
			}
		}
	});
	
});

Date.prototype.toString = function(showWeek)  
{   
    var myDate= this;   
    if (showWeek)  
    {   
        var Week = ['日','一','二','三','四','五','六'];  
        return '星期' + Week[myDate.getDay()];  
    }
}

//修改个人信息
function editPreview(){
	location.href="/forum/editPreview.html";
}

//签到
function signIn(){
	$.ajax({
		url:"signIn.json",
		error:function(){alert("签到失败！")},
		success:function(data){
			var jsonObj = eval("("+data+")");
			if(jsonObj.success){
				alert(jsonObj.result);
			}
			else{
				alert(jsonObj.result);
			}
		}
	});
}