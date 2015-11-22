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
    readURL: function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#uploaded-photo').attr('src', e.target.result);
                $('#uploaded-photo').css('display', 'block');
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

$(function(){
    Start.init();
    Upload.init();
});

