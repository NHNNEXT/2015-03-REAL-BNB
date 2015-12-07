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

angular.module('balbumApp', [])
.controller('CardController', function() {
    var cardTimeline = this;
    cardTimeline.testData = [{
        content: "테스트 콘텐츠 지롱",
        modified: '2015-04-02',
        imgUrl: "img/photo1.jpg",
        babies: [
            {name: "다정이",birth: "3개월", imgUrl: "img/baby1.jpeg"},
            {name: "연우",birth: "2살", imgUrl: "img/baby2.jpeg"}],
        cId: 1
        }, {
        content: "테스트를 또 하지롱 다정이",
        modified: '2015-04-05',
        imgUrl: "img/photo2.jpg",
        babies: [
            {name: "다정이",birth: "3개월", imgUrl: "img/baby1.jpeg"}],
        cId: 2
        }, {
        content: "테스트를 또 하지롱 연우 ",
        modified: '2015-04-07',
        imgUrl: "img/photo3.jpg",
        babies: [
            {name: "연우",birth: "2살", imgUrl: "img/baby2.jpeg"}],
        cId: 3
        }
    ];
});

$(function(){
    Start.init();
    Upload.init();
});

