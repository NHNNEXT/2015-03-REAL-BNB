var address = "http://dev.balbum.net/";
// var address = "http://192.168.1.146:8080/";
// var token = localStorage.getItem('token');
var token = 'asdf1234';

var testData;

var Start = {
    init: function() {
        $("input[name='token']").val(token);
        $('.button-collapse').sideNav();
        // $('.modal-trigger').leanModal();
        $('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
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
        // $('.timemachine-wrapper .row').pushpin({ top: $('.timemachine-wrapper').offset().top }); //materialize pushpins 동적 활성화

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
    init: function() {
        this.steps();
        this.uploadPhoto();

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

            // $('body').css('overflow', 'hidden');
            // $('#main-modal').css('z-index', '1003');
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
            $(this).ajaxSubmit({
                //보내기전 validation check가 필요할경우
                beforeSubmit: function (data, $form, opt) {
                    return true;
                },
                /* submit이후의 처리. 제일 위에 방금 올린 카드 추가. */
                success: function(responseText, statusText, xhr, $form){
                    bMain.babyList.push(responseText.res);
                    console.log("베이비리스트 가랏", bMain.babyList);
                    $('#babyForm').clearForm();
                    $scope.$apply();
                    $("input[name='token']").val(token);
                    // Upload.resetPhotoBox();
                },
                //ajax error
                error: function(){
                    alert("문제가 생겼어요, 다시 올려주시겠어요?");
                }
            });
            return false;
        });
    },
    getFamily: function($http, bMain) {
        $http({
            url: address + 'api/user/family/findFromMail',
            method: "GET",
            params: {token: token, email:'a'}
        }).then( function(response) {
            console.log("res:", response);
        }, function() {
            alert('사용자 정보를 불러오지 못하였습니다.');
        });
    },
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
        }, function() {
            alert('사용자 정보를 불러오지 못하였습니다.');
        });
    },
    /* 카드 올릴 때 아이를 체크 */
    checkBaby: function(index, bId, isBabyChecked) {
        $('.check-hidden[name="bIds[' + index + ']"]').attr('value', bId);
        var bIdsArr = "bIds[" + User.babyIdx + "]";
        if(isBabyChecked){
            $('.check-hidden').eq(index).attr({
                name: bIdsArr,
                value: bId
            });
            User.babyIdx++;
        } else {
            $('.check-hidden').eq(index).removeAttr('name').removeAttr('value');
            User.babyIdx--;
        }
    }
}

var CardCRUD = {
    init: function() {

    },
    get: function($http, bMain) {
        $http.get(address + 'api/card?token='+token).then(function(res) {
            bMain.cardList = res.data.cardList;
        }, function() {
            alert('카드를 불러오지 못했어요. 새로고침을 해주시겠어요?');
        });
    },
    post: function($scope, bMain) {
        $('#cardForm').submit(function() {
            $(this).ajaxSubmit({
                //보내기전 validation check가 필요할경우
                beforeSubmit: function (data, $form, opt) {
                    return true;
                },
                /* submit이후의 처리. 제일 위에 방금 올린 카드 추가. */
                success: function(responseText, statusText, xhr, $form){
                    bMain.cardList.unshift(responseText.res);
                    $('#cardForm').clearForm();
                    $scope.$apply();
                    console.log("scope2", bMain.cardList);
                    $("input[name='token']").val(token);
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
    delete: function($scope, bMain, $http, cid) {
        $http.get(address + 'api/card/delete?cId='+cid).then(function(res) {
            Materialize.toast('삭제되었습니다.', 5000);
            testData = bMain.cardList;
            bMain.cardList.map(function(item, index, array){
                if(item.cid==cid) {
                    array.splice(index, 1);
                }
            });
        }, function() {
            alert('카드를 삭제하지 못했습니다. 새로고침을 해주시겠어요?');
        });
    },
}

var balbumApp = angular.module('balbumApp', ['ngRoute']);

balbumApp.config(function($routeProvider) {
    // $locationProvider.html5Mode(true);
    $routeProvider
        // route for the home page
        .when('/', {
            templateUrl : 'pages/card-timeline.htm',
            // controller  : 'CardController'
        })

        // route for the about page
        .when('/settings', {
            templateUrl : 'pages/setting.htm',
            controller  : 'SettingsController'
        });
});

balbumApp.controller('MainController', function($scope, $http) {
    console.log("메인컨트롤러");

});

balbumApp.controller('CardController', function($scope, $http) {
    console.log("카드컨트롤러");
    var bMain = this;
    /* 함수들 초기화 */
    Start.init();
    Upload.init();
    CardCRUD.init();
    InitModal.init();

    /* 아기목록, 카드목록 */
    bMain.babyList;
    bMain.cardList;
    /*카드올릴때 아이를 체크하면 hidden된 input에 데이터값이 박혀 들어간다. 서버 처리랑 연동때문.*/
    /* TODO: 서버에 올려지기 직전에 name이랑 value를 index값에 맞춰서 들어가게 해야한다. 지금은 버그 있음. */
    $scope.babyCheckChanged = function(index, bId, isBabyChecked) {
        return User.checkBaby(index, bId, isBabyChecked);
    }

    User.getBaby($http, this); /* 서버에 저장된 유저 토큰값으로 불러오기 */
    CardCRUD.get($http, this); /* 서버에 저장된 카드 가져오기 */
    CardCRUD.post($scope, this); /* 카드를 서버에 저장하기 */
    InitModal.postBaby($scope, this); /* 카드를 서버에 저장하기 */
    $('.btn-get-family').click(function() {
        InitModal.getFamily($http, this); /* main modal에서 가족 검색 */
    })

    $scope.cardActionDropdownClick = function($event, cid) {
        $event.stopPropagation();
        $('.baby-card[data-cid="' + cid + '"]').find('.action-dropdown-menu').toggleClass("active");
    }

    $scope.cardModify = function(cid) {
        console.log("modify", cid);
        $('#update-modal').openModal();
    }
    $scope.cardDelete = function(cid) {
        CardCRUD.delete($scope, bMain, $http, cid);
    }
    console.log("카드컨트롤러 끝");
});

balbumApp.controller('SettingsController', function($scope) {
    $scope.message = 'Look! I am an about page.';
});

