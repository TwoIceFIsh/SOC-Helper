<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일 업로드 폼</title>
</head>


<h3>파일 업로드 폼</h3>

이글루 엑셀만 업로드해주세요
<body>
	<form method="POST" action="FileHandleServlet"
		enctype="multipart/form-data">
		File: <input type="file" name="file" id="file" /> <br /> <input
			type="submit" value="Upload" name="upload" id="upload" />
	</form>
</body>
</html>



<!-- 
<script type="text/javascript">
    //이미지 미리보기
    var sel_file;
 
    $(document).ready(function() {
        $("#file1").on("change", handleImgFileSelect);
    });
 
    function handleImgFileSelect(e) {
        var files = e.target.files;
        var filesArr = Array.prototype.slice.call(files);
 
        var reg = /(.*?)\/(jpg|jpeg|csv|xlsx)$/;
 
        filesArr.forEach(function(f) {
            if (!f.type.match(reg)) {
                alert("확장자는 xlsx 확장자만 가능합니다.");
                return;
            }
 
            sel_file = f;
 
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#img").attr("src", e.target.result);
            }
            reader.readAsDataURL(f);
        });
    }
</script>

<script>
//파일 업로드
function fn_submit(){
        
        var form = new FormData();
        form.append( "file1", $("#file1")[0].files[0] );
        
         jQuery.ajax({
             url : "/uploadServlet"
           , type : "POST"
           , processData : false
           , contentType : false
           , data : form
           , success:function(response) {
               alert("성공하였습니다.");
               console.log(response);
           }
           ,error: function (jqXHR) 
           { 
               alert(jqXHR.responseText); 
           }
       });
}
</script>

 
<div>
    <label for="file1">파일</label> 
    <input type="file" id="file1" name="file1"> 
    <button id="btn_submit" onclick="javascript:fn_submit()">전송</button>    
</div>
 
<div>
       <div class="img_wrap">
           <img id="img" />
       </div>
</div>
  -->