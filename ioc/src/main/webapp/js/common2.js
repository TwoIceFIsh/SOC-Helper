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


var address = "";
document.getElementById("a").addEventListener('click', showMaila);
function showMaila() {
	address = 'a'
	setMail(address)
}
document.getElementById("b").addEventListener('click', showMailb);
function showMailb() {
	address = 'b'
	setMail(address)
}
document.getElementById("c").addEventListener('click', showMailc);
function showMailc() {
	address = 'c'
	setMail(address)
}
document.getElementById("d").addEventListener('click', showMaild);
function showMaild() {
	address = 'd'
	setMail(address)
}
document.getElementById("e").addEventListener('click', showMaile);
function showMaile() {
	address = 'e'
	setMail(address)
}
document.getElementById("f").addEventListener('click', showMailf);
function showMailf() {
	address = 'f'
	setMail(address)
}
document.getElementById("g").addEventListener('click', showMailg);
function showMailg() {
	address = 'g'
	setMail(address)
}
document.getElementById("h").addEventListener('click', showMailh);
function showMailh() {
	address = 'h'
	setMail(address)
}


function setMail(address) {

	$.ajax({
		type: 'POST',
		url: './mailCheckServlet',
		data: {
			address: address
		},
		success: function(result) {

			if (result == 1)
				$('#statusMessage6').html('👍보안관제팀👍');
			if (result == 2)
				$('#statusMessage6').html('부장님😁');
			if (result == 3)
				$('#statusMessage6').html('승환😎');
			if (result == 4)
				$('#statusMessage6').html('명훈😊');
			if (result == 5)
				$('#statusMessage6').html('병호🤑');
			if (result == 6)
				$('#statusMessage6').html('성민😴');
			if (result == 7)
				$('#statusMessage6').html('예지😎');
			if (result == 8)
				$('#statusMessage6').html('형욱😡');

		}

	});

}

heartMail();
function heartMail() {
	address = ""
	$.ajax({
		type: 'POST',
		url: './hearthCheckServlet',
		data: {
			address: address
		},
		success: function(result) {

			if (result == 1)
				$('#statusMessage6').html('보안관제팀👍');
			if (result == 2)
				$('#statusMessage6').html('부장님😁');
			if (result == 3)
				$('#statusMessage6').html('승환😎');
			if (result == 4)
				$('#statusMessage6').html('명훈😊');
			if (result == 5)
				$('#statusMessage6').html('병호🤑');
			if (result == 6)
				$('#statusMessage6').html('성민😴');
			if (result == 7)
				$('#statusMessage6').html('예지😎');
			if (result == 8)
				$('#statusMessage6').html('형욱😡');


		}

	});

}


nokori();
setInterval(nokori, 4000);

function nokori() {
	heart = ""
	$.ajax({
		type: 'POST',
		url: './nokori',
		data: {
			heart: heart
		},
		success: function(result) {

			if (result == 0)
				$('#nokori').html('(작업 없음)');
			if (result > 0)
				$('#nokori').html('(처리 ' + result + '건 남음)' + '</br>' + (result * 5)/   60 + '분 소요');


		}
	});

}

loglog();
setInterval(loglog, 4000);

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

			tmp = '';
			for (i = 0; i < no; i++) {
				tmp = tmp + splitResult[i] + '</br>';
			}
			$('#statusMessage99').html(tmp+'');

		}
	});

}

