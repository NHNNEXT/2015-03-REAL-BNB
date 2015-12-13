//@TODO 일단 전역으로 뺐다..

// token이 있으면(로그인 된 상태라면) mainPage로 간다
if(localStorage.getItem("token")){
    window.location.assign("/"); // mainPage로 감 
}


var Start = {
    init: function() {
	    $('.modal-trigger').leanModal(); // modal을 활성화하는 오브젝트
    },
    resetLogin: $('#login_form').html(), // login form 부분 초기화를 위한 오브젝트 
    resetSignup: $('#signup_form').html() // signup form 부분 초기화를 위한 오브젝트 
};

$('.login_btn').click(function(e){
    // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
    e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
    ajaxPostLogin();
});

var ajaxPostLogin =  function() {
    var elEmail = $('#login-email');
    var elPassword = $('#login-password');
    var url = "http://dev.balbum.net/";  
    // var url = "http://10.73.38.106:8080/";
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + elEmail.val();
    postString += "&password=" + elPassword.val();

    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url + "api/user/login",
        data: postString,
        success: function(res) {  //성공시 이 함수를 호출한다.
            if(res.token != null){
                console.log("성공데스네");

                localStorage.setItem("token", res.token); // token을 localStorage에 저장

                window.location.assign("/"); // mainPage로 감 
                // document.location.replace('localhost:8000');
                // location.href = "localhost:8000";
            }
            else{
                // 존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.
            }
            $('#login_form').html(Start.resetLogin); // form창 초기화
       },
       error: function(res){
            debugger;
            console.log("실패라능");
       }
    });
 };

 $('.signup_btn').click(function(e){
     // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
     e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
     ajaxPostSignup();
 });

 var ajaxPostSignup =  function() {
    var formData = new FormData();
    // formData.append("uploadfile",$("input[name=uploadfile]")[0].files[0]);
     var elEmail = $('#signup-email');
     var elPassword = $('#signup-password');
     var elConfirmPassword = $('#signup-confirm-password');
     var elRole = $('#signup-role');

     var url = "http://dev.balbum.net/";  
     var postString = "";       // post방식으로 처리하기 위한 파라미터들
     
     postString  = "email=" + elEmail.val();
     postString += "&password=" + elPassword.val();
     // postString += "&image=" + formData;
     postString += "&role=" + elRole.val();

     $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
         type: "POST",
         url: url + "api/user/create",
         data: formData,
         processData: false,
         contentType: false,
         success: function(res) {  //성공시 이 함수를 호출한다.
             if(res.token != null){
                 console.log("성공데스네");

                 localStorage.setItem("token", res.token); // token을 localStorage에 저장

                 window.location.assign("/"); // mainPage로 감 // @TODO 나중에 아이추가 모달로 가는걸로 바꿔야함
             }
             else{
                // 존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.
             }
             $('#signup_form').html(Start.resetSignup); // form창 초기화
        },
        error: function(res){
             debugger;
             console.log("실패라능");
        }
     });
  };



$(function(){
    Start.init();
});

// @TODO : 현재 모두다 전역객체로 되어있음 -> 나중에 리팩토링 필요! 