	$(function() {
  var datepicker = $("#datepicker");
  datepicker.datepicker({ 
    firstDay: 0, // 일요일 부터 시작 
    maxDate: 0, // 오늘 이후는 선택 못함
    dayNamesMin: 
        [ "일", "월", "화", "수", "목", "금", "토" ] ,
    dateFormat: "yy-mm-dd"
  });

  $("#ui-datepicker-div").addClass("ui-datepicker-default");
  
	datepicker.click(function() {
   $("#ui-datepicker-div").removeClass("ui-datepicker-default");
  });
});