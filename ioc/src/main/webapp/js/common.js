siteStatus();
setInterval(siteStatus, 4000);
var heart = 0

siteStatus2();
setInterval(siteStatus2, 4000);
var heart2 = 0

siteStatus3();
setInterval(siteStatus3, 4000);
var heart2 = 0

siteStatus4();
setInterval(siteStatus4, 4000);
var heart2 = 0


function siteStatus() {

	var heart = 1;

	$.ajax({
		type: 'POST',
		url: './siteCheckServlet',
		data: {
			heart: heart
		},
		success: function(result) {

			$('#statusMessage').html(result);


		}
	});

}

function siteStatus2() {

	var heart = 1;

	$.ajax({
		type: 'POST',
		url: './siteCheckServlet2',
		data: {
			heart: heart
		},
		success: function(result) {

			$('#statusMessage2').html(result);


		}
	});

}

function siteStatus3() {

	var heart = 1;

	$.ajax({
		type: 'POST',
		url: './siteCheckServlet3',
		data: {
			heart: heart
		},
		success: function(result) {

			if (result != 0) {
				$('#statusMessage3').html('CVE : 🤔!!');
				 }
			else {
				$('#statusMessage3').html('CVE : 😴zZ');
				 	}


		}
	});

}

function siteStatus4() {

	var heart = 1;

	$.ajax({
		type: 'POST',
		url: './siteCheckServlet4',
		data: {
			heart: heart
		},
		success: function(result) {

			if (result != 0) {
				$('#statusMessage4').html('IOC :  🤔!!');

			}
			else {
				$('#statusMessage4').html('IOC :  😴zZ');

			}
		}
	});

}
