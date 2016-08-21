
var authorController = {
	 loadAuthorList : function(id){
		get("/author/?action=getAuthorList",function(res){
			var data = JSON.parse(res);
			var x = document.createElement("SELECT");
			var option = document.createElement("option");
			option.text = "";
			option.value = "";
			x.id = id;
			x.addEventListener("change",authorController.loadAuthor);
			x.add(option);
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
	loadAuthor : function(){
		var id = document.getElementById("authorList").value;
		if(id === ""){
			document.getElementById("name").value = "";
			document.getElementById("yob").value = "";
			document.getElementById("description").value = "";
		}else
		get("/author/?action=getAuthor&authorId=" + id,function(res){
			var data = JSON.parse(res);
			document.getElementById("name").value = data.name;
			document.getElementById("yob").value = data.yob;
			document.getElementById("description").value = data.comment;
		});
	},
	saveAuthor : function(){
		var data = {};
		data.name = document.getElementById("name").value;
		data.yob = document.getElementById("yob").value;
		data.comment = document.getElementById("description").value;
		data.id = document.getElementById("authorList").value;
		post("/author/?action=saveAuthor",JSON.stringify(data),function(res){
			alert(res);
		});		
	},
	deleteAuthor : function(){
		var id = document.getElementById("authorList").value;
		get("/author/?action=deleteAuthor&authorId=" + id,function(res){alert(res);});
	}
};
