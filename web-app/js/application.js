if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
	
	
	DropDown.prototype = {
	    initEvents : function() {
	        var obj = this;
	 
	        obj.dd.on('click', function(event){
	            $(this).toggleClass('active');
	            event.stopPropagation();
	        }); 
	    }
	}
}
function DropDown(el) {
    this.dd = el;
    this.initEvents();
}