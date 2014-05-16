
app.Movie = Backbone.Model.extend({

    urlRoot:"rs/movie",
    
    initialize:function () {
        this.related = new app.MovieCollection();
        this.related.url = this.urlRoot + "/" + this.id + "/related";
    }

});

app.MovieCollection = Backbone.Collection.extend({
    model: app.Movie,
    url: "rs/movie/search"
});

app.PaginatedCollection = Backbone.PageableCollection.extend({
	  url: "rs/movie?genres=all",
	  model: app.Movie,
	  state: {
	    firstPage: 1,
	    currentPage: 1,
	    pageSize: 18
	  },
	  queryParams: {
	    currentPage: "currentPage",
	    pageSize: "limitSize",
	    sortField: "id",
	    sortOrder: "desc"
	  },
	  
	  setGenres: function (genres) {
		  this.url = "rs/movie?genres=" + genres;
	  },
	  
	  parseState: function (resp, queryParams, state, options) {
          return {totalRecords: resp.totalRecords, totalPages: resp.totalPages, currentPage: resp.currentPage};
	  },
	  
	  parseRecords: function (resp, options) {
		  return resp.items;
	  }
});

/*
var originalSync = Backbone.sync;
Backbone.sync = function (method, model, options) {
    if (method === "read") {
        options.dataType = "jsonp";
        return originalSync.apply(Backbone, arguments);
    }

};*/