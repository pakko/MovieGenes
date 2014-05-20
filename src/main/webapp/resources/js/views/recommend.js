app.RecommendView = Backbone.View.extend({
	
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

	loadData: function (id) {
		this.model = new app.ServerPaginatedCollection();
    	this.model.baseUrl = "rs/recommend/user/" + id + "/items";
    	this.model.setGenres("所有");
    	this.model.state.pageSize = 10;
    	this.model.fetch({reset: true});
    	
    	this.model.on("reset", this.render, this);
	},
	
	destroy:function () {
    },
	
	initialize:function () {
		var self = this;
		/*this.model.on('request', function() {
			self.$el.html("loading...");
        }),*/
        
        
        
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
        "click button": 	"showMore",
        "mouseenter .test2":	"starHoverEnter",
        "mouseleave .test2":	"starHoverLeave",
    },
    
    starHoverEnter: function(e) {
    	$(e.currentTarget).find('.caption-btm').slideDown(250); //.fadeIn(250)
    },
    
    starHoverLeave: function(e) {
    	$(e.currentTarget).find('.caption-btm').slideUp(205); //.fadeOut(205)
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
    
    render:function () {
    	//show #content first
    	this.$el.html(this.template());
    	console.log("x");
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
    	$('#recommendPagination', this.el).html(new app.PaginatorView({collection: this.model}).render().el);
    	
        _.each(this.model.models, function (movie) {
        	$('#recommendList', this.el).append(new app.RecommendItemView({model:movie}).render().el);
        }, this);
        
        //resize image
        $('#recommendList', this.el).find(".plain img").on("load", { self: self }, function (event) { 
        	$(event.currentTarget).css("width", "108px"); 
        	$(event.currentTarget).css("height", "160px");
        });
        //load default image
        $('#recommendList', this.el).find(".plain img").on("error", { self: self }, function (event) { 
        	$(this).attr("src", "resources/pics/Amy_Jones.jpg");
        });
        
        //rated star action
        $('.caption-btm').raty({
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
        
        return this;
    }

});

app.RecommendItemView = Backbone.View.extend({
    
    render:function () {
        var data = _.clone(this.model.attributes);
        data.id = this.model.id;
        
        this.$el.html(this.template(data));

        return this;
    }
});
