<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>File Upload Test</title>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script> 
   <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script> -->
    <!-- <script src="/resources/lib/jquery.form.min.js"></script> -->
<!--     <script src="/resources/lib/expansion.js"></script>
    <script src="/resources/js/excel-download.js"></script> -->
</head>
<body>
    <div class="container">
        <h3>File Upload Test</h3>
        <form id="frm-upload" enctype="multipart/form-data" action="${pageContext.request.contextPath}/file/upload" method="post">
            <div class="form-group">
                <label for="exampleInputFile">File input</label>
                <input type="file" id="test-file" name="file">
            </div>
            <div class="form-group">
                <input type="file" multiple="multiple" name="file">
            </div>
            <div class="form-group">
                <input type="text" name="userName" placeholder="Enter user name">
            </div>
            
            <!-- Form 태그 자체로 전송하기 -->
            <button type="button" class="btn btn-primary btn-form-submit">Form Submit</button>
            
            <!-- jquery ajax으로 전송하기 -->
            <button type="button" class="btn btn-primary btn-grnl-submit">Form Data Submit</button>
            <button type="button" class="btn btn-primary btn-ajax-submit">Ajax Submit</button>
            <button type="button" class="btn btn-primary btn-ajaxform-submit">Ajax Form Submit</button>
    
        </form>
    </div>    
</body>

<script type="text/javascript">
    $(document).ready(function() {

        /**
        * 1.Form 태그 자체로 전송하기
        */
        $('.btn-form-submit').on('click', function() {
            $('#frm-upload').submit();
        });
        
        /**
        * jQeury ajax form 전송
        */
        $('.btn-grnl-submit').on('click', function() {

            var form = $('#frm-upload');
            //formdata 생성
            var formData = new FormData();


            formData.append("file", $('input[name=file]')[0].files[0]);

            var len = $('input[name="file"]')[1].files.length;
            for (var i = 0; i < len; i++) {
                formData.append("file" + i, $('input[name=file]')[1].files[i]);
            }
            $.ajax({
                url : '${pageContext.request.contextPath}/file/upload',
                type : 'POST',
                data : formData,
                processData : false,
                contentType : false,
                beforeSend : function() {
                    console.log('jQeury ajax form submit beforeSend');
                },
                success : function(data) {
                    console.log('jQeury ajax form submit success');
                }, 
                error : function(data) {
                    console.log('jQeury ajax form submit error');
                },
                complete : function() {
                    console.log('jQeury ajax form submit complete');
                }
            });//end ajax
        });
        
        /**
        * Ajax submit
        */
        $('.btn-ajax-submit').on('click', function() {
            $.ajax({
                url : '${pageContext.request.contextPath}/file/upload',
                type : 'POST',
                dataType : 'json',
                data : {
                    file : $('#test-file').val()
                },
                beforeSend : function() {
                    console.log('Ajax submit beforeSend');
                },
                success : function(data) {
                    console.log('Ajax submit success');
                }, 
                error : function(data) {
                    console.log('Ajax submit error');
                },
                complete : function() {
                    console.log('Ajax submit complete');
                }
            });//end ajax
        });
        
        /**
        * Ajax Form submit
        */
        $('.btn-ajaxform-submit').on('click', function() {
            $('#frm-upload').ajaxForm({
                beforeSubmit : function(data, form, option) {
                    console.log("data ::::: ", data);
                    console.log("form ::::: " , form);
                    console.log("option ::::: ", option);
                    //check validation
/*                     var username = $('input[name=userName]');
                    checkName(username); */
                },
                success : function(data) {
                    //성공후 서버에서 받은 데이터 처리
                    console.log('Ajax Form submit success');
                }, 
                error : function(data) {
                    //에러발생을 위한 code페이지
                    console.log('Ajax Form submit error');
                }
            }).submit();
        });
        
        function checkName(username) {
            if (username.val() == null || username.val() == '') {
                alert('이름을 입력하세요');
                username.focus();
                return false;
            }
            return true;
        }
        
    });//end document ready
</script>
</html>