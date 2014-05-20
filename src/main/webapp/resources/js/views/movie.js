app.MovieView = Backbone.View.extend({
	
	setModel: function( model) {
	    // unbind all view related things
	    this.$el.children().removeData().unbind();
	    this.$el.children().remove();
	    this.stopListening();

	    // clear model
	    if ( this.model) {
	        this.model.unbind();
	        this.model.stopListening();        
	    }

	    // set new model and call initialize
	    this.model = model;
	    this.delegateEvents( this.events);    // will call undelegateEvents internally      
	    this.initialize();
	},  

	destroy:function () {
    },
    
	initialize:function () {
		this.model = new app.ServerPaginatedCollection();
    	this.model.setGenres("所有");
    	this.model.fetch({reset: true});
    	
		var self = this;
		/*this.model.on('request', function() {
			self.$el.html("loading...");
        }),*/
        this.model.on("reset", this.render, this);
        
        
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
        "click .genres": 	"changeGenres",
        "mouseenter .test1":	"starHoverEnter2",
        "mouseleave .test1":	"starHoverLeave2",
    },
    
    starHoverEnter2: function(e) {
    	$(e.currentTarget).find('.caption-btm2').slideDown(250); //.fadeIn(250)
    },
    
    starHoverLeave2: function(e) {
    	$(e.currentTarget).find('.caption-btm2').slideUp(205); //.fadeOut(205)
    },
    
    changeGenres: function(e) {
    	e.preventDefault();
    	var temp = $(e.currentTarget).text();
		//if category not equal, reset the current page index
		if(temp != this.genre) {
			this.model.state.currentPage = 1;
		}
		this.genre = temp;
    	this.model.setGenres(this.genre);
    	this.model.fetch({reset: true});
    	return this;
    },
    
    ratyed: function () {
    	//rated star action
        $('.caption-btm2').raty({
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
    },
    
    render:function () {
    	//show #content first
    	this.$el.html(this.template());

    	//show genres
    	var self = this;
    	_.each(this.genres, function (genre) {
    		if(self.genre == genre) {
    			$('#genres ul li').removeClass('active');
    			$('#genres ul', this.el).append("<li class=\"active genres\"><a href=\"#" + genre + "\">" + genre + "</a></li>");
    		}
    		else {
    			$('#genres ul', this.el).append("<li class=\"genres\"><a href=\"#" + genre + "\">" + genre + "</a></li>");
    		}
    	}, this);
    	
    	//show pagination
    	$('#pagination', this.el).html(new app.PaginatorView({collection: this.model}).render().el);

        _.each(this.model.models, function (movie) {
        	$('#movieList', this.el).append(new app.MovieItemView({model:movie}).render().el);
        }, this);
        
        //resize image
        $('#movieList', this.el).find(".plain img").on("load", { self: self }, function (event) { 
        	$(event.currentTarget).css("width", "108px"); 
        	$(event.currentTarget).css("height", "160px");
        });
        //load default image
        $('#movieList', this.el).find(".plain img").on("error", { self: self }, function (event) { 
        	$(this).attr("src", "resources/pics/Amy_Jones.jpg");
        });
        
        this.ratyed();
        
        return this;
    }

});

app.MovieItemView = Backbone.View.extend({

	tagName:"div",
    className:'col-sm-4 col-md-2',
    
    initialize: function () {
    	$('.collapse').collapse();
    },
    
    render:function () {
        var data = _.clone(this.model.attributes);
        data.id = this.model.id;
        this.$el.html(this.template(data));
        
        return this;
    }
});

//movie details
app.MovieDetailsView = Backbone.View.extend({

	initialize: function () {
		
	},
	
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