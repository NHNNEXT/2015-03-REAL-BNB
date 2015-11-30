var Start = {
    init: function() {
	    $('.modal-trigger').leanModal(); // modal을 활성화하는 오브젝트
    },
    reset: $('#login_form').html() // form 부분 초기화를 위한 오브젝트 
};

$('.login_btn').click(function(e){
    // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
    e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
    ajaxPostSend();
});

var ajaxPostSend =  function() {
    var elEmail = $('#email');
    var elPassword = $('#password');
    var url = "http://dev.balbum.net/";   //Controller 호출
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + elEmail.val();
    postString += "&password=" + elPassword.val();

    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url + "api/user/login",
        data: postString,
        success: function() {  //성공시 이 함수를 호출한다.
            $('#login_form').html(Start.reset); // form창 초기화
       }
    });
 };

$(function(){
    Start.init();
});

// @TODO : 현재 모두다 전역객체로 되어있음 -> 나중에 리팩토링 필요! 