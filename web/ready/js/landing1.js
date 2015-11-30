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
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + elEmail.val();
    postString += "&password=" + elPassword.val();

    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url + "api/user/login",
        data: postString,
        success: function() {  //성공시 이 함수를 호출한다.
            $('#login_form').html(Start.resetLogin); // form창 초기화
       }
    });
 };

 $('.signup_btn').click(function(e){
     // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
     e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
     ajaxPostSignup();
 });

 var ajaxPostSignup =  function() {
     var elEmail = $('#signup-email');
     var elPassword = $('#signup-password');
     var elConfirmPassword = $('#signup-confirm-password');
     var elNickname = $('#signup-nickname');

     var url = "http://dev.balbum.net/";  
     var postString = "";       // post방식으로 처리하기 위한 파라미터들
     
     postString  = "email=" + elEmail.val();
     postString += "&password=" + elPassword.val();
     postString += "&confirmPassword=" + elConfirmPassword.val();
     postString += "&nickname=" + elNickname.val();

     $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
         type: "POST",
         url: url + "api/user/create",
         data: postString,
         success: function() {  //성공시 이 함수를 호출한다.
             $('#signup_form').html(Start.resetSignup); // form창 초기화
        }
     });
  };

$(function(){
    Start.init();
});

// @TODO : 현재 모두다 전역객체로 되어있음 -> 나중에 리팩토링 필요! 