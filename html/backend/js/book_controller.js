
var bookController = {
	 onload : function(){
		get("/author/?action=getAuthorList",function(res){
			var data = JSON.parse(res);
			var x = document.createElement("SELECT");
			var content;
			content = "<ul>"
			for(var o in data){
				content += "<li><label for=\""+ o +"\"><input type=\"checkbox\" name=\"authorId\" id=\""+ o +"\">"+ data[o] +"</label></li>"
			}
			content += "</ul>";
			document.getElementById("divAuthorList").innerHTML = "";
			document.getElementById("divAuthorList").innerHTML = content;
		
		});
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
	},
	loadBook : function(){
		var id = document.getElementById("bookList").value;
			var checkboxs = document.getElementsByName('authorId');
					for(var i = 0; i < checkboxs.length; i++){
				checkboxs[i].checked = false;
			}
		if(id === ""){
			document.getElementById("name").value = "";
			document.getElementById("year").value = "";
			document.getElementById("info").value = "";
			document.getElementById("publishYear").value = "";
		  document.getElementById("publisher").value = "";
		  document.getElementById("translator").value = "";
		}else
		get("/book/?action=getBook&bookId=" + id,function(res){
			var data = JSON.parse(res);
			document.getElementById("name").value = data.bookName;
			document.getElementById("releaseYear").value = data.releaseYear;
			document.getElementById("publishYear").value = data.publishYear;
		  document.getElementById("publisher").value = data.publisher;
		  document.getElementById("translator").value = data.translator;
      if(data.info != null)
        document.getElementById("info").value = data.info;
			for(var i = 0; i < data.authorId.length; i++){
			  document.getElementById(data.authorId[i]).checked = true;
			}
		});
	},
	saveBook : function(){
	  if(!bookController.validate()){
	    alert('invalid data!');
	    return;
	  }
		var data = {};
		data.bookName = document.getElementById("name").value;
		data.releaseYear = document.getElementById("releaseYear").value;
		data.id = document.getElementById("bookList").value;
		data.info = document.getElementById("info").value;
		data.publishYear = document.getElementById("publishYear").value;
		data.publisher = document.getElementById("publisher").value;
		data.translator = document.getElementById("translator").value;
		data.authorId = [];
					var checkboxs = document.getElementsByName('authorId');
					for(var i = 0; i < checkboxs.length; i++){
				if(checkboxs[i].checked){
				  data.authorId.push(checkboxs[i].id);
				}
			}
		post("/book/?action=saveBook",JSON.stringify(data),function(res){
			alert(res);
		});		
	},
	deleteBook : function(){
		var id = document.getElementById("bookList").value;
		if(id === "") return;
		get("/book/?action=deleteBook&bookId=" + id,function(res){alert(res);});
	},
	loopForm: function(form) {
    var cbResults = 'Checkboxes: ';
    var radioResults = 'Radio buttons: ';
    for (var i = 0; i < form.elements.length; i++ ) {
        if (form.elements[i].type == 'checkbox') {
            if (form.elements[i].checked == true) {
                cbResults += form.elements[i].value + ' ';
            }
        }
        if (form.elements[i].type == 'radio') {
            if (form.elements[i].checked == true) {
                radioResults += form.elements[i].value + ' ';
            }
        }
    }
},
	validate : function(){
	  if(!Utils.isNumeric(document.getElementById("publishYear").value)) return false;
	  if(!Utils.isNumeric(document.getElementById("releaseYear").value)) return false;
	  return true;
	}
};
