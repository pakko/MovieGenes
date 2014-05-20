var commonView = Backbone.View.extend({
    template:null, //handlebars template of the view
    templateData:{}, //data to be used while rendering the template
    el:$('#content'), //jquery element to render view into
    initialize:function () {    //compile view template
    },
    dateChanged:function () {    //called when user changes the date selected
        this.renderCommon();
    },
    appChanged:function () {    //called when user changes selected app from the sidebar
        /*countlyEvent.reset();

        var self = this;
        $.when(countlyEvent.initialize()).then(function() {
           self.render();
        });*/
    },
    beforeRender: function () {
        return true;
    },
    afterRender: function() {},
    render:function () {    //backbone.js view render function
    	$("#content-top").html("");
        $(this.el).html('<div id="content-loader"></div>');
        
        this.renderCommon();
        this.afterRender();
        app.pageScript();

        return this;
    },
    renderCommon:function (isRefresh) {
    }, // common render function of the view
    refresh:function () {    // resfresh function for the view called every 10 seconds by default
        return true;
    },
    restart:function () { // triggered when user is active after idle period
        this.refresh();
    },
    destroy:function () {
    },
    selectMenuItem: function(menuItem) {
        $('.navbar .nav li').removeClass('active');
        if (menuItem) {
            $('.' + menuItem + '-menu').addClass('active');
        }
    }
    
});

var Template = function () {
    this.cached = {};
};
var T = new Template();

$.extend(Template.prototype, {
    render:function (name, callback) {
        if (T.isCached(name)) {
            callback(T.cached[name]);
        } else {
            $.get(T.urlFor(name), function (raw) {
                T.store(name, raw);
                T.render(name, callback);
            });
        }
    },
    renderSync:function (name, callback) {
        if (!T.isCached(name)) {
            T.fetch(name);
        }
        T.render(name, callback);
    },
    prefetch:function (name) {
        $.get(T.urlFor(name), function (raw) {
            T.store(name, raw);
        });
    },
    fetch:function (name) {
        // synchronous, for those times when you need it.
        if (!T.isCached(name)) {
            var raw = $.ajax({'url':T.urlFor(name), 'async':false}).responseText;
            T.store(name, raw);
        }
    },
    isCached:function (name) {
        return !!T.cached[name];
    },
    store:function (name, raw) {
        T.cached[name] = Handlebars.compile(raw);
    },
    urlFor:function (name) {
        //return "/resources/templates/"+ name + ".handlebars";
        return name + ".html";
    }
});

window.SearchView = Backbone.View.extend({
	name: 'search',
    initialize:function () {
        this.template = Handlebars.compile($("#search-template").html());
    	this.searchResults = new MovieCollection();
    },
    render:function () {
    	$('.navbar-search').append(this.template(this.searchResults.models));
    	//$(this.el).html(this.template());
    },
    events: {
        "keyup .search-query": "search",
        "keypress .search-query": "onkeypress"
    },
    search: function (event) {
    	console.log("cc");
        var key = $('#searchText').val();
        this.searchResults.fetch({reset: true, data: {name: key}});
        var self = this;
        setTimeout(function () {
            $('.dropdown').addClass('open');
        });
    },
    onkeypress: function (event) {
        if (event.keyCode === 13) { // enter key pressed
            event.preventDefault();
        }
    }
});

window.HomeView = commonView.extend({
	name: 'home',
    initialize:function () {
        this.template = Handlebars.compile($("#home-template").html());
    },
    renderCommon:function () {
    	$(this.el).html(this.template());
    },
});

window.AboutView = commonView.extend({
	name: 'about',
    initialize:function () {
        this.template = Handlebars.compile($("#about-template").html());
    },
    renderCommon:function () {
    	$(this.el).html(this.template());
    },
});

window.MovieView = commonView.extend({
	name: 'movie',
    
    initialize:function () {
    	this.template = Handlebars.compile($("#movie-template").html());
    	this.initGenres();
    	this.loaded = false;
    },
    
    initGenres: function() {
    	var self = this;
    	$.ajax({
        	url: 'rs/movie/genres',
        	type: 'GET',
        	dataType: 'json',
            async: false,
            success: function(data) {
            	self.genres = data;
            }
        });
    },
    
    loadData: function() {
    	if(this.loaded)
    		return;
    	this.model = new ServerPaginatedCollection();
    	this.model.urlRoot = "rs/movie";
    	this.model.state.pageSize = 18;
		this.genre = "所有";
    	this.model.setGenres(this.genre);
    	this.model.fetch({reset: true});
    	this.loaded = true;
    	this.model.on("reset", this.renderCommon, this);
    },
    
    events: {
        "click .genres": 	"changeGenres"
    },
    
    changeGenres: function(e) {
    	e.preventDefault();
    	var temp = $(e.currentTarget).text();
    	
		//if category not equal, reset the current page index
		if(temp != this.genre) {
			console.log(this.model);
			this.model.state.currentPage = 1;
		}
		//this.model.urlRoot = "rs/movie";
    	//this.model.state.pageSize = 18;
		this.genre = temp;
    	this.model.setGenres(this.genre);
    	this.model.fetch({reset: true});
    	return this;
    },
    
    pageScript: function () {
    	$('#movieList .thumbnail').hover(
    			function() {
    				$(this).find('.caption-star').slideDown(250);
    			},
    			function() {
    				$(this).find('.caption-star').slideUp(205);
    			}
    	);
    	
    	//rated star action
        $('.caption-star').raty({
        	 width: '100%',
        	 path: 'resources/img',
        	 score: function() {
        		    return $(this).attr('data-score');
        	 },
        	 click: function(score) {
        		var userId = $("#userId").attr('data');
        		if(typeof userId == 'undefined')
        			alert("请先登录！");
        		
        		var itemId = $(this).attr('data');
        		var url = 'rs/preferences/' + userId + '/' + itemId + '/' + score;
        		
        		$.ajax({
                	url: url,
                	type: 'POST',
                	dataType: 'json',
                    async: false
                });
	    	 }
    	});
        
      //resize image
        $('#movieList', this.el).find(".plain img").on("load", { self: self }, function (event) { 
        	$(event.currentTarget).css("width", "108px"); 
        	$(event.currentTarget).css("height", "160px");
        });
        //load default image
        $('#movieList', this.el).find(".plain img").on("error", { self: self }, function (event) { 
        	$(this).attr("src", "resources/pics/Amy_Jones.jpg");
        });
    },
    
    renderCommon:function () {
    	if(typeof this.model == 'undefined')
    		return;
    	
    	this.templateData = {
    		"currentGenre": this.genre,
			"genres": this.genres,
			"data": this.model.models
    	};
    	this.$el.html(this.template(this.templateData));
        
        this.pageScript();
        
        return this;
    }
});

window.RecommendView = commonView.extend({
	name: 'recommend',
    
    initialize:function () {
    	this.template = Handlebars.compile($("#recommend-template").html());
    	
    	this.initGenres();
        
    	this.loaded = false;
    },
    
    initGenres: function() {
    	var self = this;
    	$.ajax({
        	url: 'rs/movie/genres',
        	type: 'GET',
        	dataType: 'json',
            async: false,
            success: function(data) {
            	self.genres = data;
            }
        });
    },
    
    loadData: function(id) {
    	if(this.loaded)
    		return;
    	console.log(id);
    	this.model = new ServerPaginatedCollection();
		
    	this.model.urlRoot = "rs/recommend/user/" + id + "/items";
    	this.model.state.pageSize = 10;
		this.genre = "所有";
    	this.model.setGenres(this.genre);
    	this.model.fetch({reset: true});
    	this.loaded = true;
    	this.model.on("reset", this.renderCommon, this);
    },
    
    events: {
        "click .genres2": 	"changeGenres",
        "click button": 	"showMore"
    },
    
    changeGenres: function(e) {
    	e.preventDefault();
    	var temp = $(e.currentTarget).text();
    	
		//if category not equal, reset the current page index
		if(temp != this.genre) {
			this.model.state.currentPage = 1;
		}
		//this.model.urlRoot = "rs/recommend/user/3032305/items";
    	//this.model.state.pageSize = 10;
		this.genre = temp;
    	this.model.setGenres(this.genre);
    	this.model.fetch({reset: true});
    	return this;
    },
    
    showMore: function(e) {
    	e.preventDefault();
    	var data = $(e.currentTarget).attr('data');
    	if ($('.display-' + data).is(":visible") == false) {
    		$('.display-' + data).show();
	    }
	    else {
	    	$('.display-' + data).hide();
	    }
    	return this;
    },
    
    pageScript: function () {
    	$('#recommendList .thumbnail').hover(
    			function() {
    				$(this).find('.caption-star').slideDown(250);
    			},
    			function() {
    				$(this).find('.caption-star').slideUp(205);
    			}
    	);
    	
    	//rated star action
        $('.caption-star').raty({
        	 width: '100%',
        	 path: 'resources/img',
        	 score: function() {
        		    return $(this).attr('data-score');
        	 },
        	 click: function(score) {
        		var userId = $("#userId").attr('data');
        		if(typeof userId == 'undefined')
        			alert("请先登录！");
        		
        		var itemId = $(this).attr('data');
        		var url = 'rs/preferences/' + userId + '/' + itemId + '/' + score;
        		
        		$.ajax({
                	url: url,
                	type: 'POST',
                	dataType: 'json',
                    async: false
                });
	    	 }
    	});
        
      //resize image
        $('#recommendList', this.el).find(".plain img").on("load", { self: self }, function (event) { 
        	$(event.currentTarget).css("width", "108px"); 
        	$(event.currentTarget).css("height", "160px");
        });
        //load default image
        $('#recommendList', this.el).find(".plain img").on("error", { self: self }, function (event) { 
        	$(this).attr("src", "resources/pics/Amy_Jones.jpg");
        });
    },
    
    renderCommon:function () {
    	if(typeof this.model == 'undefined')
    		return;
    	
    	this.templateData = {
    		"currentGenre": this.genre,
			"genres": this.genres,
			"data": this.model.models
    	};
    	console.log(this.model);
    	this.$el.html(this.template(this.templateData));
        
        this.pageScript();
        
        return this;
    }
});


var AppRouter = Backbone.Router.extend({
    routes: {
    	"":				"home",
        "about":          	"about",
        "movie/:id":    	"movieDetails",
        "movies":			"movies",
        "recommend/:id":    "recommend",
        "login#*q":			"login",
        "*path":			"main"
    },
    main:function () {
        this.navigate("/", true);
    },
    home: function () {
        this.renderWhenReady(this.homeView);
    },
    about: function () {
    	this.renderWhenReady(this.aboutView);
    },
    movies: function () {
    	this.renderWhenReady(this.movieView);
    	this.movieView.loadData();
    },
    recommend: function (id) {
    	this.renderWhenReady(this.recommendView);
    	this.recommendView.loadData(id);
    },
    /*recommend: function (id) {
    	if(this.id != id) {
    		this.recommendView.loadData(id);
    		this.id = id;
    	}
    	this.renderWhenReady(this.recommendView);
        this.commonView.selectMenuItem('recommend-menu');
    },
    
    movieDetails: function (id) {
        var movie = new app.Movie({id: id});
        var self = this;
        movie.fetch({
            success: function (data) {
                self.$content.html(new app.MovieDetailsView({model: data}).render().el);
            }
        });
        app.commonView.selectMenuItem();
    },
    
    login: function (q) {
    	var params = parseQueryString(q);
    	//var params = {access_token: "c2fae6bd88c6850748f0b4389a538f15"};
    	var self = this;
        $.ajax({
        	url: 'rs/user?access_token=' + params.access_token,
        	type: 'GET',
        	dataType: 'json',
            async: false,
            success: function(data) {
            	self.data = data;
            }
        });
        //add login view to show logging...
    	app.commonView.login(self.data);
    	window.location = "/";
    },*/
    
    renderWhenReady:function (viewName) { //all view renders end up here
    	// If there is an active view call its destroy function to perform cleanups before a new view renders
        if (this.activeView) {
            this.activeView.destroy();
        }
        this.activeView = viewName;
        viewName.render();
        viewName.selectMenuItem(viewName.name);
    },
    initialize: function () {
    	this.searchView = new SearchView();
    	this.searchView.render();
    	
        // Close the search dropdown on click anywhere in the UI
        $('body').click(function () {
            $('.dropdown').removeClass("open");
        });
        
        this.homeView = new HomeView();
        this.aboutView = new AboutView();
        this.movieView = new MovieView();
        this.recommendView = new RecommendView();
        
        Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {
            switch (operator) {
                case '==':
                    return (v1 == v2) ? options.fn(this) : options.inverse(this);
                case '===':
                    return (v1 === v2) ? options.fn(this) : options.inverse(this);
                case '<':
                    return (v1 < v2) ? options.fn(this) : options.inverse(this);
                case '<=':
                    return (v1 <= v2) ? options.fn(this) : options.inverse(this);
                case '>':
                    return (v1 > v2) ? options.fn(this) : options.inverse(this);
                case '>=':
                    return (v1 >= v2) ? options.fn(this) : options.inverse(this);
                case '&&':
                    return (v1 && v2) ? options.fn(this) : options.inverse(this);
                case '||':
                    return (v1 || v2) ? options.fn(this) : options.inverse(this);
                default:
                    return options.inverse(this);
            }
        });
    },
    
    pageScript:function () { //scripts to be executed on each view change
        
        var self = this;
        $(document).ready(function () {
        });
    }
});


function parseQueryString(queryString) {
    if (!_.isString(queryString))
        return;
    queryString = queryString.substring( queryString.indexOf('?') + 1 );
    var params = {};
    var queryParts = decodeURI(queryString).split(/&/g);
    _.each(queryParts, function(val) {
            var parts = val.split('=')
            if (parts.length >= 1) {
                var val = undefined
                if (parts.length == 2)
                    val = parts[1]
                params[parts[0]] = val
            }
        });
    return params;
}


var app = new AppRouter();
Backbone.history.start();