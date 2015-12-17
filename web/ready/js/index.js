var address = "http://dev.balbum.net/";
// var address = "http://192.168.1.146:8080/";

var Start = {
    init: function() {
        $('.button-collapse').sideNav();
        $('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
        $('.scrollspy').scrollSpy();
        $('.timemachine-wrapper .row').pushpin({ top: $('.timemachine-wrapper').offset().top })
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
            $("#upload-photo").click();
        });

        // form이 변경되면 fileread를 한다.
        $("#upload-photo").change(function(){
            Upload.readURL(this);
            $('.upload-photo-box').css('display', 'none');
        });
    }
}

var cardCRUD = {
    init: function() {

    },
    get: function($http, cardTimeline) {
        $http.get(address + 'api/card').then( function(res) {
            cardTimeline.cardList = res.data.cardList;
        }, function() {
            alert('카드를 불러오지 못했어요. 새로고침을 해주시겠어요?');
        });
    },
    post: function($scope, cardTimeline) {
        $('#ajaxForm').submit(function() {
            $(this).ajaxSubmit({
               //보내기전 validation check가 필요할경우
               beforeSubmit: function (data, frm, opt) {
                    return true;
                },
                //submit이후의 처리
                success: function(responseText, statusText, xhr, $form){
                    cardTimeline.cardList.unshift(responseText.res);
                    addData = responseText.res;
                    $('#ajaxForm').clearForm();
                    $scope.$apply();
                },
                //ajax error
                error: function(){
                    alert("문제가 생겼어요, 다시 올려주시겠어요?");
                }
            });
            return false;
        });
    }
}

var balbumApp = angular.module('balbumApp', []);
balbumApp.controller('CardController', function($scope, $http) {
    var cardTimeline = this;
    cardTimeline.cardList;

    /* 서버에 저장된 카드 가져오기 */
    cardCRUD.get($http, this);
    /* 카드를 서버에 저장하기 */
    cardCRUD.post($scope, this);
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
});

