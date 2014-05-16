var app = {

    views: {},
    models: {},
    loadTemplates: function(views, callback) {
        var deferreds = [];
        $.each(views, function(index, view) {
            if (app[view]) {
                deferreds.push($.get('tpl/' + view + '.html', function(data) {
                	app[view].prototype.template = _.template(data);
                }, 'html'));
            } else {
                alert(view + " not found");
            }
        });
        $.when.apply(null, deferreds).done(callback);
    }

};

app.Router = Backbone.Router.extend({
    routes: {
        "":                 "home",
        "about":          	"about",
        "movie/:id":    	"movieDetails",
        "movies":			"movies",
        "recommend/:id":    "recommend",
        "login#*q":			"login"
    },

    initialize: function () {
    	app.commonView = new app.CommonView();
        $('body').html(app.commonView.render().el);
        // Close the search dropdown on click anywhere in the UI
        $('body').click(function () {
            $('.dropdown').removeClass("open");
        });
        this.$content = $("#content");
    },

    home: function () {
        // Since the home view never changes, we instantiate it and render it only once
        if (!app.homelView) {
        	app.homelView = new app.HomeView();
        	app.homelView.render();
        } else {
            app.homelView.delegateEvents(); // delegate events when the view is recycled
        }
        this.$content.html(app.homelView.el);
        app.commonView.selectMenuItem('home-menu');
    },

    about: function () {
        if (!app.aboutView) {
        	app.aboutView = new app.AboutView();
        	app.aboutView.render();
        }
        this.$content.html(app.aboutView.el);
        app.commonView.selectMenuItem('about-menu');
    },
    
    movies: function () {
        if (!app.movieView) {
        	this.allMovies = new app.PaginatedCollection();
        	this.allMovies.fetch({reset: true});
        	app.movieView = new app.MovieView({model:this.allMovies, itemViewType: "movie"});
        	app.movieView.render();
        }
        
        this.$content.html(app.movieView.el);
        app.commonView.selectMenuItem('movie-menu');
    },
    
    recommend: function (id) {
        if (!app.recommendView) {
        	this.recommendMovies = new app.PaginatedCollection();
        	this.recommendMovies.url = "rs/recommend/user/" + id + "/items";
        	this.recommendMovies.fetch({reset: true});
        	app.recommendView = new app.MovieView({model:this.recommendMovies, itemViewType: "recommend"});
        	app.recommendView.render();
        }
        this.$content.html(app.recommendView.el);
        app.commonView.selectMenuItem('recommend-menu');
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


$(document).on("ready", function () {
	app.loadTemplates(["AboutView", "CommonView", "HomeView", "MovieDetailsView", "MovieItemView", "MovieSidebarListItemView", "MovieSummaryView", "MovieView", "RecommendItemView"],
        function () {
    		app.router = new app.Router();
            Backbone.history.start();
        });
});
