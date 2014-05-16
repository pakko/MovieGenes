directory.Movie = Backbone.Model.extend({
	initialize:function () {
        this.related = new directory.RelatedCollection();
        this.related.parent = this;
    },
    sync: function(method, model, options) {
        if (method === "read") {
            directory.store.findById(parseInt(this.id), function (data) {
                options.success(data);
            });
        }
    }
});

directory.SearchCollection = Backbone.Collection.extend({
    model: directory.Movie,
    sync: function(method, model, options) {
        if (method === "read") {
            directory.store.findByName(options.data.name, function (data) {
                options.success(data);
            });
        }
    }
});

directory.AllCollection = Backbone.Collection.extend({
    model: directory.Movie,
    sync: function(method, model, options) {
        if (method === "read") {
            directory.store.findAll(function (data) {
                options.success(data);
            });
        }
    }
});

directory.RelatedCollection = Backbone.Collection.extend({
    model: directory.Movie,
    sync: function(method, model, options) {
        if (method === "read") {
            directory.store.findRealted(this.parent.id, function (data) {
                options.success(data);
            });
        }
    }
});

directory.MemoryStore = function (successCallback, errorCallback) {

    this.findByName = function (searchKey, callback) {
        var movies = this.movies.filter(function (element) {
            return element.title.toLowerCase().indexOf(searchKey.toLowerCase()) > -1;
        });
        callLater(callback, movies);
    }
    
    this.findRealted = function (relatedId, callback) {
        var movies = this.movies.filter(function (element) {
            return relatedId === element.related;
        });
        
        callLater(callback, movies);
    }

    this.findById = function (id, callback) {
        var movies = this.movies;
        var movie = null;
        var l = movies.length;
        for (var i = 0; i < l; i++) {
            if (movies[i].id === id) {
            	movie = movies[i];
                break;
            }
        }
        callLater(callback, movie);
    }
    
    this.findAll = function (callback) {
        callLater(callback, this.movies);
    }

    // Used to simulate async calls. This is done to provide a consistent interface with stores that use async data access APIs
    var callLater = function (callback, data) {
        if (callback) {
            setTimeout(function () {
                callback(data);
            });
        }
    }

    this.movies = [{"id":1907966,"title":"疯狂原始人 / The Croods","rate":"8.9","picUrl":null,"related":1292052},{"id":4268598,"title":"告白","rate":"8.6","picUrl":null,"related":1292052},{"id":1292656,"title":"心灵捕手 / Good Will Hunting","rate":"8.6","picUrl":null,"related":1292052},{"id":1292434,"title":"一一","rate":"8.9","picUrl":null,"related":1292052},{"id":1292402,"title":"西西里的美丽传说 / Malèna","rate":"8.5","picUrl":null,"related":1292052},{"id":1292401,"title":"真爱至上 / Love Actually","rate":"8.5","picUrl":null,"related":1292052},{"id":1293359,"title":"菊次郎的夏天 / 菊次郎の夏","rate":"8.8","picUrl":null,"related":1292052},{"id":1296909,"title":"虎口脱险 / La grande vadrouille","rate":"9.0","picUrl":null,"related":1292052},{"id":1305164,"title":"甜蜜蜜","rate":"8.6","picUrl":null,"related":1292052},{"id":1295865,"title":"燃情岁月 / Legends of the Fall","rate":"8.7","picUrl":null,"related":1292052},{"id":5912992,"title":"熔炉 / 도가니","rate":"9.0","picUrl":null,"related":1292052},{"id":3007773,"title":"海盗电台 / The Boat That Rocked","rate":"8.7","picUrl":null,"related":1292052},{"id":1937946,"title":"穿越时空的少女 / 時をかける少女","rate":"8.6","picUrl":null,"related":1292052},{"id":1296339,"title":"爱在黎明破晓前 / Before Sunrise","rate":"8.7","picUrl":null,"related":1292052},{"id":1300960,"title":"碧海蓝天 / Le grand bleu","rate":"8.7","picUrl":null,"related":1292052},{"id":1292679,"title":"春光乍泄 / 春光乍洩","rate":"8.6","picUrl":null,"related":1292052},{"id":1292328,"title":"东邪西毒 / 東邪西毒","rate":"8.5","picUrl":null,"related":1292052},{"id":1291990,"title":"爱在日落黄昏时 / Before Sunset","rate":"8.7","picUrl":null,"related":1292052},{"id":2209573,"title":"贫民窟的百万富翁 / Slumdog Millionaire","rate":"8.4","picUrl":null,"related":1292052},{"id":1291818,"title":"饮食男女 / 飲食男女","rate":"8.8","picUrl":null,"related":1292052},{"id":1291870,"title":"雨人 / Rain Man","rate":"8.5","picUrl":null,"related":1292052},{"id":1417598,"title":"电锯惊魂 / Saw","rate":"8.6","picUrl":null,"related":1292052},{"id":2043546,"title":"秒速五厘米 / 秒速5センチメートル","rate":"8.5","picUrl":null,"related":1292052},{"id":4202302,"title":"借东西的小人阿莉埃蒂 / 借りぐらしのアリエッティ","rate":"8.6","picUrl":null,"related":1292052},{"id":1978709,"title":"黑天鹅 / Black Swan","rate":"8.4","picUrl":null,"related":1292052},{"id":1292052,"title":"肖申克的救赎 / The Shawshank Redemption","rate":"9.5","picUrl":null,"related":1292052},{"id":1295644,"title":"这个杀手不太冷 / Léon","rate":"9.4","picUrl":null,"related":1292052},{"id":1292720,"title":"阿甘正传 / Forrest Gump","rate":"9.3","picUrl":null,"related":1292052},{"id":1291546,"title":"霸王别姬","rate":"9.4","picUrl":null,"related":1292052},{"id":3541415,"title":"盗梦空间 / Inception","rate":"9.2","picUrl":null,"related":1292052},{"id":1292001,"title":"海上钢琴师 / La leggenda del pianista sull'oceano","rate":"9.1","picUrl":null,"related":1292052},{"id":1292063,"title":"美丽人生 / La vita è bella","rate":"9.4","picUrl":null,"related":1292052},{"id":3793023,"title":"三傻大闹宝莱坞 / 3 Idiots","rate":"9.1","picUrl":null,"related":1292052},{"id":1295124,"title":"辛德勒的名单 / Schindler's List","rate":"9.3","picUrl":null,"related":1292052},{"id":1291549,"title":"放牛班的春天 / Les choristes","rate":"9.1","picUrl":null,"related":1292052},{"id":1291560,"title":"龙猫 / となりのトトロ","rate":"9.1","picUrl":null,"related":1292052},{"id":1292000,"title":"搏击俱乐部 / Fight Club","rate":"9.1","picUrl":null,"related":1292052},{"id":1292722,"title":"泰坦尼克号 / Titanic","rate":"8.9","picUrl":null,"related":1292052},{"id":1291841,"title":"教父 / The Godfather","rate":"9.2","picUrl":null,"related":1292052},{"id":3011091,"title":"忠犬八公的故事 / Hachi: A Dog's Tale","rate":"9.1","picUrl":null,"related":1292052},{"id":1291828,"title":"天堂电影院 / Nuovo Cinema Paradiso","rate":"9.1","picUrl":null,"related":1292052},{"id":1291561,"title":"千与千寻 / 千と千尋の神隠し","rate":"9.1","picUrl":null,"related":1292052},{"id":1292213,"title":"大话西游之大圣娶亲 / 西遊記大結局之仙履奇緣","rate":"8.9","picUrl":null,"related":1292052},{"id":1293839,"title":"罗马假日 / Roman Holiday","rate":"8.9","picUrl":null,"related":1292052},{"id":1300267,"title":"乱世佳人 / Gone with the Wind","rate":"9.2","picUrl":null,"related":1292052},{"id":1292215,"title":"天使爱美丽 / Le fabuleux destin d'Amélie Poulain","rate":"8.8","picUrl":null,"related":1292052},{"id":1292064,"title":"楚门的世界 / The Truman Show","rate":"8.9","picUrl":null,"related":1292052},{"id":1849031,"title":"当幸福来敲门 / The Pursuit of Happyness","rate":"8.8","picUrl":null,"related":1292052},{"id":3319755,"title":"怦然心动 / Flipped","rate":"8.8","picUrl":null,"related":1292052},{"id":1293350,"title":"两杆大烟枪 / Lock, Stock and Two Smoking Barrels","rate":"9.1","picUrl":null,"related":1292052},{"id":1388216,"title":"撞车 / Crash","rate":"8.6","picUrl":null,"related":1292052},{"id":4848115,"title":"你看起来好像很好吃 / おまえうまそうだな","rate":"8.8","picUrl":null,"related":1292052},{"id":1302827,"title":"人工智能 / Artificial Intelligence: AI","rate":"8.5","picUrl":null,"related":1292052},{"id":1905462,"title":"荒野生存 / Into the Wild","rate":"8.7","picUrl":null,"related":1292052},{"id":1309163,"title":"恋恋笔记本 / The Notebook","rate":"8.4","picUrl":null,"related":1292052},{"id":1295399,"title":"七武士 / 七人の侍","rate":"9.1","picUrl":null,"related":1292052},{"id":1308817,"title":"两小无猜 / Jeux d'enfants","rate":"8.4","picUrl":null,"related":1292052},{"id":1291822,"title":"卢旺达饭店 / Hotel Rwanda","rate":"8.7","picUrl":null,"related":1292052},{"id":1306861,"title":"我是山姆 / I Am Sam","rate":"8.8","picUrl":null,"related":1292052},{"id":2213597,"title":"朗读者 / The Reader","rate":"8.4","picUrl":null,"related":1292052},{"id":1307793,"title":"燕尾蝶 / スワロウテイル","rate":"8.6","picUrl":null,"related":1292052},{"id":1292274,"title":"幸福终点站 / The Terminal","rate":"8.4","picUrl":null,"related":1292052},{"id":3443389,"title":"海洋 / Océans","rate":"8.9","picUrl":null,"related":1292052},{"id":1294408,"title":"音乐之声 / The Sound of Music","rate":"8.9","picUrl":null,"related":1292052},{"id":2297265,"title":"浪潮 / Die Welle","rate":"8.7","picUrl":null,"related":1292052},{"id":1294371,"title":"摩登时代 / Modern Times","rate":"9.1","picUrl":null,"related":1292052},{"id":5322596,"title":"超脱 / Detachment","rate":"8.7","picUrl":null,"related":1292052},{"id":1295409,"title":"纵横四海 / 緃横四海","rate":"8.6","picUrl":null,"related":1292052},{"id":1297574,"title":"英雄本色","rate":"8.6","picUrl":null,"related":1292052},{"id":3157605,"title":"巴黎淘气帮 / Le petit Nicolas","rate":"8.7","picUrl":null,"related":1292052},{"id":1294240,"title":"教父3 / The Godfather: Part III","rate":"8.6","picUrl":null,"related":1292052},{"id":3008247,"title":"穿条纹睡衣的男孩 / The Boy in the Striped Pajamas","rate":"8.7","picUrl":null,"related":1292052},{"id":5964718,"title":"一次别离 / جدایی نادر از سیمین","rate":"8.7","picUrl":null,"related":1292052},{"id":1291879,"title":"罗生门 / 羅生門","rate":"8.7","picUrl":null,"related":1292052},{"id":3217169,"title":"勇士 / Warrior","rate":"8.9","picUrl":null,"related":1292052},{"id":2149806,"title":"入殓师 / おくりびと","rate":"8.7","picUrl":null,"related":1292052},{"id":1780330,"title":"致命魔术 / The Prestige","rate":"8.7","picUrl":null,"related":1292052},{"id":1291548,"title":"死亡诗社 / Dead Poets Society","rate":"8.8","picUrl":null,"related":1292052},{"id":1787291,"title":"被嫌弃的松子的一生 / 嫌われ松子の一生","rate":"8.9","picUrl":null,"related":1292052},{"id":1299131,"title":"教父2 / The Godfather: Part Ⅱ","rate":"9.0","picUrl":null,"related":1292052},{"id":1296736,"title":"钢琴家 / The Pianist","rate":"8.9","picUrl":null,"related":1292052},{"id":1293182,"title":"十二怒汉 / 12 Angry Men","rate":"9.3","picUrl":null,"related":1292052},{"id":1291875,"title":"阳光灿烂的日子","rate":"8.7","picUrl":null,"related":1292052},{"id":1292343,"title":"蝴蝶效应 / The Butterfly Effect","rate":"8.6","picUrl":null,"related":1292052},{"id":1291545,"title":"大鱼 / Big Fish","rate":"8.7","picUrl":null,"related":1292052},{"id":1293544,"title":"沉默的羔羊 / The Silence of the Lambs","rate":"8.6","picUrl":null,"related":1292052},{"id":1292262,"title":"美国往事 / Once Upon a Time in America","rate":"9.0","picUrl":null,"related":1292052},{"id":1308807,"title":"哈尔的移动城堡 / ハウルの動く城","rate":"8.6","picUrl":null,"related":1292052},{"id":3792799,"title":"岁月神偷","rate":"8.7","picUrl":null,"related":1292052},{"id":1316510,"title":"射雕英雄传之东成西就 / 射鵰英雄傳之東成西就","rate":"8.7","picUrl":null,"related":1292052},{"id":3072124,"title":"玛丽和马克思 / Mary and Max","rate":"8.9","picUrl":null,"related":1292052},{"id":1291843,"title":"黑客帝国 / The Matrix","rate":"8.7","picUrl":null,"related":1292052},{"id":1303021,"title":"小鞋子 / بچههای آسمان","rate":"9.1","picUrl":null,"related":1292052},{"id":1292208,"title":"上帝之城 / Cidade de Deus","rate":"8.9","picUrl":null,"related":1292052},{"id":1291999,"title":"重庆森林 / 重慶森林","rate":"8.6","picUrl":null,"related":1292052},{"id":1293318,"title":"萤火虫之墓 / 火垂るの墓","rate":"8.7","picUrl":null,"related":1292052},{"id":1485260,"title":"本杰明·巴顿奇事 / The Curious Case of Benjamin Button","rate":"8.5","picUrl":null,"related":1292052},{"id":4917726,"title":"阳光姐妹淘 / 써니","rate":"8.8","picUrl":null,"related":1292052},{"id":1292849,"title":"拯救大兵瑞恩 / Saving Private Ryan","rate":"8.7","picUrl":null,"related":1292052},{"id":1292528,"title":"猜火车 / Trainspotting","rate":"8.6","picUrl":null,"related":1292052},{"id":1292224,"title":"飞越疯人院 / One Flew Over the Cuckoo's Nest","rate":"9.0","picUrl":null,"related":1292052},{"id":1929463,"title":"少年派的奇幻漂流 / Life of Pi","rate":"9.0","picUrl":null,"related":1292052},{"id":1291552,"title":"指环王3：王者无敌 / The Lord of the Rings: The Return of the King","rate":"9.0","picUrl":null,"related":1292052},{"id":3742360,"title":"让子弹飞","rate":"8.7","picUrl":null,"related":1292052},{"id":1292223,"title":"七宗罪 / Se7en","rate":"8.7","picUrl":null,"related":1292052},{"id":1298624,"title":"闻香识女人 / Scent of a Woman","rate":"8.8","picUrl":null,"related":1292052},{"id":1299398,"title":"大话西游之月光宝盒 / 西遊記第壹佰零壹回之月光寶盒","rate":"8.8","picUrl":null,"related":1292052},{"id":1292370,"title":"剪刀手爱德华 / Edward Scissorhands","rate":"8.7","picUrl":null,"related":1292052},{"id":1307914,"title":"无间道 / 無間道","rate":"8.8","picUrl":null,"related":1292052},{"id":3442220,"title":"海豚湾 / The Cove","rate":"9.4","picUrl":null,"related":1292052},{"id":1292220,"title":"情书 / Love Letter","rate":"8.7","picUrl":null,"related":1292052},{"id":1291858,"title":"鬼子来了","rate":"9.1","picUrl":null,"related":1292052},{"id":1306029,"title":"美丽心灵 / A Beautiful Mind","rate":"8.8","picUrl":null,"related":1292052},{"id":1291571,"title":"指环王1：魔戒再现 / The Lord of the Rings: The Fellowship of the Ring","rate":"8.8","picUrl":null,"related":1292052},{"id":6786002,"title":"触不可及 / Intouchables","rate":"9.0","picUrl":null,"related":1292052},{"id":1291572,"title":"指环王2：双塔奇兵 / The Lord of the Rings: The Two Towers","rate":"8.8","picUrl":null,"related":1292052},{"id":1291832,"title":"低俗小说 / Pulp Fiction","rate":"8.7","picUrl":null,"related":1292052},{"id":1851857,"title":"蝙蝠侠：黑暗骑士 / The Dark Knight","rate":"8.8","picUrl":null,"related":1292052},{"id":1652587,"title":"阿凡达 / Avatar","rate":"8.7","picUrl":null,"related":1292052},{"id":1294639,"title":"勇敢的心 / Braveheart","rate":"8.7","picUrl":null,"related":1292052},{"id":1292365,"title":"活着","rate":"8.9","picUrl":null,"related":1292052},{"id":2131459,"title":"机器人总动员 / WALL·E","rate":"9.3","picUrl":null,"related":1292052},{"id":2129039,"title":"飞屋环游记 / Up","rate":"8.8","picUrl":null,"related":1292052},{"id":1900841,"title":"窃听风暴 / Das Leben der Anderen","rate":"9.0","picUrl":null,"related":1292052},{"id":1309046,"title":"V字仇杀队 / V for Vendetta","rate":"8.7","picUrl":null,"related":1292052},{"id":2053515,"title":"曾经 / Once","rate":"8.4","picUrl":null,"related":1292052},{"id":1293460,"title":"雨中曲 / Singin' in the Rain","rate":"8.9","picUrl":null,"related":1292052},{"id":1865703,"title":"红辣椒 / パプリカ","rate":"8.6","picUrl":null,"related":1292052},{"id":1308575,"title":"蓝色大门 / 藍色大門","rate":"8.3","picUrl":null,"related":1292052},{"id":2365260,"title":"爱在暹罗 / รักแห่งสยาม","rate":"8.3","picUrl":null,"related":1292052},{"id":1292056,"title":"蝴蝶 / Le Papillon","rate":"8.5","picUrl":null,"related":1292052},{"id":1291853,"title":"英国病人 / The English Patient","rate":"8.4","picUrl":null,"related":1292052},{"id":1308777,"title":"暖暖内含光 / Eternal Sunshine of the Spotless Mind","rate":"8.4","picUrl":null,"related":1292052},{"id":1418834,"title":"断背山 / Brokeback Mountain","rate":"8.3","picUrl":null,"related":1292052},{"id":1305690,"title":"阿飞正传 / 阿飛正傳","rate":"8.4","picUrl":null,"related":1292052},{"id":1292329,"title":"牯岭街少年杀人事件 / 牯嶺街少年殺人事件","rate":"8.6","picUrl":null,"related":1292052},{"id":3075287,"title":"源代码 / Source Code","rate":"8.3","picUrl":null,"related":1292052},{"id":2363506,"title":"地球上的星星 / Taare Zameen Par","rate":"8.8","picUrl":null,"related":1292052},{"id":1291583,"title":"天空之城 / 天空の城ラピュタ","rate":"8.9","picUrl":null,"related":1292052},{"id":1291844,"title":"终结者2 / Terminator 2: Judgment Day","rate":"8.4","picUrl":null,"related":1292052},{"id":1301171,"title":"偷拐抢骗 / Snatch","rate":"8.5","picUrl":null,"related":1292052},{"id":1292062,"title":"美国丽人 / American Beauty","rate":"8.3","picUrl":null,"related":1292052},{"id":1292659,"title":"变脸 / Face/Off","rate":"8.3","picUrl":null,"related":1292052},{"id":1962116,"title":"我在伊朗长大 / Persepolis","rate":"8.7","picUrl":null,"related":1292052},{"id":1301169,"title":"跳出我天地 / Billy Elliot","rate":"8.7","picUrl":null,"related":1292052},{"id":1291992,"title":"末路狂花 / Thelma & Louise","rate":"8.5","picUrl":null,"related":1292052},{"id":1292055,"title":"再见列宁 / Good Bye Lenin!","rate":"8.7","picUrl":null,"related":1292052},{"id":1293399,"title":"莫扎特传 / Amadeus","rate":"8.6","picUrl":null,"related":1292052},{"id":1292287,"title":"新龙门客栈","rate":"8.3","picUrl":null,"related":1292052},{"id":1301617,"title":"不一样的天空 / What's Eating Gilbert Grape","rate":"8.6","picUrl":null,"related":1292052},{"id":1291565,"title":"疯狂约会美丽都 / Les triplettes de Belleville","rate":"8.8","picUrl":null,"related":1292052},{"id":1292270,"title":"梦之安魂曲 / Requiem for a Dream","rate":"8.8","picUrl":null,"related":1292052},{"id":3395373,"title":"蝙蝠侠：黑暗骑士崛起 / The Dark Knight Rises","rate":"8.4","picUrl":null,"related":1292052},{"id":1300616,"title":"买凶拍人 / 買兇拍人","rate":"8.5","picUrl":null,"related":1292052},{"id":1297359,"title":"幽灵公主 / もののけ姫","rate":"8.7","picUrl":null,"related":1292052},{"id":1299361,"title":"爱·回家 / 집으로...","rate":"9.0","picUrl":null,"related":1292052},{"id":1756073,"title":"我们俩","rate":"8.5","picUrl":null,"related":1292052},{"id":1438652,"title":"无耻混蛋 / Inglourious Basterds","rate":"8.3","picUrl":null,"related":1292052},{"id":1418200,"title":"傲慢与偏见 / Pride & Prejudice","rate":"8.2","picUrl":null,"related":1292052},{"id":1299327,"title":"夜访吸血鬼 / Interview with the Vampire: The Vampire Chronicles","rate":"8.3","picUrl":null,"related":1292052},{"id":1293708,"title":"她比烟花寂寞 / Hilary and Jackie","rate":"8.4","picUrl":null,"related":1292052},{"id":1401118,"title":"黄金三镖客 / Il buono, il brutto, il cattivo.","rate":"9.1","picUrl":null,"related":1292052},{"id":4023638,"title":"国王的演讲 / The King's Speech","rate":"8.2","picUrl":null,"related":1292052},{"id":1298070,"title":"加勒比海盗 / Pirates of the Caribbean: The Curse of the Black Pearl","rate":"8.4","picUrl":null,"related":1292052},{"id":1297630,"title":"第六感 / The Sixth Sense","rate":"8.7","picUrl":null,"related":1292052},{"id":3018123,"title":"家园 / Home","rate":"9.1","picUrl":null,"related":1292052},{"id":1292047,"title":"蓝白红三部曲之红 / Trois couleurs: Rouge","rate":"8.6","picUrl":null,"related":1292052},{"id":1291936,"title":"攻壳机动队 / 攻殻機動隊","rate":"8.9","picUrl":null,"related":1292052},{"id":1304056,"title":"茜茜公主 / Sissi","rate":"8.5","picUrl":null,"related":1292052},{"id":1292222,"title":"出租车司机 / Taxi Driver","rate":"8.4","picUrl":null,"related":1292052},{"id":5908478,"title":"我爱你 / 그대를 사랑합니다","rate":"9.0","picUrl":null,"related":1292052},{"id":4286017,"title":"速度与激情5 / Fast Five","rate":"8.3","picUrl":null,"related":1292052},{"id":1292211,"title":"对她说 / Hable con ella","rate":"8.6","picUrl":null,"related":1292052},{"id":1291554,"title":"歌剧魅影 / The Phantom of the Opera","rate":"8.3","picUrl":null,"related":1292052},{"id":4739952,"title":"初恋这件小事 / สิ่งเล็กเล็กที่เรียกว่า...รัก","rate":"8.2","picUrl":null,"related":1292052},{"id":5960606,"title":"再见我们的幼儿园 / さよならぼくたちのようちえん","rate":"8.7","picUrl":null,"related":1292052},{"id":1300299,"title":"杀人回忆 / 살인의 추억","rate":"8.5","picUrl":null,"related":1292052},{"id":1292281,"title":"迁徙的鸟 / Le peuple migrateur","rate":"9.1","picUrl":null,"related":1292052},{"id":4798888,"title":"叫我第一名 / Front of the Class","rate":"8.7","picUrl":null,"related":1292052},{"id":1296753,"title":"卡萨布兰卡 / Casablanca","rate":"8.6","picUrl":null,"related":1292052},{"id":6307447,"title":"被解救的姜戈 / Django Unchained","rate":"8.5","picUrl":null,"related":1292052},{"id":4920528,"title":"那些年，我们一起追的女孩 / 那些年，我們一起追的女孩","rate":"8.3","picUrl":null,"related":1292052},{"id":1760622,"title":"香水 / Perfume: The Story of a Murderer","rate":"8.3","picUrl":null,"related":1292052},{"id":1303037,"title":"喜宴","rate":"8.6","picUrl":null,"related":1292052},{"id":1300374,"title":"绿里奇迹 / The Green Mile","rate":"8.6","picUrl":null,"related":1292052},{"id":1302476,"title":"麦兜故事 / 麥兜故事","rate":"8.5","picUrl":null,"related":1292052},{"id":3920977,"title":"就是这样 / This Is It","rate":"8.7","picUrl":null,"related":1292052},{"id":1308857,"title":"可可西里","rate":"8.5","picUrl":null,"related":1292052},{"id":1292233,"title":"发条橙 / A Clockwork Orange","rate":"8.4","picUrl":null,"related":1292052},{"id":6860160,"title":"悲惨世界 / Les Misérables","rate":"8.5","picUrl":null,"related":1292052},{"id":1309027,"title":"罪恶之城 / Sin City","rate":"8.4","picUrl":null,"related":1292052},{"id":1292218,"title":"中央车站 / Central do Brasil","rate":"8.7","picUrl":null,"related":1292052},{"id":1300992,"title":"完美的世界 / A Perfect World","rate":"8.9","picUrl":null,"related":1292052},{"id":1293964,"title":"魂断蓝桥 / Waterloo Bridge","rate":"8.7","picUrl":null,"related":1292052},{"id":1419936,"title":"战争之王 / Lord of War","rate":"8.4","picUrl":null,"related":1292052},{"id":1297447,"title":"倩女幽魂","rate":"8.4","picUrl":null,"related":1292052},{"id":1858711,"title":"玩具总动员3 / Toy Story 3","rate":"8.9","picUrl":null,"related":1292052},{"id":1297478,"title":"上帝也疯狂 / The Gods Must Be Crazy","rate":"8.6","picUrl":null,"related":1292052},{"id":1293172,"title":"末代皇帝 / The Last Emperor","rate":"8.5","picUrl":null,"related":1292052},{"id":1305487,"title":"猫鼠游戏 / Catch Me If You Can","rate":"8.4","picUrl":null,"related":1292052},{"id":1867345,"title":"遗愿清单 / The Bucket List","rate":"8.4","picUrl":null,"related":1292052},{"id":1301106,"title":"布达佩斯之恋 / Gloomy Sunday - Ein Lied von Liebe und Tod","rate":"8.6","picUrl":null,"related":1292052},{"id":6019180,"title":"开心家族 / 헬로우 고스트","rate":"8.4","picUrl":null,"related":1292052},{"id":1292925,"title":"伴我同行 / Stand by Me","rate":"8.9","picUrl":null,"related":1292052},{"id":1293908,"title":"城市之光 / City Lights","rate":"9.2","picUrl":null,"related":1292052},{"id":1959195,"title":"忠犬八公物语 / ハチ公物語","rate":"9.0","picUrl":null,"related":1292052},{"id":5977835,"title":"桃姐","rate":"8.3","picUrl":null,"related":1292052},{"id":1292048,"title":"蓝白红三部曲之蓝 / Trois couleurs: Bleu","rate":"8.5","picUrl":null,"related":1292052},{"id":3824274,"title":"刺猬的优雅 / Le hérisson","rate":"8.7","picUrl":null,"related":1292052},{"id":6146955,"title":"寿司之神 / 二郎は鮨の夢を見る","rate":"8.8","picUrl":null,"related":1292052},{"id":3792848,"title":"相助 / The Help","rate":"8.7","picUrl":null,"related":1292052},{"id":3993559,"title":"赛德克·巴莱 / 賽德克·巴萊","rate":"8.5","picUrl":null,"related":1292052},{"id":1292226,"title":"2001太空漫游 / 2001: A Space Odyssey","rate":"8.5","picUrl":null,"related":1292052},{"id":1293527,"title":"美国X档案 / American History X","rate":"8.5","picUrl":null,"related":1292052},{"id":3236904,"title":"自闭历程 / Temple Grandin","rate":"8.8","picUrl":null,"related":1292052},{"id":1294490,"title":"上帝也疯狂2 / The Gods Must Be Crazy II","rate":"8.7","picUrl":null,"related":1292052},{"id":1395091,"title":"未麻的部屋 / Perfect Blue","rate":"8.7","picUrl":null,"related":1292052},{"id":3077668,"title":"天水围的日与夜 / 天水圍的日與夜","rate":"8.4","picUrl":null,"related":1292052},{"id":1300741,"title":"枪火 / 鎗火","rate":"8.4","picUrl":null,"related":1292052},{"id":4304402,"title":"精英部队2：大敌当前 / Tropa de Elite 2 - O Inimigo Agora É Outro","rate":"8.7","picUrl":null,"related":1292052},{"id":1303394,"title":"青蛇","rate":"8.2","picUrl":null,"related":1292052},{"id":6985810,"title":"狩猎 / Jagten","rate":"9.0","picUrl":null,"related":1292052},{"id":1397546,"title":"追随 / Following","rate":"9.0","picUrl":null,"related":1292052},{"id":1305666,"title":"时时刻刻 / The Hours","rate":"8.4","picUrl":null,"related":1292052},{"id":1307528,"title":"盲井","rate":"8.5","picUrl":null,"related":1292052},{"id":1291585,"title":"风之谷 / 風の谷のナウシカ","rate":"8.7","picUrl":null,"related":1292052},{"id":1293530,"title":"角斗士 / Gladiator","rate":"8.3","picUrl":null,"related":1292052},{"id":1316572,"title":"导盲犬小Q / クイール","rate":"8.3","picUrl":null,"related":1292052},{"id":1291557,"title":"花样年华 / 花樣年華","rate":"8.2","picUrl":null,"related":1292052},{"id":1308865,"title":"老男孩 / 올드보이","rate":"8.3","picUrl":null,"related":1292052},{"id":1296157,"title":"迷墙 / Pink Floyd The Wall","rate":"8.7","picUrl":null,"related":1292052},{"id":1293181,"title":"惊魂记 / Psycho","rate":"8.8","picUrl":null,"related":1292052},{"id":1302467,"title":"黑客帝国3：矩阵革命 / The Matrix Revolutions","rate":"8.2","picUrl":null,"related":1292052},{"id":3006772,"title":"第九区 / District 9","rate":"8.2","picUrl":null,"related":1292052},{"id":1428175,"title":"血钻 / Blood Diamond","rate":"8.3","picUrl":null,"related":1292052},{"id":1298653,"title":"荒岛余生 / Cast Away","rate":"8.3","picUrl":null,"related":1292052},{"id":2982823,"title":"河童之夏 / 河童のクゥと夏休み","rate":"8.6","picUrl":null,"related":1292052},{"id":1848925,"title":"恶童 / 鉄コン筋クリート","rate":"8.5","picUrl":null,"related":1292052},{"id":1293764,"title":"与狼共舞 / Dances with Wolves","rate":"8.8","picUrl":null,"related":1292052},{"id":1921583,"title":"魔术师 / L'illusionniste","rate":"8.7","picUrl":null,"related":1292052},{"id":1292275,"title":"罗拉快跑 / Lola rennt","rate":"8.2","picUrl":null,"related":1292052},{"id":1291588,"title":"岁月的童话 / おもひでぽろぽろ","rate":"8.4","picUrl":null,"related":1292052},{"id":3205624,"title":"社交网络 / The Social Network","rate":"8.1","picUrl":null,"related":1292052},{"id":6534248,"title":"无敌破坏王 / Wreck-It Ralph","rate":"8.7","picUrl":null,"related":1292052},{"id":3026357,"title":"老爷车 / Gran Torino","rate":"8.6","picUrl":null,"related":1292052},{"id":1844413,"title":"八月迷情 / August Rush","rate":"8.2","picUrl":null,"related":1292052},{"id":1298898,"title":"暗战 / 暗戰","rate":"8.3","picUrl":null,"related":1292052},{"id":3732800,"title":"我们天上见","rate":"8.5","picUrl":null,"related":1292052},{"id":1307394,"title":"千年女优 / 千年女優","rate":"8.4","picUrl":null,"related":1292052},{"id":4876722,"title":"钢的琴","rate":"8.2","picUrl":null,"related":1292052}];

    callLater(successCallback);

}

directory.store = new directory.MemoryStore();