
$(function() {
	
	console.log("ready"); // 처음 메인 페이지 왔을 때 바로 실행됨.

	// $(".fff").click(function(){
	//     // this.toggleClass("bc-selected");
	//     console.log("fffff");
	//     // this.data("cid")~~~
	// });
	
	var templateNum;

	/* 템플릿 선택  */
	$( "section" ).delegate( ".template-container", "click", function(e) {   // 그래서 event delegate가 필요함. 그런데 지금보니 index.js에 posterController 실행될때 js로 써도됨.
  		templateNum = $("input[name=template]:checked").val();
	});

	/* '다음' 버튼 눌렀을 때  */
	$("section").delegate(".next-btn", "click", function(e){
		/* 템플릿을 선택하지 않고 '다음' 버튼을 눌렀을 경우 */
		if(templateNum == undefined){
			e.preventDefault();
			alert("템플릿을 선택해주세요!");
		}
		else {
			console.log(templateNum);
			console.log("fsfsdfdsafzdzvnzxnznzvnvnzvnnvzvccvxv");
			console.log(html);
			$(".templateNum").text(templateNum);
		}

		// if( 카드 선택 개수 제한 ){}
	});

	/* 카드 선택 페이지 */
	$(".select-card-container").load function(e){
		console.log("카드선택차잉다다다다ㅏ다다다다다달날닝ㄹㄴㄹㅁㄹ");
	});

	/* 카드 선택 */
	$( "section" ).delegate( ".select-for-poster", "click", function(e) {   // 그래서 event delegate가 필요함. 그런데 지금보니 index.js에 posterController 실행될때 js로 써도됨.
  		$( this ).toggleClass( "chosen" ); // 카드를 선택할 때마다 'chosen'이라는 토글 클래스 추가/제거 

	});



	// $('div.tumble').toggleClass('bounce'


});