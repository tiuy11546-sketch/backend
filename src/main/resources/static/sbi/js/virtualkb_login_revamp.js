var fieldObj;
var bCaps=false;
var focus_count=0;
var sHTML="";
var tempVk ="";
function getArr()
{
var keyArr=[['~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'],
			  		['`', ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'], '-', '='],
			  		[['q', 'w', 'e', 'r', 't'], ['y', 'u', 'i', 'o', 'p'], '{', '}', '|'],
			  		[['a', 's', 'd', 'f', 'g'], ['h', 'j', 'k', 'l'], '[', ']', '\\', '/'],
			  		[['z', 'x', 'c', 'v'], ['b', 'n', 'm'], '<', '>', ';', ':', '\'', '"'],
			  		[',', '.', '?']];

return (keyArr);
}
function getFocus(x) {
	
	fieldObj=document.getElementById(x);
}
function constructKeyboard(bool) {
	
	var check;
	if(typeof(bool) == 'undefined')
		check= document.getElementById('chkbox').checked;
	else
		check = bool;
	var keyArr = getArr();
	var str_trco="</tr><tr>";
	sHTML="<table	border='0' class='keyboardtb_new' cellspacing='3px' id='keypad' width='95%'><tr><td class='key_head' colspan='13'>Online Virtual Keyboard</td></tr><tr>";
	var dHTML="";
	for (var i=0; i<keyArr.length-1; i++){
		//alert(keyArr[i].length)
		for (var j=0; j<keyArr[i].length; j++){
			var code;
			if(typeof(keyArr[i][j])=='object'){
				while(keyArr[i][j].length>0){
					var ix=Math.floor(Math.random()*keyArr[i][j].length);
					var ch=keyArr[i][j].splice(ix,1);
					code=ch[0].charCodeAt(0);
					if(!check){
						sHTML="<table border='0' class='keyboardtbl' cellspacing='3px' id='keypad' width='100%'><tr><td><img src='/sbijava/retail/images/login_img.png' alt='login image' /></td></tr></table>";
					}
					else
					{
						sHTML+="<td onClick='putChar(" + code + ")' class='keyboardtblenb'>" + ch + "</td>";
					}
				}
			}else{
				code=keyArr[i][j].charCodeAt(0);
				if(check)
					sHTML+="<td onClick='putChar(" + code + ")' class='keyboardtblenb'>" + keyArr[i][j] + "</td>";
				else
					sHTML="<table	border='0' class='keyboardtbl' cellspacing='3px' id='keypad' width='100%'><tr><td><img src='/sbijava/retail/images/login_img.png' alt='login image' /></td></tr></table>";
					//dHTML="<span id='logimg'><img src='images/login_img.png' alt='login image' /></span>";
			}
		}
		sHTML+=str_trco;
	}
	if(check){
	sHTML+="<td colspan='5' id='cap' onClick='setCaps(this)' class='keyboardtblenb' style='background:#E9EFF3;padding:5px 7px; width:100px; font-size:85%;'>CAPS LOCK</td>";
	sHTML+="<td colspan='5' id='clr' onClick='setClearAll()' class='keyboardtblenb' style='background:#E9EFF3;padding:5px 7px; width:100px; font-size:85%;'>CLEAR</td>";}
	else{
		sHTML="<table	border='0' class='keyboardtbl' cellspacing='3px' id='keypad' width='100%'><tr><td><img src='/sbijava/retail/images/login_img.png' alt='login image' /></td></tr></table>";
	}
	var codeArray = new Array();
	for (var i=0; i<3; i++){
		codeArray[i]=keyArr[5][i];
	}
	shuffle(codeArray);
	for (var i=0; i<3; i++){
		var code=codeArray[i].charCodeAt(0);
		if(check)
			sHTML+="<td onClick='putChar(" + code + ")' class='keyboardtblenb' >" + codeArray[i] + "</td>";
		else
			//sHTML="<table	border='0' class='keyboardtbl' cellspacing='3px' id='keypad' width='100%'><tr><td><img src='sbijava/retail/images/login_img.png' alt='login image' /></td></tr><tr><td><ul><li>test1</li><li>test2</li></ul></td></tr></table>";
			sHTML='<table border="0" class="keyboardtbl" cellspacing="3px" id="keypad" width="100%">\
				<tr><td><div class="vigilant_banner_wrapper"><img class="vb_img" src="/sbijava/retail/images/login_img_transparent.png" alt="login image" /></div></td></tr>\
				<tr class="vkboard-points"><td class="vigilantContent">\
				<span>Dear Customer,</span>\
						<ul>\
							<li>OTP based login & Mandatory login password change after 180 days introduced for added security.</li>\
							<li>Please do not share OTP/password/user information with anyone. Bank never asks for such information.</li>\
							<li>For better control & security of your account, you can Lock or Unlock your INB access through link "Lock & Unlock User" available at bottom of this Page.</li>\
							<li>Mandatory Profile password change after 365 days introduced for added security</li>\
						</ul></td></tr></table>\
			<style>\
				tr.vkboard-points {background: #2b2f76; color: white;}\
				.vb_img{max-width:100%;}\
				tr.vkboard-points ul li {font-size:12px;}\
				tr.vkboard-points ul {padding:0px 22px;}\
				tr.vkboard-points td span {font-size:12px;padding:5px 8px;display:inline-block;}\
			</style>';
	}
	sHTML+="</tr></table>";
	document.getElementById('kbplaceholder').innerHTML = sHTML;
}
shuffle = function(v){
    for(var j, x, i = v.length; i; j = parseInt(Math.random() * i), x = v[--i], v[i] = v[j], v[j] = x);
    return v;
};
function putChar(code){
	if(fieldObj.value.length < 20){
		var keycode=(code>96 && code<123 && bCaps) ? code-32 : code;
		var text=String.fromCharCode(keycode);
			fieldObj.value += text;
			setCaretTo();
	}
}
function setCaretTo(){
	var pos=fieldObj.value.length;
	if(fieldObj.createTextRange){
		var range=fieldObj.createTextRange();
		range.move('character', pos);
		range.select();
	}else if(fieldObj.selectionStart){
		fieldObj.focus();
		fieldObj.setSelectionRange(pos, pos);
	}
}
function changeCase(){
	var alphabets = document.getElementById('keypad').getElementsByTagName('TD');
	for(var i=0; i<alphabets.length; i++){
		var ch = alphabets[i].innerHTML;
		if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') && ch.length==1){
			alphabets[i].innerHTML = bCaps ? ch.toUpperCase() : ch.toLowerCase();
		}
	}
}
function setCaps(obj){
	bCaps = !(bCaps);
	toggleCap(obj);
	changeCase();
}
function toggleCap(obj){
	 var str = bCaps ? "CAPS LOCK" : "CAPS LOCK";
	 obj.innerHTML = str;
}
function setClearAll(){
	fieldObj.value = "";
	fieldObj.focus();
}

function backspacevk(){
	var profpass;  
	profpass = document.getElementsByName(fieldObj.name+'enc')[0];
	//alert(profpass.value);
	var pfvaluevk = profpass.value;
	var len = pfvaluevk.length;
	if(pfvaluevk.lastIndexOf("|^|") == -1){
		profpass.value = "";
		fieldObj.value = "";
		return;
	}else if(pfvaluevk.lastIndexOf("|^|") == (pfvaluevk.length-3)){
		pfvaluevk = pfvaluevk.substring(0,pfvaluevk.lastIndexOf("|^|"));	
	}else{
		alert("error while doing backspace");
	} 
	
	if(pfvaluevk.lastIndexOf("|^|")!= -1){
		profpass.value = pfvaluevk.substring(0,pfvaluevk.lastIndexOf("|^|") +3);
	}else{
		profpass.value = "";
	}
	
	//alert(profpass.value);
	fieldObj.value = fieldObj.value.substring(0,fieldObj.value.length-1);
}

function vkClear(){
	fieldObj.value="";
	var encField= document.getElementById(fieldObj.name + 'enc');
	if(encField == null ) {
		encField= document.getElementById(fieldObj.id + 'enc');
	}	 
	encField.value="";
	return true;
}


