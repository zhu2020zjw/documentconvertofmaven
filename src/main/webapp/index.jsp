<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DCS在线预览</title>
<style type="text/css">
	a {font-size:16px}   
	a:link {color: blue; text-decoration:none;} //未访问：蓝色、无下划线   
	a:active:{color: red; } //激活：红色   
	a:visited {color:purple;text-decoration:none;} //已访问：purple、无下划线   
	a:hover {color: red; text-decoration:underline;} //鼠标移近：红色、下划线 
</style>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$(".first").click(function() {
			$(".second_body").hide();
			$(".first_body").show();
			sessionStorage.setItem("mark",0);
		});
		
		$(".second").click(function() {
			$(".first_body").hide();
			$(".second_body").show();
			sessionStorage.setItem("mark",1);
		});
		
		var getMark = sessionStorage.getItem("mark");
		if(getMark == 1){
			$(".second_body").css("display","block");
			$(".first_body").css("display","none");
			seesionStorage.removeItem("mark");
		}else{
			$(".second_body").css("display","none");
			$(".first_body").css("display","block");
			seesionStorage.removeItem("mark");
		} 
	})
	
</script>
<style type="text/css">
	.file{  filter:alpha(opacity:0);opacity: 0;width:0px }
</style>
</head>
<body>
	
	<header>
		<h2>在线文档预览</h2>
	</header>
	<br>
	<div class="type_button">
		<a class="first" href="#">文件预览</a>
		&nbsp;&nbsp;
		<a class="second" href="#">文档转换</a>
	</div>
	<br>
	<div class="first_body">
		<fieldset>
			<legend>
				<span>输入文件</span>
			</legend>
			<form action="upLoadServlet" method="post" enctype="multipart/form-data">
				<input type='text' id='textfield' class='txt' size="50" value="${sessionScope.path }" />
				<input type='button' class='btn' value='浏览...' onclick="document.getElementById('fileField').click()" />
				<input type="file" name="file" id="fileField" class="file" size="28" onchange="document.getElementById('textfield').value=this.value" />
				<input type="submit" value="上传本地文件"/> 
			</form>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>输出文件</span>
			</legend>
			<form action="convertServlet?method=previewOfSD" method="post">
				<input type="hidden" name="uploadPath" value="${sessionScope.path }"/>
				<input type="submit" value="标准预览"/>
			</form>
			&nbsp;&nbsp;
			<a href="${requestScope.targetFileName }">${requestScope.targetFileName }</a>
			<br><br>
			<form action="convertServlet?method=previewOfHD" method="post">
				<input type="hidden" name="uploadPath" value="${sessionScope.path }"/>
				<input type="submit" value="高清预览"/>
			</form>
			&nbsp;&nbsp;
			<a href="${requestScope.targetFileNameOfHD }">${requestScope.targetFileNameOfHD }</a>
		</fieldset>
	</div>
	
	<div class="second_body" style="display: none;">
		<fieldset>
			<legend>
				<span>添加结果</span>
			</legend>
			<form action="upLoadServlet" method="post" enctype="multipart/form-data">
				<input type='text' id='textfield2' class='txt' size="50" value="${sessionScope.path }" />
				<input type='button' class='btn' value='浏览...' onclick="document.getElementById('fileField2').click()" />
				<input type="file" name="file" id="fileField2" class="file" size="28" onchange="document.getElementById('textfield2').value=this.value" />
				<input type="submit" value="上传本地文件"/>
			</form>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>选择类型</span>
			</legend>
			<div>
				<form action="convertServlet?method=convert" method="post">
					<input type="hidden" name="uploadPath" value="${sessionScope.path }"/>
					<select name="suffix">
						<option selected="selected" value="-1">请选择支持转换的格式</option>
						<option value="html">html</option>
						<option value="pdf">pdf</option>
						<option value="doc">doc</option>
						<option value="docx">docx</option>
					</select>
					&nbsp;&nbsp;
					<input type="submit" value="确定"/>
				</form>
			</div>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>转换结果</span>
			</legend>
			<form action="downLoadServlet" method="post">
				<input type="text" name="link" value="${requestScope.convertIP }" size="50"/>
				<input type="hidden" name="uploadPath" value="${sessionScope.name }"/>
				<input type="submit" value="下载"/>	
			</form>
			
			<form action="convertServlet?method=previewOfSD" method="post">
				<input type="hidden" name="uploadPath" value="${sessionScope.name }"/>
				<input type="submit" value="预览"/>
			</form>
			<a href="${requestScope.targetFileName }">${requestScope.targetFileName }</a>
		</fieldset>
	</div>
	
</body>
</html>