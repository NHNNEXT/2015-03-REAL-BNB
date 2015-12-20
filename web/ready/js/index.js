var address = "http://dev.balbum.net/";
// var address = "http://192.168.1.146:8080/";
// var token = localStorage.getItem('token');
var token = 'token';

var testData;

var Start = {
    init: function() {
        $("input[name='token']").val(token);
        $('.button-collapse').sideNav();
        $('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
        $('.scrollspy').scrollSpy();
        $('.timemachine-wrapper .row').pushpin({ top: $('.timemachine-wrapper').offset().top });
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
    resetPhoto: function() {
        $('.upload-photo-box').css('display', 'block');
        $('#uploaded-photo').css('display', 'none');
    }
}

var User = {
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
    }
}

var CardCRUD = {
    init: function() {

    },
    get: function($http, bMain) {
        $http.get(address + 'api/card').then( function(res) {
            bMain.cardList = res.data.cardList;
        }, function() {
            alert('카드를 불러오지 못했어요. 새로고침을 해주시겠어요?');
        });
    },
    post: function($scope, bMain) {
        $('#ajaxForm').submit(function() {
            $(this).ajaxSubmit({
               //보내기전 validation check가 필요할경우
               beforeSerialize: function($form, options) {
                   // var bids = {'bids':[1,2]};
                   // $form.push(bids);
                   // console.log("폼이다", $form);
               },
               beforeSubmit: function (data, $form, opt) {
                // $('.baby-check input:checked').attr('type', 'text').attr('value', function(arr) {
                //     return this.id;
                // });
                testData = data;
                console.log('data', testData);
                // var bids = {'name':'bids','value':[1,2]};
                // data.push(bids);
                // console.log("데이터다", data);

                testData.map(function(item) {
                  if(item.name=='babies'){
                    // console.log(item);
                }
            });
                return true;
            },
                //submit이후의 처리
            success: function(responseText, statusText, xhr, $form){
                    bMain.cardList.unshift(responseText.res);
                    addData = responseText.res;
                    $('#ajaxForm').clearForm();
                    $scope.$apply();
                    $("input[name='token']").val(token);
                    Upload.resetPhoto();
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
balbumApp.controller('MainController', function($scope, $http) {
    var bMain = this;
    bMain.babyList;
    bMain.cardList;
    bMain.isBabyChecked = "haha";

    $scope.babyCheckChanged = function(index, bId, isBabyChecked) {
        // $scope.checkedBid = bid;
        // $(this).closest('.baby-check').find('.check-hidden').attr('value', isBabyChecked? null : bid);
        // $(this).closest('.baby-check').find('.check-hidden').attr('value', $(this));
        // $('.check-hidden[name="bIds[index]"]')
        // $('.check-hidden[name="bIds[' + index + ']"]').attr('value', bId);
        var bIdsArr = "bIds[" + index + "]";
        if(isBabyChecked){
            $('.check-hidden').eq(index).attr({
                name: bIdsArr,
                value: bId
            });
        } else {
            $('.check-hidden').eq(index).removeAttr('name').removeAttr('value');
        }
    }

    User.getBaby($http, this); /* 서버에 저장된 유저 토큰값으로 불러오기 */


    CardCRUD.get($http, this); /* 서버에 저장된 카드 가져오기 */
    CardCRUD.post($scope, this); /* 카드를 서버에 저장하기 */

    // $('input').on('change', function() {
    //     console.log("dkafjlawkfjwkl");
    // });
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
    // $(".baby-check-input").change(function() {
    //     $(this).closest('.baby-check').find('.check-hidden').attr('value', $(this).attr('value')? null : $(this).attr('id'));
    //     console.log($(this).closest('.baby-check').find('.check-hidden').attr('value'));
    // }).change();

    // $( "input" ).change(function() {console.log("changed")});
});

