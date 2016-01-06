var address = "http://dev.balbum.net/";
// var address = "http://192.168.1.146:8080/";
var token = localStorage.getItem('token');
// var token = 'asdf1234';
var today;
var testData;

var Main = {
    init: function(bMain, $scope, $http) {
        bMain.token = token;
        $("input[name='token']").val(token);
        Main.getMyInfo($http, bMain);

        $('body').on('click', '#logout-btn', function() {
           if(localStorage.getItem("token")){
               console.log("로그인 되어있으니 로그아웃하겠습니다.");
               localStorage.clear(); // token 지우기
               window.location.assign("/landing1.html");
           }
           else{
               console.log("이미 로그아웃 되었습니다.");
           }
        });
    },
    getMyInfo: function($http, bMain) {
        $http({
            url: address + 'api/family',
            method: "GET",
            params: {token: token}
        }).then( function(response) {
            console.log("사용자 정보:", response);
            bMain.userInfo = response.data;
        }, function() {
            alert('사용자 정보를 불러오지 못하였습니다.');
        });
    },
}

var Start = {
    init: function($scope) {
        $("input[name='token']").val(token);
        Start.getToday(); // postcard에 오늘 날짜 박아넣기
        $('.button-collapse').sideNav();

        // $('.modal-trigger').leanModal();
        // $('.datepicker').pickadate({ //materialize datepicker 동적 활성화
        //      selectMonths: true, // Creates a dropdown to control month
        //      selectYears: 15 // Creates a dropdown of 15 years to control year
        //  });
        $('.scrollspy').scrollSpy();


        $('html').click(function() {
            $('.action-dropdown-menu').removeClass("active");
        });

        $('.action-dropdown-menu').click (function(e){ // 필요한지 의문
            e.stopPropagation();
        });
        $('.nav-update-modal').click(function(){
            $('#update-modal').openModal();
        });
        $('ul.tabs').tabs(); //materialize tabs 동적 활성화
        $('.postcard-tabs .tabs li:nth-child(2) a').removeClass("active"); //tabs에서 active 떼기
        $('div.indicator').remove(); //materialize tabs 하단 강조선 떼기
        // $('.timemachine-wrapper .row').pushpin({ top: $('.timemachine-wrapper').offset().top }); //materialize pushpins 동적 활성화
        // $scope.$apply();
    },
    getToday: function() {
        var now = new Date();
        var day = ("0" + now.getDate()).slice(-2);
        var month = ("0" + (now.getMonth() + 1)).slice(-2);
        today = now.getFullYear()+"-"+(month)+"-"+(day);
        $('.date-container input').val(today);
    }

}

var Upload = {
    init: function() {
        this.uploadPhoto();
    },

    /* 사진 프리뷰 */
    readURL: function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.uploaded-photo').attr('src', e.target.result);
                $('.uploaded-photo').css('display', 'block').css('max-width', '100%');
            }
            reader.readAsDataURL(input.files[0]);
        }
    },
    uploadPhoto: function() {
        // 업로드버튼이나 올려진 사진을 누르면 hidden된 form을 누른다.
        $('.upload-photo-box, .uploaded-photo').click(function(event) {
            $(".upload-photo-input").click();
        });

        // form이 변경되면 fileread를 한다.
        $(".upload-photo-input").change(function(){
            Upload.readURL(this);
            $('.upload-photo-box').css('display', 'none');
        });
    },
    resetPhotoBox: function() {
        $('.upload-photo-box').css('display', 'block');
        $('.uploaded-photo').css('display', 'none');
    }
}

var InitModal = {
    init: function($scope, bMain) {
        this.steps();
        this.uploadPhoto();

        $('.modal-add-baby .action-container .btn-start').on('click', '', function() {
            $('.add-baby-btn button').click();
        });
        $('.modal-add-baby .action-container .btn-done').on('click', '', function() {
            localStorage.setItem('initCompleted', token);
        });

    },
    steps: function() {
        $('.modal-main-btn').click(function(){
            $('#main-modal').leanModal({
                  dismissible: false, // Modal can be dismissed by clicking outside of the modal
                  ready: function() { alert('Ready'); }, // Callback for Modal open
                  complete: function() { alert('Closed'); } // Callback for Modal close
                }
            );
            $('#main-modal').openModal();
        });
        $('.js-btn-find-user').on('click', function() {
            $('.found-existing-user').removeClass('hide');
        });
        $('#modal-main').modalSteps({
            callbacks: {
                // '1': function(){ alert('Tanam!');}
            }
        });
    },
    readURL: function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.uploaded-baby-photo').attr('src', e.target.result);
                $('.uploaded-baby-photo').css('display', 'block').css('max-width', '100%');
            }
            reader.readAsDataURL(input.files[0]);
        }
    },
    uploadPhoto: function() {
        // 업로드버튼이나 올려진 사진을 누르면 hidden된 form을 누른다.
        $('.upload-baby-photo, .uploaded-baby-photo').click(function(event) {
            $(".upload-baby-photo-input").click();
        });

        // form이 변경되면 fileread를 한다.
        $(".upload-baby-photo-input").change(function(){
            InitModal.readURL(this);
            $('.upload-baby-photo').css('display', 'none');
        });
    },
    postBaby: function($scope, bMain) {
        $('#babyForm').submit(function() {
            console.log("포스트베이비");
            $('#babyForm').ajaxSubmit({
                //보내기전 validation check가 필요할경우
                beforeSubmit: function (data, $form, opt) {
                    $("input[name='token']").val(token);
                    $scope.$apply();
                    return true;
                },
                /* submit이후의 처리. 제일 위에 방금 올린 카드 추가. */
                success: function(responseText, statusText, xhr, $form){
                    bMain.babyList.push(responseText.res);
                    console.log("베이비리스트 가랏", bMain.babyList);
                    $('#babyForm').clearForm();
                    $("input[name='token']").val(token);
                    $scope.$apply();
                    InitModal.resetPhotoBox();
                },
                //ajax error
                error: function(){
                    alert("아이 올리는 데 문제가 생겼어요, 다시 올려주시겠어요?");
                }
            });
            return false;
        });
    },
    getFamily: function($scope, $http, bMain) {
        $http({
            url: address + 'api/user/family/findFromMail',
            method: "GET",
            params: {token: token, email:$('.modal-find-balbum input').val()}
        }).then( function(response) {
            console.log("res:", response);
            bMain.foundUser = response.data.res;
            console.log(bMain.foundUser);
            // $scope.$apply();
            // $scope.$apply("cCtrl.foundUser = response.data.res;");
        }, function() {
            alert('사용자 정보를 불러오지 못하였습니다.');
        });
    },
    resetPhotoBox: function() {
        $('.upload-baby-photo').css('display', 'block');
        $('.uploaded-baby-photo').css('display', 'none');
    }
}

var User = {
    babyIdx: 0,
    get: function($http) {
        $http({
            url: address + 'api/user',
            method: "GET",
            params: {token: token}
        }).then( function(res) {
            console.log("get res:", res);
        }, function() {
            alert('사용자 정보를 불러오지 못하였습니다.');
        });
    },
    getBaby: function($http, bMain) {
        $http({
            url: address + 'api/user/baby',
            method: "GET",
            params: {token: token}
        }).then( function(res) {
            bMain.babyList = res.data;
            console.log("baby res:", bMain.babyList);
            if(res.data.length == 0) { /* 아이 정보가 없으면 모달 띄우기 */
                $('div.modal-main-btn').click();
            }
        }, function() {
            alert('아이 정보를 불러오지 못하였습니다.');
        });
    },
    getBabyInfo: function($http, cCtrl, bId) {
        $http({
            url: address + 'api/user/baby',
            method: "GET",
            params: {token: token}
        }).then( function(res) {
            // bMain.babyList = res.data;
            $.each(res.data, function(k, v) {
                if(v.bid == bId) {
                    console.log(v.bid);
                    cCtrl.filteredBaby = v;
                }
            });

        }, function() {
            alert('아이 정보를 불러오지 못하였습니다.');
        });
    },
}

var CardCRUD = {
    init: function($http, cCtrl) {
        $('li.timeline').click(function() { /* 타임라인 메뉴 누르면 전체 아이 카드 나옴 */
            CardCRUD.get($http, this);
        });

        $('aside.left-col').on('click', '.bfilter', function() { /* 아이 메뉴 누르면 특정 아이 카드 나옴 */
            $('article.main-col').data('bfilter', $(this).data('bid'));
            CardCRUD.get($http, cCtrl, $(this).data('bid'));
        });
    },
    get: function($http, ctrl, bId) {
        var url = bId? 'api/filter/baby?token='+token+'&bId='+bId : 'api/card?token='+token;
        $http.get(address + url).then(function(res) {
            ctrl.cardList = res.data.cardList;
        }, function() {
            alert('카드를 불러오지 못했어요. 새로고침을 해주시겠어요?');
        });
    },
    post: function($scope, cCtrl) {
        $('.cardForm').submit(function() {
            $(this).ajaxSubmit({
                //보내기전 validation check가 필요할경우
                beforeSubmit: function (data, $form, opt) {
                    /* 카드 올리기 전 baby check */
                    $('input.baby-check-input:checked').each(function(i, e) {
                        var obj = {};
                        obj.name = "bIds["+i+"]";
                        obj.value = $(this).data('bid');
                        data.push(obj);
                    });
                    console.log('data:', data);
                    return true;
                },
                /* submit이후의 처리. 제일 위에 방금 올린 카드 추가. */
                success: function(responseText, statusText, xhr, $form){
                    console.log('cardList', cCtrl.cardList);
                    console.log('responseText', responseText.res);
                    cCtrl.cardList.unshift(responseText.res);
                    $('.cardForm').clearForm();
                    $('input:checkbox.baby-check-input').removeAttr('checked');
                    $("input[name='token']").val(token);
                    Start.getToday();
                    $scope.$apply();
                    Upload.resetPhotoBox();
                },
                //ajax error
                error: function(){
                    alert("문제가 생겼어요, 다시 올려주시겠어요?");
                }
            });
            return false;
        });
    },
    delete: function($scope, cCtrl, $http, cid) {
        $http.get(address + 'api/card/delete?cId='+cid).then(function(res) {
            Materialize.toast('삭제되었습니다.', 5000);
            testData = cCtrl.cardList;
            cCtrl.cardList.map(function(item, index, array){
                if(item.cid==cid) {
                    array.splice(index, 1);
                }
            });
        }, function() {
            alert('카드를 삭제하지 못했습니다. 새로고침을 해주시겠어요?');
        });
    },
}

// var Poster = {
//     init: function() {

//     },
//     selectTemplate: function(templateNum) {

//     },
//     selectCards: function(input) {

//     }

// }

var balbumApp = angular.module('balbumApp', ['ngRoute']);

balbumApp.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl : 'pages/card-timeline.htm',
        })
        .when('/!', {
            templateUrl : 'pages/card-timeline.htm',
        })
        .when('/baby/:babyId', {
            templateUrl : 'pages/card-timeline.htm',
        })
        .when('/poster', {
            templateUrl : 'pages/poster.htm',
            controller  : 'PosterController'
        })
        .when('/poster/make', {
            templateUrl : 'pages/make-poster.htm',
            controller  : 'PosterController'
        })
        .when('/poster/select', {
            templateUrl : 'pages/select-card.htm',
            controller  : 'PosterController'
        })
        .when('/poster/print', {
            templateUrl : 'pages/print-poster.htm',
            controller  : 'PosterController'
        })
        .when('/poster/view', {
            templateUrl : 'pages/view-poster.htm',
            controller  : 'PosterController'
        })
        .when('/settings', {
            templateUrl : 'pages/settings.htm',
            controller  : 'SettingsController'
        });
    // $locationProvider.html5Mode(true); //url에서 해쉬값 떼기
});

balbumApp.controller('MainController', function($scope, $http) {
    console.log("메인컨트롤러");
    var bMain = this;
    bMain.babyList;
    bMain.token;
    bMain.userInfo;
    bMain.foundUser;

    Main.init(bMain, $scope, $http);


    User.get($http, bMain); /* 서버에 저장된 유저 토큰값으로 불러오기 */
    User.getBaby($http, bMain); /* 서버에 저장된 유저 토큰별 아이 불러오기 */
    InitModal.postBaby($scope, bMain); /* 모달에서 아이 추가하기 */

    $('.btn-get-family').click(function() { /* main modal에서 가족 검색 */
        InitModal.getFamily($scope, $http, bMain);
    });
});

balbumApp.controller('CardController', function($scope, $http, $routeParams) {
    console.log("카드컨트롤러");
    var cCtrl = this;
    var filteredBId = $routeParams.babyId;
    cCtrl.cardList;
    cCtrl.filteredBaby;


    /* 함수들 초기화 */
    Start.init($scope);
    Upload.init();
    CardCRUD.init($http, cCtrl);
    InitModal.init($scope, cCtrl);


    if(filteredBId) { /* 아기 타임라인이면 포스트 숨기고 타이틀 열기 */
        cCtrl.isBabyPage = true;
        User.getBabyInfo($http, this, filteredBId); /* 현재 타임라인 아이정보 불러오기 */
    }

    CardCRUD.get($http, this, filteredBId); /* 서버에 저장된 카드 가져오기 */
    CardCRUD.post($scope, this); /* 카드를 서버에 저장하기 */


    cCtrl.cardActionDropdownClick = function($event, cid) {
        $event.stopPropagation();
        $('.baby-card[data-cid="' + cid + '"]').find('.action-dropdown-menu').toggleClass("active");
    }

    cCtrl.cardModify = function(cid) {
        console.log("modify", cid);
        $('#update-modal').openModal();
    }
    cCtrl.cardDelete = function(cid) {
        CardCRUD.delete($scope, cCtrl, $http, cid);
    }


});

var templateNum = 0;
var selectedCardNum = 0;

balbumApp.controller('PosterController', function($scope, $http) {
    console.log("포스터컨트롤러");

    /* 포스터 페이지 올때마다 초기화 */
    $(".poster-container a").click(function(e){
        templateNum = 0;
        selectedCardNum = 0;
    });

    /* 템플릿 선택 페이지에 오게되면 초기화 */
    $(".gotoSelectTemplate").click(function(e){
        templateNum = 0;
        selectedCardNum = 0;
    });

    /* 카드 선택 페이지로 가는 버튼을 눌렀을 때  */
    $(".gotoSelectCard").click(function(e){
        /* 템플릿을 선택하지 않고 '다음' 버튼을 눌렀을 경우 */
        if(templateNum == 0){
            e.preventDefault();
            alert("템플릿을 선택해주세요!");
        }
    });

    /* 카드 선택 페이지 진입 시 html 초기화 */
     $(".templateNum").text(templateNum);

    /* 포스터 미리보기 페이지로 가는 버튼을 눌렀을 때  */
    $(".gotoPreviewPoster").click(function(e){
        /* 선택한 카드수가 템플릿에 필요한 카드수보다 적을 경우 */
        if(selectedCardNum < templateNum){
            e.preventDefault();
            alert("카드를 "+selectedCardNum+"개 선택하셨습니다. 총 "+templateNum+"개 선택해주세요.");
        }
    });

    /* 템플릿 선택 시 */
    $(".template-container").click( function(e) {
        templateNum = $("input[name=template]:checked").val();
    });

    /* 카드 선택 시 */
    $(".select-for-poster").click(function(e) {   // 그래서 event delegate가 필요함. 그런데 지금보니 index.js에 posterController 실행될때 js로 써도됨.
        $( this ).toggleClass( "chosen" ); // 카드를 선택할 때마다 'chosen'이라는 토글 클래스 추가/제거
    });



    /* 카드 선택 페이지 */
    var pCtrl = this;

    pCtrl.cardList;
    CardCRUD.get($http, pCtrl); /* 서버에 저장된 카드 가져오기 */

});

balbumApp.controller('SettingsController', function($scope) {
    $scope.message = 'Look! I am an about page.';
});

