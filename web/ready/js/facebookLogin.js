
window.fbAsyncInit = function() {
  FB.init({
    // appId      : '389047587948213', // 경륜
    appId      : '333726796797560', // 혜연언니
    cookie     : true,  // enable cookies to allow the server to access the session
    status     : true,
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.5' // use version 2.5
  });
    
    
  // FB.Event.subscribe('auth.authResponseChange', function(response) {
  //   if (response.status === 'connected') {
  //     console.log("페이스북 연결에 성공했습니다.");
  //     //SUCCESS
  //   }  else if (response.status === 'not_authorized') {
  //     console.log("페이스북 연결에 실패했습니다.");
  //     //FAILED
  //   } else {
  //     console.log("페이스북 로그아웃했습니다.");  
  //     //UNKNOWN ERROR
  //   }
  // }); 
  
};
    
/* 페북으로 로그인하기 버튼 눌렀을 때 */
function fbLogin(){
  FB.login(function(response) {
    if (response.authResponse) {

          FB.api('/me',{ locale: 'en_US', fields: 'name, email' }, function(response) { 

            fbEmailValidation(response.email, function(state){
              if(state == true){
                // 페북로그인 불가능 -> "아직 벨범에 가입되지 않은 페북아이디 입니다. 먼저 벨범에 가입해주세요!"
                $('#login-message').text('먼저 벨범에 가입해주세요!');

              } else {
                // 페북로그인 성공
                var postString = response.email;

                $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
                    type: "POST",
                    url: URL_CONFIG + "api/user/signup/fb_token/web",
                    data: postString,
                    success: function(res) {  //성공시 이 함수를 호출한다.
                        if(res.token != null){
                            console.log("성공데스네");

                            localStorage.setItem("token", res.token); // token을 localStorage에 저장
                            window.location.assign("/"); // mainPage로 감 
                        }
                        else{
                            $('#login-message').text('존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.');
                        }
                        $('#login-form').html(Start.resetLogin); // form창 초기화
                   },
                   error: function(res){
                        console.log("[ajaxPostFbLogin] ajax 실패라능");
                   }
                });
              }
            });

          });
    } else {
      console.log('User cancelled login or did not fully authorize.');
    }
  },{scope: 'email,user_photos,user_videos'});
}


/* 페북으로 가입하기 버튼 눌렀을 때 */
function fbSignup(){
  FB.login(function(response) {
    if (response.authResponse) {

          FB.api('/me',{ locale: 'en_US', fields: 'name, email' }, function(response) { 

            // 받아오는 것들 
            // response.name, response.id(=token), response.email

            var str ="";
            str +="<div class='row'><div class='input-field s12'><img id='upload-preview' class='circle responsive-img s12' alt='your image'/></div></div>";
            str +="<div class='row'><div class='input-field s12'><input disabled value="+response.email+" id='signup-email' type='text' class='validate' onblur='emailValidation()'><label for='disabled'></label></div></div>";
            str +="<input id='signup-token' type='hidden' class='validate' value="+response.id+">";
            str +="<div class='row'><div class='input-field s12'><input id='signup-role' type='text' class='validate'><label for='role'>Role</label></div></div>";
            str +="<div class='row'><div id='signup-message'></div></div>";
            str +="<button name='submit' type='submit' value='submit' class='fb-signup-btn modal-action modal-close waves-effect waves-light btn-large'>계정 만들기</button>";

            fbEmailValidation(response.email, function(state){
              if(state == false){
                // 페북가입 불가능 
                $('#signup-message').text('이미 가입된 페북계정입니다.');
              } else {
                // 페북가입 가능 
                getPhoto(); // 페북 프로필 사진 가져오기 
                $('#signup-form').html(str); // form창 변경 

                $('.fb-signup-btn').click(function(e){
                    // e.stopPropagation(); // 상위 엘리먼트에 걸려있는 이벤트의 전파를 막는 함수 
                    e.preventDefault(); // 브라우저에 기본으로 걸려있는 이벤트를 막는 함수
                    ajaxPostFbSignup();
                });
              }
            });
          });
    } else {
      console.log('User cancelled login or did not fully authorize.');
    }
  },{scope: 'email,user_photos,user_videos'});
}


var ajaxPostFbSignup =  function() {
    var formData = new FormData();
    var elImage = $('#upload-preview');
    var elEmail = $('#signup-email');
    var elToken = $('#signup-token');
    var elRole = $('#signup-role');

    formData.append("image",elImage.attr('src'));
    formData.append("email",elEmail.val());
    formData.append("fb_token",elToken.val());
    formData.append("role",elRole.val());

    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
       type: "POST",
       url: URL_CONFIG + "api/user/signup/fb_token/web",
       data: formData, 
       processData: false,
       contentType: false,
       success: function(response) {  //성공시 이 함수를 호출한다.
           if(response.token != null){
               localStorage.setItem("token", response.token); // token을 localStorage에 저장
               window.location.assign("/"); 
           }
           else{
               $('#signup-message').text(response.message);
               // alert(response.message);
               // 존재하지 않는 아이디이거나 비밀번호가 올바르지 않습니다.
           }
           $('#signup-form').html(Start.resetSignup); // form창 초기화
      },
      error: function(res){
           console.log("[ajaxPostFbSignup] ajax 실패라능");
      }
   });
 };


// 페북로그인/페북회원가입 버튼을 눌렀을 때, 페북이메일 주소가 이미 가입되어있는 이메일인지 확인하는 함수 
var fbEmailValidation = function(email, callback){
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    
    postString  = "email=" + email;
    $.ajax({                  
        type: "GET",
        url: URL_CONFIG + "/api/user/isNewEmail",
        data: postString,
        success: function(res) {  //성공시 이 함수를 호출한다.
            callback(res.state);
       },
       error: function(res){
            console.log("[fbEmailValidation] ajax 실패라능");
       }
    });

}

// 페북 프로필 사진을 가져오는 함수 
function getPhoto() {
  FB.api('/me/picture?type=normal', function(response) {
    $("#upload-preview").attr("src", response.data.url);
  });
}


// Load the SDK asynchronously
(function(d){
  var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement('script'); js.id = id; js.async = true;
  js.src = "//connect.facebook.net/en_US/all.js";
  ref.parentNode.insertBefore(js, ref);
}(document));




