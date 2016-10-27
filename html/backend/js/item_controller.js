var itemList = [];
var itemController = {

	loadBook : function(eventContext){
		var bookId = document.getElementById("bookList").value;

	  get("/book/?action=getAuthorsByBook&bookId=" + bookId,function (res){
	    var data = JSON.parse(res);
	    var x = document.createElement("SELECT");
	    x.id = 'authorList';
	    for(var o in data){
	      var option = document.createElement("option");
				option.text = data[o];
				option.value = o;
				x.add(option);
	    }
	    document.getElementById("divAuthorList").innerHTML = "";
			document.getElementById("divAuthorList").appendChild(x);
	  });
		
		get("/infomation/?action=getInfoListByBook&bookId=" + bookId,function(res){
			var data = JSON.parse(res);
			itemList = data;
			var x = document.createElement("SELECT");
			var option = document.createElement("option");
			option.text = "";
			option.value = "";
			x.id = "itemList";
			x.addEventListener("change",itemController.loadItem);
			x.add(option);
	    for(var o in data){
	      var option = document.createElement("option");
				option.text = data[o];
				option.value = o;
				x.add(option);
	    }
	    document.getElementById("divItemList").innerHTML = "";
			document.getElementById("divItemList").appendChild(x);
		});
	},
	loadItem : function (eventContext){
	  var infoId = document.getElementById("itemList").value;
	  if(infoId === ""){
	  	document.getElementById("title").value = "";
	    document.getElementById("time").value = "";
	    document.getElementById("info").value = "";
	    document.getElementById("lunarDate").value = "";
	    document.getElementById("tags").value = "";lunarDate
	    return;
	  }
	  get("/infomation/?action=getInfo&id=" + infoId,function(res){
	    var data = JSON.parse(res);
	    document.getElementById("authorList").value = data.author_id;
	    document.getElementById("title").value = data.title;
	    document.getElementById("time").value = data.date;
	    document.getElementById("opinion").value = data.oppinion;
	    document.getElementById("info").value = data.info;
	    document.getElementById("lunarDate").value = data.lunarDate;
	    document.getElementById("tags").value = data.tags;
	  });
	},
	loadAuthor : function (eventContext){
	var bookId = document.getElementById("bookList").value;
	  get("/infomation/?action=getAuthorsByBook&bookId=" + bookId,function (res){
	    var data = JSON.parse(res);
	    var x = document.createElement("SELECT");
	    for(var o in data){
	      var option = document.createElement("option");
				option.text = data[o];
				option.value = o;
				x.add(option);
	    }
	    document.getElementById("divAuthorList").innerHTML = "";
			document.getElementById("divAuthorList").appendChild(x);
	  });
	},
	onload : function(eventContext){
			get("/book/?action=getBookList",function(res){
			var data = JSON.parse(res);
			var x = document.createElement("SELECT");
			var option = document.createElement("option");
			option.text = "";
			option.value = "";
			x.id = "bookList";
			x.addEventListener("change",itemController.loadBook);
			x.add(option);
			for(var o in data){
				var option = document.createElement("option");
				option.text = data[o];
				option.value = o;
				x.add(option);
			}
			document.getElementById("divBookList").innerHTML = "";
			document.getElementById("divBookList").appendChild(x);
		});
	},
	saveItem : function (){
	  if(!document.getElementById("time").validity.valid){
	    alert("Incorrect date input");
	    return;
	  }
	  var data = {};
	  data.id = document.getElementById("itemList").value;
	  data.book_id = document.getElementById("bookList").value;
	  if(data.book_id === ""){
	    alert("Incorrect book input");
	    return;
	  }
	  data.author_id = document.getElementById("authorList").value;
	  data.title = document.getElementById("title").value;
	  data.date = document.getElementById("time").value;
	  data.oppinion = document.getElementById("opinion").value;
	  data.info = document.getElementById("info").value;
	  data.lunarDate = document.getElementById("lunarDate").value;
	  data.tags = document.getElementById("tags").value.split(',');
	  post("/infomation/?action=saveInfo",JSON.stringify(data),function(res){
			alert(res);
		});	
	},
	deleteItem : function (){
		var id = document.getElementById("itemList").value;
		if(id === "") return;
		get("/infomation/?action=deleteInfo&id=" + id,function(res){alert(res);});
	}
}
