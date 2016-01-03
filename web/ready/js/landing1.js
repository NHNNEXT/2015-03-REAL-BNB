//@TODO 일단 전역으로 뺐다..

// 반응형 메뉴 등장하는 함수 (모바일 크기일때 등장)
$(".button-collapse").sideNav();


// login창에서 닫기 버튼을 눌렀을 때, login창 닫기
$('#close-login').click(function(){
    $('#modal-login').closeModal();
});

// signup창에서 닫기 버튼을 눌렀을 때, signup창 닫기
$('#close-signup').click(function(){
    $('#modal-signup').closeModal();
});



function readURL(input) {
  console.log(input.files);
  console.log(input.files[0]);

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
          console.log(e.target);
            $('#upload-preview').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#signup-file").change(function () {
    readURL(this);
});


// token이 있으면(로그인 된 상태라면) mainPage로 간다
if(localStorage.getItem("token")){
    window.location.assign("/"); // mainPage로 감 
}


var Start = {
    init: function() {
	    $('.modal-trigger').leanModal(); // modal을 활성화하는 오브젝트
    },
    resetLogin: $('#login-form').html(), // login form 부분 초기화를 위한 오브젝트 
    resetSignup: $('#signup-form').html() // signup form 부분 초기화를 위한 오브젝트 
};

$('.login-btn').click(function(e){
    // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
    e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
    ajaxPostLogin();
});

var ajaxPostLogin =  function() {
    var elEmail = $('#login-email');
    var elPassword = $('#login-password');
    // var url = "http://dev.balbum.net/";  
    var url = "http://10.73.42.216:8080/";
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + elEmail.val();
    postString += "&password=" + elPassword.val();

    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url + "api/user/login/web",
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
                $('#login-message').text('존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.');
            }
            $('#login-form').html(Start.resetLogin); // form창 초기화
       },
       error: function(res){
            console.log("[ajaxPostLogin] ajax 실패라능");
       }
    });
 };

 $('.signup-btn').click(function(e){
     // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
     e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
     ajaxPostSignup();
 });

 var ajaxPostSignup =  function() {
    // form 형식에 enctype="multipart/form-data" 을 추가했다면 ajax의 data를 보낼때 FormData 객체 형태로 보내야함. 또한 processData, contentType 코드도 추가해야한다.
     var formData = new FormData();
     var elEmail = $('#signup-email');
     var elPassword = $('#signup-password');
     var elConfirmPassword = $('#signup-confirm-password');
     var elRole = $('#signup-role');
     // var url = "http://dev.balbum.net/";  
     var url = "http://10.73.42.216:8080/";
    
     formData.append("image",$("input[name=uploadfile]")[0].files[0]);
     formData.append("email",elEmail.val());
     formData.append("password",elPassword.val());
     formData.append("role",elRole.val());

     emailValidation(); // 실시간 반영됨
     // passwordValidation(); //실시간 반영안됨 

     $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url + "api/user/create",
        data: formData, // 
        processData: false,
        contentType: false,
        success: function(response) {  //성공시 이 함수를 호출한다.
                debugger;
            if(response.token != null){
                localStorage.setItem("token", response.token); // token을 localStorage에 저장
                window.location.assign("/pages/main-modal.htm"); 
            }
            else{
                // alert(response.message);
                // 존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.
            }
            $('#signup-form').html(Start.resetSignup); // form창 초기화
       },
       error: function(res){
            console.log("[ajaxPostSignup] ajax 실패라능");
       }
    });
  
     
  };

// 회원가입 모달에서, 이메일 주소가 이미 가입되어있는 이메일인지 확인하는 함수 
var emailValidation = function(){
    var elEmail = $('#signup-email');
    var url = "http://dev.balbum.net/";  
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + elEmail.val();
    $.ajax({                  
        type: "GET",
        url: url + "/api/user/isNewEmail",
        data: postString,
        success: function(res) {  //성공시 이 함수를 호출한다.
            console.log(res.state, res.error);
            $('#signup-message').text(res.error);
            
       },
       error: function(res){
            console.log("[emailValidation] ajax 실패라능");
       }
    });

}

// 회원가입 모달에서, 비번/비번 확인이 같은지 체크하는 함수 
var passwordValidation = function(){
    var password = $('#signup-password').val();
    var confirmPassword = $('#signup-confirm-password').val();

    if(password != confirmPassword){
        $('#signup-message').text('비밀번호를 다시 확인해주세요.');
    }
}

$(function(){
    Start.init();
});

// @TODO : 현재 모두다 전역객체로 되어있음 -> 나중에 리팩토링 필요! 