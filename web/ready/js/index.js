var address = "http://dev.balbum.net/";
// var address = "http://192.168.1.146:8080/";
// var token = localStorage.getItem('token');
var token = 'token';

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
        $('.timemachine-wrapper .row').pushpin({ top: $('.timemachine-wrapper').offset().top });

        $('html').click(function() {
            $('.action-dropdown-menu').removeClass("active");
        });

        $('.action-dropdown-menu').click (function(e){ // 필요한지 의문
            e.stopPropagation();
        });
        $('.nav-update-modal').click(function(){
            $('#update-modal').openModal();
        });
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
                $('#uploaded-photo').attr('src', e.target.result);
                $('#uploaded-photo').css('display', 'block').css('max-width', '100%');
            }
            reader.readAsDataURL(input.files[0]);
        }
    },
    uploadPhoto: function() {
        // 업로드버튼이나 올려진 사진을 누르면 hidden된 form을 누른다.
        $('.upload-photo-box, #uploaded-photo').click(function(event) {
            $("#upload-photo-input").click();
        });

        // form이 변경되면 fileread를 한다.
        $("#upload-photo-input").change(function(){
            Upload.readURL(this);
            $('.upload-photo-box').css('display', 'none');
        });
    },
    resetPhotoBox: function() {
        $('.upload-photo-box').css('display', 'block');
        $('#uploaded-photo').css('display', 'none');
    }
}

var InitModal = {
    init: function() {
        $('.modal-main-btn').click(function(){
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
            console.log("res:", res);
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
            console.log("res:", bMain.babyList);
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
        $http.get(address + 'api/card').then(function(res) {
            bMain.cardList = res.data.cardList;
        }, function() {
            alert('카드를 불러오지 못했어요. 새로고침을 해주시겠어요?');
        });
    },
    post: function($scope, bMain) {
        $('#ajaxForm').submit(function() {
            $(this).ajaxSubmit({
                //보내기전 validation check가 필요할경우
                beforeSubmit: function (data, $form, opt) {
                    return true;
                },
                /* submit이후의 처리. 제일 위에 방금 올린 카드 추가. */
                success: function(responseText, statusText, xhr, $form){
                    bMain.cardList.unshift(responseText.res);
                    addData = responseText.res;
                    $('#ajaxForm').clearForm();
                    $scope.$apply();
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

var balbumApp = angular.module('balbumApp', []);
balbumApp.controller('MainController', function($scope, $http) {
    var bMain = this;

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

});

/*
balbumApp.controller('postController', function($scope, $http) {
    $scope.card = {};
    $scope.submitForm = function() {
        console.log('go to http!');
        $http({
            method  : 'POST',
            // method  : 'JSONP',
            url     : address + 'api/card',
            headers : {'Content-Type': 'multipart/form-data'},
            data    : $scope.card, //forms user object
            data: {
                email: "test1",
                token: "token",
                upload: $scope.file
            },
            transformRequest: function (data, headersGetter) {
                var formData = new FormData();
                angular.forEach(data, function (value, key) {
                    formData.append(key, value);
                });

                var headers = headersGetter();
                delete headers['Content-Type'];
                return formData;
            }
        })
        .success(function(data) {
            console.log(data);
            if (data.errors) {
                $scope.errorName = data.errors.name;

            } else {
                $scope.message = data.message;
                addData.content = $scope.card.content;
                testData.unshift(addData);
                $scope.card = '';
            }
        });
    };
});
*/

$(function(){
    Start.init();
    Upload.init();
    CardCRUD.init();
    InitModal.init();
});

