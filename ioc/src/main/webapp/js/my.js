
moduleCheck();
setInterval(moduleCheck, 4000);
function moduleCheck() {

	$.ajax({
		type: 'POST',
		url: './moduleStatusServlet',
		data: {

		},
		success: function(result) {

			splitResult = result.split('#');

			A = splitResult[0];
			B = splitResult[1];
			C = splitResult[2];
			D = splitResult[3];
			E = splitResult[4];

			if (A == '0') {
				$('#moduleA').html('🟢');
			}
			if (B == '0') {
				$('#moduleB').html('🟢');
			}
			if (C == '0') {
				$('#moduleC').html('🟢');
			}
			if (D == '0') {
				$('#moduleD').html('🟢');
			}
			if (E == '0') {
				$('#moduleE').html('🟢');
			}


			if (A == '1') {
				$('#moduleA').html('🟡');
			}
			if (B == '1') {
				$('#moduleB').html('🟡');
			}
			if (C == '1') {
				$('#moduleC').html('🟡');
			}
			if (D == '1') {
				$('#moduleD').html('🟡');
			} if (E == '1') {
				$('#moduleE').html('🟡');
			}


			if (A == '3') {
				$('#moduleA').html('🔴');
			}
			if (B == '3') {
				$('#moduleB').html('🔴');
			}
			if (C == '3') {
				$('#moduleC').html('🔴');
			}
			if (D == '3') {
				$('#moduleD').html('🔴');
			}
			if (E == '3') {
				$('#moduleE').html('🔵');
			}

		}
	});
}


function enterkey2() {
	if (window.event.keyCode == 13) {
		sendMessage();
	}
}

function sendMessage() {

	var message = $('#sendM').val();

	$.ajax({
		type: 'POST',
		url: './sendMessageServlet',
		data: {
			message: message
		},
		success: function(result) {

			out = result.split('#');


			$('#messageA').html(out[0]);
			var input = document.getElementById('sendM');
			input.value = null;

			var vScrollDown = $("#selecter").prop('scrollHeight');
			$("#selecter").scrollTop(vScrollDown);


		}
	});
}

showMessage();
setInterval(showMessage, 2000);
function showMessage() {

	var message = $('#sendM').val();

	$.ajax({
		type: 'POST',
		url: './showMessageServlet',
		data: {
			message: message
		},
		success: function(result) {

			out = result.split('#');


			$('#messageA').html(out[0]);

			var vScrollDown = $("#selecter").prop('scrollHeight');
			$("#selecter").scrollTop(vScrollDown);


		}
	});
}



aMail();
function aMail() {
	 
	$.ajax({
		type: 'POST',
		url: './showMailStatusServlet',
		data: {
			 
		},
		success: function(result) {
			$('#statusMessage6').html(result);
		}
	});

}

function showMail(name) {
	 
	var name = name;

	$.ajax({
		type: 'POST',
		url: './mailCheckServlet2',
		data: {
			name: name
		},
		success: function(result) {
			$('#statusMessage6').html(result);
		}
	});

}


loglog();
setInterval(loglog, 5000);
function loglog() {
	heart = ""
	$.ajax({
		type: 'POST',
		url: './loglog',
		data: {
			heart: heart
		},
		success: function(result) {

			splitResult = result.split(',');

			no = splitResult.length - 1;
			no2 = splitResult.length;

			tmp = '';
			tmp2 = '';
			for (i = 0; i < no - 1; i++) {

				if ('null' != splitResult[i])
					tmp = tmp + splitResult[i] + '</br>';

			}

			tmp2 = splitResult[no - 1];


			$('#statusMessage99').html(tmp + '');
			$('#statusMessage100').html(tmp2 + '');

		}
	});

}

siteStatus();
setInterval(siteStatus, 4000);
var heart = 0
function siteStatus() {

	var heart = 1;

	$.ajax({
		type: 'POST',
		url: './siteCheckServlet',
		data: {
			heart: heart
		},
		success: function(result) {
			splitResult = result.split('#');
			a = splitResult[0];
			b = splitResult[1];
			c = splitResult[2];
			d = splitResult[3];

			$('#statusMessage').html("" + a);
			$('#statusMessage2').html("" + b);

			if (c != 0) {

				$('#nokori').html('💫'+c + '건 (' + parseInt((c * 1) / 60) + ')분 남음');
				
			} else {
			$('#nokori').html('💤 대기 중');
			}
			if (d != 0) {

				$('#nokori2').html('💫'+d + '건 (' + ((d * 15) / 60) + ')분 남음');
				
			} else {
			$('#nokori2').html('💤 대기 중');
			}
 

		}
	});

} 

