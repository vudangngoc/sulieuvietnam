var itemController = {

	loadItem : function(){
		var id = document.getElementById("bookList").value;
			var checkboxs = document.getElementsByName('authorId');
					for(var i = 0; i < checkboxs.length; i++){
				checkboxs[i].checked = false;
			}
		if(id === ""){
			document.getElementById("name").value = "";
			document.getElementById("year").value = "";
			document.getElementById("info").value = "";
		}else
		get("/book/?action=getBook&bookId=" + id,function(res){
			var data = JSON.parse(res);
			document.getElementById("name").value = data.bookName;
			document.getElementById("year").value = data.releaseYear;
      if(data.info != null)
        document.getElementById("info").value = data.info;
			for(var i = 0; i < data.authorId.length; i++){
			  document.getElementById(data.authorId[i]).checked = true;
			}
		});
	},
	onload : function(){
			get("/book/?action=getBookList",function(res){
			var data = JSON.parse(res);
			var x = document.createElement("SELECT");
			var option = document.createElement("option");
			option.text = "";
			option.value = "";
			x.id = "bookList";
			x.addEventListener("change",bookController.loadBook);
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
	}
}
