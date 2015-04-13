// trinary-blog.js
var app = angular.module("trinary-blog", ["ngCookies", "ngRoute", "ngResource", "hateoas", "hc.marked"]);

function clearTextAreas() {
	var textAreas = document.getElementsByTagName("textarea");
	for (var i = 0; i < textAreas.length; i++) {
		var textArea = textAreas[i];
		textArea.value = "";
	}
}

function copyObject(object) {
	return JSON.parse(JSON.stringify(object));
}

app.config(function (HateoasInterceptorProvider) {
    HateoasInterceptorProvider.transformAllResponses();
});

app.controller('app-controller', function($scope, $http, $cookies) {
	console.log("APP CONTROLLER LOADED");
	
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
	
	$scope.login = function(username, password) {
		console.log("USERNAME: " + username);
		console.log("PASSWORD: " + password);
		$http({
			url: "/trinary-blog/v2/security/auth",
			method: "POST",
			data: {username: $scope.username, password: $scope.password},
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