(function($){
  $(function(){

    $('.button-collapse').sideNav();

    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });

    // function readURL(input) {
    //     if (input.files && input.files[0]) {
    //         var reader = new FileReader();

    //         reader.onload = function (e) {
    //             $('#blah').attr('src', e.target.result);
    //         }
    //         reader.readAsDataURL(input.files[0]);
    //     }
    // }

    // $("#imgInp").change(function(){
    //     readURL(this);
    // });

  }); // end of document ready
})(jQuery); // end of jQuery name space
