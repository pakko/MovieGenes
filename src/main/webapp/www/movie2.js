window.BACKEND_URL = "http://localhost:8203/";

window.MovieCollection = Backbone.Collection.extend({
	url: BACKEND_URL + "rs/movie/search"
});

window.ServerPaginatedCollection = Backbone.PageableCollection.extend({
	findAllRecommend: function (callback) {
		var recommend = {"items":[{"commons":[{"id":"508","title":"费城故事 ","imdbID":"0107818","dbID":"1293145","rating":6.5,"picUrl":"http://content6.flixster.com/movie/10/93/80/10938004_det.jpg","genres":["剧情","同性"]},{"id":"3793","title":"X战警 ","imdbID":"0120903","dbID":"1295250","rating":7.0,"picUrl":"http://content7.flixster.com/movie/10/92/95/10929501_det.jpg","genres":["动作","科幻"]}],"userSimilarity":"10.00","userName":"1117","recommendItems":[{"id":"930","title":"匪帮说唱传奇 ","imdbID":"0472198","dbID":"2133403","rating":5.5,"picUrl":"http://content8.flixster.com/movie/10/88/40/10884038_det.jpg","genres":["传记","剧情","音乐"]},{"id":"48082","title":"科学睡眠 ","imdbID":"0354899","dbID":"1759371","rating":6.6,"picUrl":"http://content6.flixster.com/movie/10/89/25/10892548_det.jpg","genres":["喜剧","剧情","奇幻","爱情"]},{"id":"4979","title":"天才一族 ","imdbID":"0265666","dbID":"1306031","rating":7.2,"picUrl":"http://content6.flixster.com/movie/11/11/99/11119924_det.jpg","genres":["剧情","喜剧","家庭"]},{"id":"5992","title":"时时刻刻 ","imdbID":"0274558","dbID":"1305666","rating":7.4,"picUrl":"http://content8.flixster.com/movie/26/18/261814_det.jpg","genres":["剧情","传记"]},{"id":"38886","title":"鱿鱼和鲸 ","imdbID":"0367089","dbID":"1437377","rating":7.9,"picUrl":"http://content6.flixster.com/movie/25/02/250292_det.jpg","genres":["剧情","喜剧","家庭"]}]},{"commons":[{"id":"1653","title":"千钧一发 ","imdbID":"0119177","dbID":"1300117","rating":7.1,"picUrl":"http://content9.flixster.com/movie/25/53/255319_det.jpg","genres":["剧情","爱情","科幻","惊悚"]}],"userSimilarity":"10.00","userName":"10272","recommendItems":[{"id":"1298","title":"迷墙 ","imdbID":"0084503","dbID":"1296157","rating":6.5,"picUrl":"http://content8.flixster.com/movie/10/87/16/10871678_det.jpg","genres":["剧情","音乐"]},{"id":"1220","title":"福禄双霸天 ","imdbID":"0080455","dbID":"1297200","rating":6.9,"picUrl":"http://content6.flixster.com/movie/10/84/85/10848572_det.jpg","genres":["动作","喜剧","音乐","歌舞"]},{"id":"1273","title":"不法之徒 ","imdbID":"0090967","dbID":"1301798","rating":7.4,"picUrl":"http://content8.flixster.com/movie/10/93/40/10934066_det.jpg","genres":["喜剧","犯罪","剧情"]},{"id":"1358","title":"弹簧刀","imdbID":"0117666","dbID":"1294589","rating":8.3,"picUrl":"http://content7.flixster.com/movie/10/93/85/10938549_det.jpg","genres":["剧情"]}]},{"commons":[{"id":"3793","title":"X战警 ","imdbID":"0120903","dbID":"1295250","rating":7.0,"picUrl":"http://content7.flixster.com/movie/10/92/95/10929501_det.jpg","genres":["动作","科幻"]}],"userSimilarity":"10.00","userName":"18797","recommendItems":[{"id":"2478","title":"神勇三蛟龙 ","imdbID":"0092086","dbID":"1293178","rating":5.2,"picUrl":"http://content7.flixster.com/movie/25/45/254593_det.jpg","genres":["冒险","喜剧","西部"]},{"id":"3831","title":"拯救格雷斯 ","imdbID":"0195234","dbID":"1300947","rating":6.0,"picUrl":"http://content8.flixster.com/movie/10/91/52/10915290_det.jpg","genres":["喜剧","犯罪"]},{"id":"7815","title":"梦想奔驰 ","imdbID":"0418647","dbID":"1437909","rating":6.3,"picUrl":"http://content6.flixster.com/movie/25/03/250392_det.jpg","genres":["剧情","家庭","运动"]},{"id":"8827","title":"比尔·考斯比：他本人","imdbID":"0083652","dbID":"5050516","rating":7.0,"picUrl":"http://content8.flixster.com/movie/20/53/43/2053434_det.jpg","genres":["喜剧","纪录片"]},{"id":"3037","title":"小巨人 ","imdbID":"0065988","dbID":"1301428","rating":7.9,"picUrl":"http://content8.flixster.com/movie/10/91/68/10916850_det.jpg","genres":["冒险","剧情","历史","战争","西部"]},{"id":"1226","title":"蓬门今始为君开 ","imdbID":"0045061","dbID":"1295757","rating":8.0,"picUrl":"http://content8.flixster.com/movie/29/63/296314_det.jpg","genres":["剧情","喜剧","爱情"]},{"id":"1281","title":"大独裁者 ","imdbID":"0032553","dbID":"1295646","rating":8.9,"picUrl":"http://content8.flixster.com/movie/10/93/78/10937802_det.jpg","genres":["喜剧","剧情","战争"]}]},{"commons":[{"id":"4993","title":"指环王1：魔戒再现 ","imdbID":"0120737","dbID":"1291571","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/65/266503_det.jpg","genres":["剧情","动作","奇幻","冒险"]}],"userSimilarity":"10.00","userName":"20262","recommendItems":[{"id":"2843","title":"黑猫白猫 ","imdbID":"0118843","dbID":"1293236","rating":6.9,"picUrl":"http://content7.flixster.com/movie/10/83/83/10838389_det.jpg","genres":["喜剧","音乐","爱情"]},{"id":"1232","title":"潜行者 ","imdbID":"0079944","dbID":"1295656","rating":8.2,"picUrl":"http://content7.flixster.com/movie/29/43/294309_det.jpg","genres":["冒险","剧情","奇幻","悬疑","科幻"]},{"id":"3503","title":"飞向太空 ","imdbID":"0069293","dbID":"1300977","rating":8.3,"picUrl":"http://content9.flixster.com/movie/10/86/81/10868183_det.jpg","genres":["冒险","剧情","悬疑","爱情","科幻"]},{"id":"32892","title":"伊万的童年 ","imdbID":"0056111","dbID":"1294421","rating":8.6,"picUrl":"http://content8.flixster.com/movie/10/92/00/10920078_det.jpg","genres":["剧情","战争"]},{"id":"26150","title":"安德烈·卢布廖夫 ","imdbID":"0060107","dbID":"1298248","rating":8.7,"picUrl":"http://content9.flixster.com/movie/29/58/295899_det.jpg","genres":["传记","剧情","历史","战争"]},{"id":"1233","title":"从海底出击 ","imdbID":"0082096","dbID":"1293909","rating":9.0,"picUrl":"http://content6.flixster.com/movie/10/92/18/10921888_det.jpg","genres":["动作","剧情","历史","战争"]}]},{"commons":[{"id":"4226","title":"记忆碎片 ","imdbID":"0209144","dbID":"1304447","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/78/267827_det.jpg","genres":["犯罪","剧情","悬疑","惊悚"]}],"userSimilarity":"10.00","userName":"30210","recommendItems":[{"id":"541","title":"银翼杀手","imdbID":"0083658","dbID":"1291839","rating":8.0,"picUrl":"http://content8.flixster.com/movie/10/85/39/10853986_det.jpg","genres":["剧情","科幻","惊悚"]}]},{"commons":[{"id":"6534","title":"无敌浩克 ","imdbID":"0800080","dbID":"1866475","rating":6.1,"picUrl":"http://content6.flixster.com/movie/10/84/92/10849216_det.jpg","genres":["动作","科幻","惊悚"]}],"userSimilarity":"10.00","userName":"30490","recommendItems":[{"id":"26680","title":"哭泣宝贝 ","imdbID":"0099329","dbID":"1304713","rating":6.7,"picUrl":"http://content9.flixster.com/movie/55/36/05/5536059_det.jpg","genres":["喜剧","歌舞","爱情"]},{"id":"4936","title":"名扬四海 ","imdbID":"0080716","dbID":"1293043","rating":6.9,"picUrl":"http://content6.flixster.com/movie/26/83/268312_det.jpg","genres":["剧情","音乐"]},{"id":"1293","title":"甘地传 ","imdbID":"0083987","dbID":"1292238","rating":7.7,"picUrl":"http://content9.flixster.com/movie/10/85/69/10856927_det.jpg","genres":["传记","剧情","历史"]},{"id":"2096","title":"睡美人 ","imdbID":"0053285","dbID":"1297110","rating":7.8,"picUrl":"http://content8.flixster.com/movie/30/64/306418_det.jpg","genres":["动画","家庭","奇幻","歌舞","爱情"]}]},{"commons":[{"id":"4993","title":"指环王1：魔戒再现 ","imdbID":"0120737","dbID":"1291571","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/65/266503_det.jpg","genres":["剧情","动作","奇幻","冒险"]}],"userSimilarity":"10.00","userName":"32663","recommendItems":[{"id":"1959","title":"走出非洲 ","imdbID":"0089755","dbID":"1291840","rating":6.2,"picUrl":"http://content9.flixster.com/movie/10/85/35/10853583_det.jpg","genres":["冒险","传记","剧情","爱情"]},{"id":"2739","title":"紫色 ","imdbID":"0088939","dbID":"1294503","rating":6.7,"picUrl":"http://content9.flixster.com/movie/10/93/85/10938559_det.jpg","genres":["剧情"]}]},{"commons":[{"id":"4993","title":"指环王1：魔戒再现 ","imdbID":"0120737","dbID":"1291571","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/65/266503_det.jpg","genres":["剧情","动作","奇幻","冒险"]}],"userSimilarity":"10.00","userName":"32861","recommendItems":[{"id":"17","title":"理智与情感 ","imdbID":"0114388","dbID":"1299193","rating":7.9,"picUrl":"http://content6.flixster.com/movie/11/12/50/11125040_det.jpg","genres":["剧情","爱情"]},{"id":"858","title":"教父 ","imdbID":"0068646","dbID":"1291841","rating":9.0,"picUrl":"http://content9.flixster.com/movie/11/12/76/11127687_det.jpg","genres":["犯罪","剧情","惊悚"]}]},{"commons":[{"id":"4993","title":"指环王1：魔戒再现 ","imdbID":"0120737","dbID":"1291571","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/65/266503_det.jpg","genres":["剧情","动作","奇幻","冒险"]}],"userSimilarity":"10.00","userName":"33008","recommendItems":[{"id":"8974","title":"海绵宝宝历险记 ","imdbID":"0345950","dbID":"1309156","rating":6.2,"picUrl":"http://content6.flixster.com/movie/11/12/84/11128492_det.jpg","genres":["动画","冒险","喜剧","家庭"]},{"id":"55765","title":"美国黑帮 ","imdbID":"0765429","dbID":"1578503","rating":7.0,"picUrl":"http://content7.flixster.com/movie/84/42/76/8442765_det.jpg","genres":["犯罪","剧情"]},{"id":"608","title":"冰血暴 ","imdbID":"0116282","dbID":"1292067","rating":8.4,"picUrl":"http://content6.flixster.com/movie/31/32/313212_det.jpg","genres":["犯罪","剧情","惊悚"]}]},{"commons":[{"id":"4993","title":"指环王1：魔戒再现 ","imdbID":"0120737","dbID":"1291571","rating":8.1,"picUrl":"http://content9.flixster.com/movie/26/65/266503_det.jpg","genres":["剧情","动作","奇幻","冒险"]},{"id":"3793","title":"X战警 ","imdbID":"0120903","dbID":"1295250","rating":7.0,"picUrl":"http://content7.flixster.com/movie/10/92/95/10929501_det.jpg","genres":["动作","科幻"]}],"userSimilarity":"10.00","userName":"42337","recommendItems":[{"id":"2296","title":"舞翻天 ","imdbID":"0120770","dbID":"1293645","rating":3.4,"picUrl":"http://content7.flixster.com/movie/25/48/254893_det.jpg","genres":["喜剧"]}]}],"totalRecords":678,"currentPage":1,"totalPages":68};
	    this.callLater(callback, recommend);
	},
	findAllMovies: function (callback) {
		var movies = {"items":[{"id":"3","title":"斗气老顽童 ","imdbID":"0107050","dbID":"1296537","rating":5.9,"picUrl":"http://content6.flixster.com/movie/25/60/256020_det.jpg","genres":["喜剧","剧情"]},{"id":"4","title":"待到梦醒时分 ","imdbID":"0114885","dbID":"1298842","rating":5.6,"picUrl":"http://content9.flixster.com/movie/10/94/17/10941715_det.jpg","genres":["喜剧","剧情","爱情"]},{"id":"11","title":"美国总统 ","imdbID":"0112346","dbID":"1293740","rating":7.0,"picUrl":"http://content7.flixster.com/movie/25/42/254205_det.jpg","genres":["喜剧","剧情","爱情"]},{"id":"14","title":"尼克松 ","imdbID":"0113987","dbID":"1297419","rating":6.7,"picUrl":"http://content6.flixster.com/movie/28/30/283016_det.jpg","genres":["传记","剧情"]},{"id":"17","title":"理智与情感 ","imdbID":"0114388","dbID":"1299193","rating":7.9,"picUrl":"http://content6.flixster.com/movie/11/12/50/11125040_det.jpg","genres":["剧情","爱情"]},{"id":"18","title":"四个房间 ","imdbID":"0113101","dbID":"1293346","rating":3.5,"picUrl":"http://content7.flixster.com/movie/93/49/77/9349773_det.jpg","genres":["喜剧"]},{"id":"21","title":"矮子当道 ","imdbID":"0113161","dbID":"1293335","rating":7.7,"picUrl":"http://content7.flixster.com/movie/98/65/89/9865893_det.jpg","genres":["喜剧","犯罪","惊悚"]},{"id":"22","title":"凶手就在门外 ","imdbID":"0112722","dbID":"1294197","rating":6.6,"picUrl":"http://content9.flixster.com/movie/27/27/272723_det.jpg","genres":["犯罪","悬疑","惊悚"]},{"id":"26","title":"奥赛罗 ","imdbID":"0114057","dbID":"1294034","rating":6.3,"picUrl":"http://content6.flixster.com/movie/10/92/98/10929892_det.jpg","genres":["剧情","爱情"]},{"id":"28","title":"劝导 ","imdbID":"0114117","dbID":"1299537","rating":7.7,"picUrl":"http://content6.flixster.com/movie/11/12/73/11127360_det.jpg","genres":["爱情","剧情"]},{"id":"29","title":"童梦失魂夜 ","imdbID":"0112682","dbID":"1306450","rating":7.0,"picUrl":"http://content6.flixster.com/movie/26/78/267824_det.jpg","genres":["冒险","喜剧","剧情","奇幻","科幻"]},{"id":"223","title":"疯狂店员 ","imdbID":"0109445","dbID":"1298320","rating":7.4,"picUrl":"http://content8.flixster.com/movie/10/93/69/10936950_det.jpg","genres":["喜剧"]},{"id":"226","title":"创业先锋 ","imdbID":"0096316","dbID":"1296408","rating":7.2,"picUrl":"http://content7.flixster.com/movie/25/57/255709_det.jpg","genres":["传记","剧情"]},{"id":"227","title":"终极特区 ","imdbID":"0109676","dbID":"1296848","rating":4.1,"picUrl":"http://content9.flixster.com/movie/27/31/273103_det.jpg","genres":["动作","惊悚"]},{"id":"234","title":"勇闯快活岛 ","imdbID":"0109758","dbID":"1298464","rating":2.4,"picUrl":"http://content8.flixster.com/movie/26/84/268410_det.jpg","genres":["喜剧","惊悚"]},{"id":"238","title":"小黄历险记","imdbID":"0113028","dbID":"3303327","rating":5.2,"picUrl":"http://content8.flixster.com/movie/26/37/263710_det.jpg","genres":["冒险","家庭"]},{"id":"248","title":"家有贵客糗事多","imdbID":"0110066","dbID":"1304328","rating":3.4,"picUrl":"http://content7.flixster.com/movie/26/06/260693_det.jpg","genres":["喜剧"]},{"id":"143","title":"Gospa","imdbID":"0113200","dbID":"5110760","rating":0.0,"picUrl":"","genres":["剧情"]}],"totalRecords":9559,"currentPage":1,"totalPages":532};
	    this.callLater(callback, movies);
	},
	callLater: function (callback, data) {
	    if (callback) {
	        setTimeout(function () {
	            callback(data);
	        });
	    }
	},
	sync: function(method, model, options) {
        if (method === "read") {
        	if(this.type == "movie") {
        		this.findAllMovies(function (data) {
                    options.success(data);
                });
        	}
        	if(this.type == "recommend") {
        		this.findAllRecommend(function (data) {
                    options.success(data);
                });
        	}
        }
    },
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
		  //this.url = this.urlRoot + "?genres=" + genres;
	  },
	  parseState: function (resp, queryParams, state, options) {
          return {totalRecords: resp.totalRecords, totalPages: resp.totalPages, currentPage: resp.currentPage};
	  },
	  parseRecords: function (resp, options) {
		  return resp.items;
	  }
});

window.PageHandleView = Backbone.View.extend({

    /** @property */
    tagName: "button",
    className: "btn btn-negative btn-block",

    /** @property */
    events: {
      "click a": "changePage"
    },

    /**
       @property {string|function(Object.<string, string>): string} title
       The title to use for the `title` attribute of the generated page handle
       anchor elements. It can be a string or an Underscore template function
       that takes a mandatory `label` parameter.
    */
    title: _.template('Page <%- label %>', null, {variable: null}),

    /**
       @property {boolean} isRewind Whether this handle represents a rewind
       control
    */
    isRewind: false,

    /**
       @property {boolean} isBack Whether this handle represents a back
       control
    */
    isBack: false,

    /**
       @property {boolean} isForward Whether this handle represents a forward
       control
    */
    isForward: false,

    /**
       @property {boolean} isFastForward Whether this handle represents a fast
       forward control
    */
    isFastForward: false,

    /**
       Initializer.

       @param {Object} options
       @param {Backbone.Collection} options.collection
       @param {number} pageIndex 0-based index of the page number this handle
       handles. This parameter will be normalized to the base the underlying
       PageableCollection uses.
       @param {string} [options.label] If provided it is used to render the
       anchor text, otherwise the normalized pageIndex will be used
       instead. Required if any of the `is*` flags is set to `true`.
       @param {string} [options.title]
       @param {boolean} [options.isRewind=false]
       @param {boolean} [options.isBack=false]
       @param {boolean} [options.isForward=false]
       @param {boolean} [options.isFastForward=false]
    */
    initialize: function (options) {
      var collection = this.collection;
      var state = collection.state;
      var currentPage = state.currentPage;
      var firstPage = state.firstPage;
      var lastPage = state.lastPage;

      _.extend(this, _.pick(options,
                            ["isRewind", "isBack", "isForward", "isFastForward"]));

      var pageIndex;
      if (this.isRewind) pageIndex = firstPage;
      else if (this.isBack) pageIndex = Math.max(firstPage, currentPage - 1);
      else if (this.isForward) pageIndex = Math.min(lastPage, currentPage + 1);
      else if (this.isFastForward) pageIndex = lastPage;
      else {
        pageIndex = +options.pageIndex;
        pageIndex = (firstPage ? pageIndex + 1 : pageIndex);
      }
      this.pageIndex = pageIndex;

      this.label = (options.label || (firstPage ? pageIndex : pageIndex + 1)) + '';
      var title = options.title || this.title;
      this.title = _.isFunction(title) ? title({label: this.label}) : title;
    },

    /**
       Renders a clickable anchor element under a list item.
    */
    render: function () {
      this.$el.empty();
      var anchor = document.createElement("a");
      anchor.href = '#';
      if (this.title) anchor.title = this.title;
      anchor.innerHTML = this.label;
      this.el.appendChild(anchor);

      var collection = this.collection;
      var state = collection.state;
      var currentPage = state.currentPage;
      var pageIndex = this.pageIndex;

      if (this.isRewind && currentPage == state.firstPage ||
         this.isBack && !collection.hasPrevious() ||
         this.isForward && !collection.hasNext() ||
         this.isFastForward && (currentPage == state.lastPage || state.totalPages < 1)) {
        this.$el.addClass("disabled");
      }
      else if (!(this.isRewind ||
                 this.isBack ||
                 this.isForward ||
                 this.isFastForward) &&
               state.currentPage == pageIndex) {
        this.$el.addClass("active");
      }

      this.delegateEvents();
      return this;
    },

    /**
       jQuery click event handler. Goes to the page this PageHandle instance
       represents. No-op if this page handle is currently active or disabled.
    */
    changePage: function (e) {
      e.preventDefault();
      var $el = this.$el, col = this.collection;
      if (!$el.hasClass("active") && !$el.hasClass("disabled")) {
        if (this.isRewind) col.getFirstPage({reset:true});
        else if (this.isBack) col.getPreviousPage({reset:true});
        else if (this.isForward) col.getNextPage();
        else if (this.isFastForward) col.getLastPage({reset:true});
        else col.getPage(this.pageIndex, {reset: true});
      }
      return this;
    }

  });

window.PaginatorView = Backbone.View.extend({

    /** @property */
    windowSize: 10,

    /**
       @property {number} slideScale the number used by #slideHowMuch to scale
       `windowSize` to yield the number of pages to slide. For example, the
       default windowSize(10) * slideScale(0.5) yields 5, which means the window
       will slide forward 5 pages as soon as you've reached page 6. The smaller
       the scale factor the less pages to slide, and vice versa.

       Also See:

       - #slideMaybe
       - #slideHowMuch
    */
    slideScale: 0.5,

    /**
       @property {Object.<string, Object.<string, string>>} controls You can
       disable specific control handles by setting the keys in question to
       null. The defaults will be merged with your controls object, with your
       changes taking precedent.
    */
    controls: {
      rewind: {
        label: "<<",
        title: "First"
      },
      back: {
        label: "前进",
        title: "Previous"
      },
      forward: {
        label: "更多",
        title: "Next"
      },
      fastForward: {
        label: ">>",
        title: "Last"
      }
    },

    /** @property */
    renderIndexedPageHandles: true,

    /**
       @property {Backgrid.Extension.PageHandle} pageHandle. The PageHandle
       class to use for rendering individual handles
    */
    pageHandle: PageHandleView,

    /** @property */
    goBackFirstOnSort: true,

    /**
       Initializer.

       @param {Object} options
       @param {Backbone.Collection} options.collection
       @param {boolean} [options.controls]
       @param {boolean} [options.pageHandle=Backgrid.Extension.PageHandle]
       @param {boolean} [options.goBackFirstOnSort=true]
    */
    initialize: function (options) {
      var self = this;
      self.controls = _.defaults(options.controls || {}, self.controls,
                                 PaginatorView.prototype.controls);

      _.extend(self, _.pick(options || {}, "windowSize", "pageHandle",
                            "slideScale", "goBackFirstOnSort",
                            "renderIndexedPageHandles"));

      var col = self.collection;
      self.listenTo(col, "add", self.render);
      self.listenTo(col, "remove", self.render);
      self.listenTo(col, "reset", self.render);
      self.listenTo(col, "backgrid:sorted", function () {
        if (self.goBackFirstOnSort) col.getFirstPage({reset: true});
      });
    },

    /**
      Decides whether the window should slide. This method should return 1 if
      sliding should occur and 0 otherwise. The default is sliding should occur
      if half of the pages in a window has been reached.

      __Note__: All the parameters have been normalized to be 0-based.

      @param {number} firstPage
      @param {number} lastPage
      @param {number} currentPage
      @param {number} windowSize
      @param {number} slideScale

      @return {0|1}
     */
    slideMaybe: function (firstPage, lastPage, currentPage, windowSize, slideScale) {
      return Math.round(currentPage % windowSize / windowSize);
    },

    /**
      Decides how many pages to slide when sliding should occur. The default
      simply scales the `windowSize` to arrive at a fraction of the `windowSize`
      to increment.

      __Note__: All the parameters have been normalized to be 0-based.

      @param {number} firstPage
      @param {number} lastPage
      @param {number} currentPage
      @param {number} windowSize
      @param {number} slideScale

      @return {number}
     */
    slideThisMuch: function (firstPage, lastPage, currentPage, windowSize, slideScale) {
      return ~~(windowSize * slideScale);
    },

    _calculateWindow: function () {
      var collection = this.collection;
      var state = collection.state;

      // convert all indices to 0-based here
      var firstPage = state.firstPage;
      var lastPage = +state.lastPage;
      lastPage = Math.max(0, firstPage ? lastPage - 1 : lastPage);
      var currentPage = Math.max(state.currentPage, state.firstPage);
      currentPage = firstPage ? currentPage - 1 : currentPage;
      var windowSize = this.windowSize;
      var slideScale = this.slideScale;
      var windowStart = Math.floor(currentPage / windowSize) * windowSize;
      if (currentPage <= lastPage - this.slideThisMuch()) {
        windowStart += (this.slideMaybe(firstPage, lastPage, currentPage, windowSize, slideScale) *
                        this.slideThisMuch(firstPage, lastPage, currentPage, windowSize, slideScale));
      }
      var windowEnd = Math.min(lastPage + 1, windowStart + windowSize);
      return [windowStart, windowEnd];
    },

    /**
       Creates a list of page handle objects for rendering.

       @return {Array.<Object>} an array of page handle objects hashes
    */
    makeHandles: function () {

      var handles = [];
      var collection = this.collection;

      var window = this._calculateWindow();
      var winStart = window[0], winEnd = window[1];

      if (this.renderIndexedPageHandles) {
        for (var i = winStart; i < winEnd; i++) {
          handles.push(new this.pageHandle({
            collection: collection,
            pageIndex: i
          }));
        }
      }

      var controls = this.controls;
      _.each(["forward", "back", "fastForward", "rewind"], function (key) {
    	  if(typeof this.options.control[key] != 'undefined')
    		  return;
        var value = controls[key];
        if (value) {
          var handleCtorOpts = {
            collection: collection,
            title: value.title,
            label: value.label
          };
          handleCtorOpts["is" + key.slice(0, 1).toUpperCase() + key.slice(1)] = true;
          var handle = new this.pageHandle(handleCtorOpts);
          if (key == "rewind" || key == "back") handles.unshift(handle);
          else handles.push(handle);
        }
      }, this);

      return handles;
    },

    /**
       Render the paginator handles inside an unordered list.
    */
    render: function () {
      this.$el.empty();

      if (this.handles) {
        for (var i = 0, l = this.handles.length; i < l; i++) {
          this.handles[i].remove();
        }
      }

      var handles = this.handles = this.makeHandles();

      //var ul = document.createElement("ul");
      for (var i = 0; i < handles.length; i++) {
        //ul.appendChild(handles[i].render().el);
    	this.el.appendChild(handles[i].render().el);
      }

      //this.el.appendChild(ul);

      return this;
    }

  });
