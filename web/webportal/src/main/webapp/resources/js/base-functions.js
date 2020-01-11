function toggleMenu (){
		const adminmenu = document.getElementById("adminmenu");
		adminmenu.classList.toggle("show");
		const adminmenuContent = document.getElementById("adminmenuContent");
		adminmenuContent.classList.toggle("show");
}
function switchDisplay(id){
 			var x = document.getElementById(id);
 			if (x.style.display === "none") {
 				x.style.display = "block";
 			} else {
 				x.style.display = "none";
 			}
}

function openModal(id){
	   $('#'+id).modal('show');
}

function closeModal(){
	   $('#'+id).modal('hide');
}

$( document ).ready(function() {
     // Define a function that updates all relative dates defined by <time class='aot-moment-date'>
    var updateAllRelativeDates = function() {
			
            $('time').each(function (i, e) {

                if ($(e).attr("class") == 'aot-moment-date') {
                    var time = moment($(e).attr('datetime')).format('MMM Do YYYY h:mm:ss ');
 $(e).html('<span>' + time + '</span>');

                }
            });
        };
    // Update all dates initially
    updateAllRelativeDates();
    // Register the timer to call it again every minute
    setInterval(updateAllRelativeDates, 6000);

   var navListItems = $('div.setup-panel div a'),
        allWells = $('.setup-content'),
        allNextBtn = $('.nextBtn');

    allWells.hide();

    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
            $item = $(this);
	    if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-success').addClass('btn-default');
            $item.addClass('btn-success');
            allWells.hide();
            $target.show();
            $target.find('input:eq(0)').focus();
        }
    });

    allNextBtn.click(function () {
        var curStep = $(this).closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url']"),
            isValid = true;

        $(".form-group").removeClass("has-error");
        for (var i = 0; i < curInputs.length; i++) {
			console.log(curInputs[i]);
            if (!curInputs[i].validity.valid) {
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }

        if (isValid) nextStepWizard.removeAttr('disabled').trigger('click');
    });

    $('div.setup-panel div a.btn-success').trigger('click');
 });


