
//Métodos usados para Dojo
	function preventBackspace(e) {
	    var evt = e || window.event;
	    if (evt) {
	        var keyCode = evt.charCode || evt.keyCode;
	        if (keyCode === 8) {
	            if (evt.preventDefault) {
	                evt.preventDefault();
	            } else {
	                evt.returnValue = false;
	            }
	        }
	    }
	}
	
	
	
	
	function divHide(layer_ref) { 

		var state = 'none'; 
		if (document.all) { //IS IE 4 or 5 (or 6 beta) 
			eval( "document.all." + layer_ref + ".style.display = state"); 
		} 
		if (document.layers) { //IS NETSCAPE 4 or below 
			document.layers[layer_ref].display = state; 
		} 
		if (document.getElementById &&!document.all) { 
			hza = document.getElementById(layer_ref); 
			hza.style.display = state; 
		} 
	} 


	function divShow(layer_ref) { 
		var state = 'block'; 
		
		if (document.all) { //IS IE 4 or 5 (or 6 beta) 
			eval( "document.all." + layer_ref + ".style.display = state"); 
		} 
		if (document.layers) { //IS NETSCAPE 4 or below 
			document.layers[layer_ref].display = state; 
		} 
		if (document.getElementById &&!document.all) { 
			hza = document.getElementById(layer_ref); 
			hza.style.display = state; 
		} 
	} 

	
	
	

	function addCommas(nStr)
	{
		 nStr += '';
		 nStr=removeCommas(nStr);
	    
	    x = nStr.split('.');
	    
	    x1 = x[0];
	    
	    x2 = x.length > 1 ? '.' + x[1] : '';
	    var rgx = /(\d+)(\d{3})/;
	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }
	    
	    return x1 + x2.substring(0,3);
	}

	function removeCommas(nStr){
		nStr += '';
		var i=0;
		x = nStr.split(',') ;
		j = x.length;
		nStr ='';
		
		while( i <j ){
			nStr += x[i];
			i++;
		}
		
		return nStr;
	}

	function isDecimalKey(evt, txt) {
		
		var key = evt.which || evt.keyCode;
	    // Capturar el codigo de la tecla presionada. 
	    // Si no es evt.which, usar evt.keyCode (para IE) 

	    if ((key == 13 || key == 46 || key == 8) || (key >= 48 && key <=57)){
	    // Si la tecla es backspace, enter, punto o digito 
	        if (key == 46 && txt.indexOf('.')!=-1){
	        // Si es el caracter "." comprobar que sea el unico 
	            return false; 
	        } 
	        return true; 
	    } 
	    return false; 
	}
	
	function trim(myString){
		return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
	}
	

	function redondear2decimales(numero)
	{
		var original=parseFloat(numero);
		var result=Math.round(original*100)/100 ;
		return result;
	}

	