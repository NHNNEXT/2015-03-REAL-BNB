var cardCRUD = {
	// url : "http://10.73.39.92:8080/api",
	url : "http://dev.balbum.net/api",

	init : function() {
		$(window).on("online", this.onofflineListener);
		$(window).on("offline", this.onofflineListener);
	},
	onofflineListener : function() {
		// $("#header")[navigator.onLine?"removeClass":"addClass"]("offline");
		// if (navigator.onLine) $("#header").removeClass("offline");
		// else $("#header").addClass("offline");

		if (navigator.onLine) {
			//서버로 Sync
		}
	},
	get : function(callback) {
		if (navigator.onLine) {
			$.ajax({
				url: this.url+"/card",
				method: "GET",
				error: function(result) {
					var result = [{
						cId: 1234,
						fId: 1234,
						writer: {
							name: "챙챙",
							email: "erin314@naver.com"
						},
						img: "http://localhost:8000/img/photo1.jpg",
						content: "우리 아이가 이쁘다 ",
						modifiedDate: "1994-03-01:00:00:22",
						babies: [{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						},{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						}
						,{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						}]
					},{
						cId: 1234,
						fId: 1234,
						writer: {
							name: "챙챙",
							email: "erin314@naver.com"
						},
						img: "http://localhost:8000/img/photo1.jpg",
						content: "우리 아이가 이쁘다 ",
						modifiedDate: "1994-03-01:00:00:22",
						babies: [{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						},{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						}
						,{
							bID: 1,
							name: "pi",
							birth: "1990-02-01:00:00:00"
						}]
					}];
					callback(result);

					
				}
			});
		}
		else {
			console.log("hello get else");
			//Local에 저장
		}
	},
	add : function(todo, callback) {
		if (navigator.onLine) {
			$.ajax({
				url: this.url,
				method: "PUT",
				data: {"todo": todo},
				success: function(result) {
					callback(result.insertId);
				}
			});
		}
		else {
			//Local에 저장
		}
	},
	completed : function(key, complete, callback) {
		if (navigator.onLine) {
			$.ajax({
				url: this.url + "/" + key,
				method: "POST",
				data: {"completed": complete},
				success: function(result) {
	                callback();
				}
			});
		}
		else {
			//Local에 저장
		}
	},
	remove : function(key, callback) {
		if (navigator.onLine) {
			$.ajax({
				url: this.url + "/" + key,
				method: "DELETE",
				success: function(result) {
	                callback();
				}
			});
		}
		else {
			//Local에 저장
		}
	}
};

cardCRUD.get(function(result){
	$.each(result.reverse(), function(index, item) {
		console.log(item.img, item.content, item.modifiedDate, item.babies);
    });
});

