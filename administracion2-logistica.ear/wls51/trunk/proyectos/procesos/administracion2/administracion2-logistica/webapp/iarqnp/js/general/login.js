function initElementsLogin() {
	setInitElementsLogin();
}

function setInitElementsLogin() {
	addEventElement("btnSubmitLogin", "click", clickBtnSubmitLogin);
}

function clickBtnSubmitLogin() {
	
	hideElement("divErrorMessageLogin");
	var formNode = $("#formLogin");
	if (formNode) {
		formNode.find("input[name='username']").val(getUpperCaseValueInputText("txtUsername"));
		formNode.find("input[name='password']").val(getUpperCaseValueInputText("txtPassword"));
		formNode.submit();
	}
}

var submitLoginOnKeypressInput = function(event) {
	var keyCode = window.event ? event.keyCode : event.which;
	if (keyCode == 13) {
		clickBtnSubmitLogin();
		return false;
	}
	else {
		return true;
	}
}