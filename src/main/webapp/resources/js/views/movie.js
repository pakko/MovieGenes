app.MovieView = Backbone.View.extend({
	
	initialize:function () {
        this.model.on("reset", this.render, this);
        
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
    
    events: {
        "click a": "changeGenres"
    },
    
    changeGenres: function(e) {
    	e.preventDefault();
    	var temp = $(e.target).text();
    	
    	//exclude the page a click
    	if(isNaN(temp)) {
    		
    		//if category not equal, reset the current page index
    		if(temp != this.genre) {
    			this.model.state.currentPage = 1;
    		}
    		
    		this.genre = $(e.target).text();
	    	this.model.setGenres(this.genre);
    	}
    	this.model.fetch({reset: true});
    	return this;
    },
    
    render:function () {
    	//show #content first
    	this.$el.html(this.template());
    	
    	//show genres
    	var self = this;
    	_.each(this.genres, function (genre) {
    		if(self.genre == genre) {
    			$('#genres ul li').removeClass('active');
    			$('#genres ul', this.el).append("<li class=\"active\"><a href=\"#" + genre + "\">" + genre + "</a></li>");
    		}
    		else {
    			$('#genres ul', this.el).append("<li><a href=\"#" + genre + "\">" + genre + "</a></li>");
    		}
    	}, this);
    	
    	//show pagination
    	$('#pagination', this.el).html(new app.PaginatorView({collection: this.model}).render().el);

    	//show movie list
        _.each(this.model.models, function (movie) {
        	if(this.options.itemViewType == "movie") {
        		$('#movieList', this.el).append(new app.MovieItemView({model:movie}).render().el);
        	}
        	else if(this.options.itemViewType == "recommend") {
        		$('#movieList', this.el).append(new app.RecommendItemView({model:movie}).render().el);
        	}
        }, this);
        return this;
    }

});

app.MovieItemView = Backbone.View.extend({

	tagName:"div",
    className:'col-sm-4 col-md-2',
    
    setImgSize: function () {
    	$('.plain img').each(function() {
    		$(this).css("width", "108px"); 
	        $(this).css("height", "160px"); 
    	});
    },
    
    render:function () {
        var data = _.clone(this.model.attributes);
        data.id = this.model.id;
        this.$el.html(this.template(data));
        
        var self = this;
        self.$el.find("img").on("load", { self: self }, function (event) { 
        	event.data.self.setImgSize(); 
        });
        //load default image
        self.$el.find("img").on("error", { self: self }, function (event) { 
        	$(this).attr("src", "resources/pics/Amy_Jones.jpg");
        });
        
        return this;
    }
});

app.RecommendItemView = Backbone.View.extend({

	tagName:"div",
    className:'col-sm-4 col-md-2',
    
    setImgSize: function () {
    	$('.plain img').each(function() {
    		$(this).css("width", "108px"); 
	        $(this).css("height", "160px"); 
    	});
    },
    
    render:function () {
        var data = _.clone(this.model.attributes);
        data.id = this.model.id;
        this.$el.html(this.template(data));
        
        var self = this;
        self.$el.find("img").on("load", { self: self }, function (event) { 
        	event.data.self.setImgSize(); 
        });

        return this;
    }
});

//movie details
app.MovieDetailsView = Backbone.View.extend({

    render: function () {
    	//show movie details
        this.$el.html(this.template(this.model.attributes));
        
        //show #details
        $('#details', this.el).html(new app.MovieSummaryView({model:this.model}).render().el);
        this.model.related.fetch({
            success:function (data) {
                if (data.length == 0)
                    $('.no-reports').show();
            }
        });
        
        //show #reports
        $('#reports', this.el).append(new app.MovieSidebarListView({model:this.model.related}).render().el);
        return this;
    }
});

app.MovieSummaryView = Backbone.View.extend({

    render:function () {
        this.$el.html(this.template(this.model.attributes));
        return this;
    }

});


//movie sidebar list
app.MovieSidebarListView = Backbone.View.extend({

    tagName:'ul',

    className:'nav nav-list',

    initialize:function () {
    	var self = this;
        this.model.on("reset", this.render, this);
        this.model.on("add", function(movie){
        	self.$el.append(new app.MovieSidebarListItemView({model:movie}).render().el);
        });
    },

    render:function () {
    	this.$el.empty();
        _.each(this.model.models, function (movie) {
            this.$el.append(new app.MovieSidebarListItemView({model:movie}).render().el);
        }, this);
        return this;
    }

});

app.MovieSidebarListItemView = Backbone.View.extend({

    tagName:"li",
    
    render:function () {
        var data = _.clone(this.model.attributes);
        data.id = this.model.id;
        this.$el.html(this.template(data));
        return this;
    }

});