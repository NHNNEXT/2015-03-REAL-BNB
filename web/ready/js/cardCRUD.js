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
		console.log("hello get");
		if (navigator.onLine) {
			console.log("hello get if")
			$.ajax({
				url: this.url+"/card",
				method: "GET",
				success: function(result) {
					console.log("success");
	                $.each(result.reverse(), function(index, item) {
	                	console.log(item);
	                    // callback(item.todo, item.id, item.completed);
	                });
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

cardCRUD.get();

