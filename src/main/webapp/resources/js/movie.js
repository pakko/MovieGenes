window.Movie = Backbone.Model.extend({

    urlRoot: "rs/movie",
    
    initialize:function () {
        this.related = new MovieCollection();
        this.related.url = this.urlRoot + "/" + this.id + "/related";
    }

});

window.MovieCollection = Backbone.Collection.extend({
    model: Movie,
    urlRoot: "rs/movie"
});

window.ServerPaginatedCollection = Backbone.PageableCollection.extend({
	  urlRoot: "rs/movie",
	  model: Movie,
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
		  this.url = this.urlRoot + "?genres=" + genres;
	  },
	  
	  parseState: function (resp, queryParams, state, options) {
          return {totalRecords: resp.totalRecords, totalPages: resp.totalPages, currentPage: resp.currentPage};
	  },
	  
	  parseRecords: function (resp, options) {
		  return resp.items;
	  }
});