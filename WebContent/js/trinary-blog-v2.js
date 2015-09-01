// trinary-blog.js
var app = angular.module("trinary-blog", ["ngCookies", "ngRoute", "ngResource", "ngAnimate", "hateoas", "hc.marked"]);

function copyObject(object) {
	return JSON.parse(JSON.stringify(object));
}

app.config(function (HateoasInterceptorProvider) {
    HateoasInterceptorProvider.transformAllResponses();
});

app.controller('app-controller', function($scope, $http, $cookies) {
	console.log("APP CONTROLLER LOADED");
	
	$scope.divStates = {};
	$scope.authFailed = false;
	$scope.token = $cookies.get("token");
	if ($scope.token != null) {
		// Strip user data
		var tokenData = copyObject($cookies.getObject("token"));
		tokenData.user = null;
		tokenData.links = null;
		console.log(tokenData);
		$http({
			url: "/trinary-blog/v2/security/reauth",
			method: "POST",
			data: tokenData,
			headers: {'Content-Type': 'application/json'}
		}).success(function(data) {
			console.log("TOKEN: " + data.token);
			$scope.authenticated = true;
			$scope.token = data;
			$scope.principal = data.user;
			
			// Store the token in the cookie
			$cookies.putObject("token", data, {
				expires: new Date(data.expires)
			});
		}).error(function(data) {
			$scope.authenticated = false;
		});
	} else {
		$scope.authenticated = false;
	}
	
	$scope.preview = function(string, nWords) {
	    var words = string.split(" ", nWords);
	    var lastWord = words[words.length - 1];
	    var lastWordCount = 0;
	    
	    for (var i = 0; i < words.length; i++) {
	        if (words[i] == lastWord) {
	            lastWordCount++;
	        }
	    }
	    
	    var subs = string.split(lastWord);
	    var previewString = "";
	    
	    for (i = 0; i < lastWordCount; i++) {
	        previewString += subs[i] + lastWord;
	    }
	    
	    return previewString + "...";
	}
	
	$scope.show = function(event, id) {
		var element = document.getElementById(id);
		element.style.left = event.target.offsetLeft;
		element.style.top  = event.target.offsetTop + event.target.offsetHeight;
		$scope.divStates[id] = true;
	}
	
	$scope.toggle = function(event, id) {
		if ($scope.divStates[id]) {
			$scope.hide(event, id);
		} else {
			$scope.show(event, id);
		}
	}
	
	$scope.hide = function(event, id) {
		$scope.divStates[id] = false;
	}
	
	$scope.shouldShow = function(id) {
		return $scope.divStates[id];
	}
	
	$scope.createUser = function(newUser) {
		if (newUser.password != newUser.confirm) {
			return;
		}
		
		// Submit user creation request
		$http({
			url: "/trinary-blog/v2/security/user",
			method: "POST",
			data: newUser,
			headers: {'Content-Type': 'application/json'}
		}).success(function(data) {
			console.log("TOKEN: " + data.token);
			$scope.authenticated = true;
			$scope.token = data;
			$scope.principal = data.user;
			$scope.authFailed = false;
			
			// Store the token in the cookie
			$cookies.putObject("token", data, {
				expires: new Date(data.expires)
			});
		}).error(function(data) {
			$scope.authenticated = false;
			$scope.authError  = "Bad credentials.  Access denied!";
			$scope.authFailed = true;
		});
		
		newUser.username = "";
		newUser.password = "";
		newUser.confirm = "";
		newUser.emailAddress = "";
	}
	
	$scope.validateForm = function(form) {
		return form.valid;
	}
	
	$scope.login = function(form, creds) {
		$http({
			url: "/trinary-blog/v2/security/auth",
			method: "POST",
			data: {username: creds.username, password: creds.password},
			headers: {'Content-Type': 'application/json'}
		}).success(function(data) {
			console.log("TOKEN: " + data.token);
			$scope.authenticated = true;
			$scope.token = data;
			$scope.principal = data.user;
			$scope.authFailed = false;
			
			// Store the token in the cookie
			$cookies.putObject("token", data, {
				expires: new Date(data.expires)
			});
		}).error(function(data) {
			$scope.authenticated = false;
			$scope.authError  = "Bad credentials.  Access denied!";
			$scope.authFailed = true;
		});
		
		creds.username = "";
		creds.password = "";
		form.$setPristine();
	}
	
	$scope.logout = function() {
		// Strip user data
		var tokenData = copyObject($cookies.getObject("token"));
		tokenData.user = null;
		tokenData.links = null;
		console.log(tokenData);
		$http({
			url: "/trinary-blog/v2/security/deauth",
			method: "POST",
			data: tokenData,
			headers: {'Content-Type': 'application/json'}
		}).success(function(data) {
			console.log("TOKEN: " + data.token);
			$scope.authenticated = false;
			
			// Store the token in the cookie
			$cookies.remove("token");
		});
	}
});

app.controller('chat-controller', function($scope, $resource, $http) {
    var socket = new SockJS("/trinary-blog/v1/stomp");
    $scope.chatClient = Stomp.over(socket);
    $scope.chatLog = "";

    $scope.chatClient.connect({}, function() {
    	console.log("CONNECTED");
    	$scope.chatClient.subscribe("/topic/broadcast", function(message) {
        	var o = JSON.parse(message.body);
        	console.log("RECEIVED MESSAGE: " + o.text);
        	var div = document.getElementById("chat-display");
        	div.innerHTML += "<b>" + o.user + "</b>" + ": " + o.text + "<br/>";
        });
    });
    
    $scope.sendMessage = function(message) {
    	$scope.chatClient.send("/app/broadcast", {}, JSON.stringify({user: $scope.principal.username, text: message}));
    }
});

app.controller('blog-controller', function($scope, $resource, $http) {
	console.log("BLOG CONTROLLER LOADED");
	
	if ($scope.page == null) {
		$scope.page = 1;
	}
	
	setupBlogs();
	
	// Change the page of the given blog.
	$scope.changeCommentPage = function(blog, page) {
		blog.page = page;
		setupComments(blog);
		
		var element = document.getElementById("blog-" + blog.blogId + "-comment");
		element.scrollIntoView();
	}
	
	// Change the page of the given blog.
	$scope.changeBlogPage = function(blog, page) {
		$scope.page = page;
		setupBlogs();
	}
	
	// Submit a comment for a given blog.
	$scope.submitComment = function(form, blog, comment) {
		if (!$scope.authenticated || !comment) {
			return false;
		}
		
		form.$setPristine();
		
		// Submit the comment command
		$http({
			url: "/trinary-blog/v2/blog/" + blog.blogId + "/comment",
			method: "POST",
			data: comment,
			headers: {
				'Content-Type': 'application/json',
				'Authorization': 'Token token=' + $scope.token.token
			}
		}).success(function(data) {
			// Update this comments section
			setupComments(blog);
			comment.text = "";
		}).error(function(data, status) {
			// Authorization issue
			if (status > 400 && status < 500) {
				$scope.authenticated = false;
			}
		});
	}
	
	// Refresh the blogs on the page
	function setupBlogs() {
		// Get blogs
		var blogPage = $resource("/trinary-blog/v2/blog/?order=:order&page=:page&pageSize=:pageSize").get(
		{
			page: $scope.page, 
			pageSize: 10,
			order: "created:desc,id:desc"
		}, function() {
			$scope.blogPage = blogPage;
			$scope.blogs = blogPage.resources;
		
			angular.forEach(blogPage.resources, function (blog) {
				var author   = blog.resource("author").get(null, function() {
					blog.author = author;
				});
				blog.page = 1;
				blog.preview = true;
				setupComments(blog);
			});
		});
	}
	
	// Refresh the comments of this blog.
	function setupComments(blog) {
		var commentPage = blog.resource("comments").get(
		{
			page: blog.page, 
			pageSize: 10, 
			order: "created:desc,id:desc"
		}, function () {
			blog.commentPage = commentPage;
			blog.comments = commentPage.resources;
			angular.forEach(commentPage.resources, function (comment) {
				var author = comment.resource("author").get(null, function () {
					comment.author = author;
				});
			});
		});
	}
	
	$scope.range = function(min, max, step){
		step = step || 1;
		var input = [];
		for (var i = min; i <= max; i += step) input.push(i);
		return input;
  	};
});