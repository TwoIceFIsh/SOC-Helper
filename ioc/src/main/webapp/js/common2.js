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
				$('#nokori2').html('');
				$('#nokori').html('CVE 처리 ' + c + '건 남음 -' + parseInt((c * 1) / 60) + '분 소요');
				$('#nokori3').html('');
			}
			if (d != 0) {
				$('#nokori').html('');
				$('#nokori2').html('IOC 처리 ' + d + '건 남음 -' + ((c * 15) / 60) + '분 소요');
				$('#nokori3').html('');
			}
			if (c == 0 && d == 0) {
				$('#nokori').html('');
				$('#nokori2').html('');
				$('#nokori3').html('작업 없음');
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
document.getElementById("j").addEventListener('click', showMailj);
function showMailj() {
	address = 'j'
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
			if (result == 9)
				$('#statusMessage6').html('테스트🥰');


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
			if (result == 9)
				$('#statusMessage6').html('테스트🥰');


		}

	});

}



loglog();
setInterval(loglog, 1000);

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
/*
function mailCheck() {
	
	var id = document.getElementById("id");
	
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
	
}*/

function passwordCheckFunction() {

	var MEMBER_PW_1 = $('#pw1').val();
	var MEMBER_PW_2 = $('#pw2').val();

	if (MEMBER_PW_1 != MEMBER_PW_2 && MEMBER_PW_2 != "") {
		$('#statusMessagepw').html('비밀번호가 달라요');
		$('#statusMessagepw').css('color', 'red');
		$('#z').addClass('vibrate-1');
	} else {
		$('#statusMessagepw').html('');
	}
}

function getCode() {
	var mail = $('#mail').val();

	$.ajax({
		type: 'POST',
		url: './getCodeServlet',
		data: {
			mail: mail
		},
		success: function(result) {

			if (result == 1) {
				$('#statusMessagepw').html('인증코드 발송 완료(3분소요)');
			}
			if (result == 0) {
				$('#statusMessagepw').html('이미 가입되어있습니다.');
			}
			if (result == 2) {
				$('#statusMessagepw').html('메일주소가 회사도메인이 아닙니다.');
			}
			if (result == 4) {
				$('#statusMessagepw').html('인증코드가 이미 발송되었습니다.');
			}
			if (result == 9) {
				$('#statusMessagepw').html('DB Error.');
			}

		}
	});
}


function checkCode() {
	var mail = $('#mail').val();
	var code = $('#code').val();

	$.ajax({
		type: 'POST',
		url: './checkCodeServlet',
		data: {
			mail: mail,
			code: code
		},
		success: function(result) {

			if (result == 10) {
				$('#statusMessagepw').html('인증코드 불일치');
			}
			if (result == 11) {
				$('#statusMessagepw').html('인증코드 일치');
			}
			if (result == 9) {
				$('#statusMessagepw').html('DB Error.');
			}

		}
	});
}




function reg() {
	var mail = $('#mail').val();
	var pw1 = $('#pw1').val();
	var name = $('#name').val();
	var code = $('#code').val();

	$.ajax({
		type: 'POST',
		url: './joinServlet',
		data: {
			mail: mail,
			pw1: pw1,
			name: name,
			code: code
		},
		success: function(result) {
			$('#z').addClass('vibrate-1');
			if (result == 10) {
				$('#statusMessagepw').html('인증코드 불일치');
			}
			if (result == 11) {
				alert('계정생성 완료');
				window.location.href = 'http://222.110.22.168:8080/ioc/login.jsp';
			}
			if (result == 9) {
				$('#statusMessagepw').html('DB Error.');
			}
			if (result == 222) {
				$('#statusMessagepw').html('인증코드를 재발송 해주세요.');
			}
			if (result == 333) {
				$('#statusMessagepw').html('이미 계정이 생성되었습니다.');
			}

		}
	});
}

function enterkey() {
	if (window.event.keyCode == 13) {
		login();
	}
}

function login() {
	var id = $('#id').val();
	var pw = $('#pw').val();

	$.ajax({
		type: 'POST',
		url: './loginServlet',
		data: {
			id: id,
			pw: pw
		},
		success: function(result) {

			var timeOut = function() {
				window.location.href = 'http://222.110.22.168:8080/ioc/main.jsp';
			}


			if (result == 11) {
				$('#big2').addClass('scale-out-center');
				setTimeout(timeOut, 1000);

				//

			}
			if (result == 333) {
				$('#s').addClass('vibrate-1');
				$('#statusMessagepw').html('계정 또는 비밀번호가 틀립니다.');
			}

		}
	});
}


function find() {
	var id = $('#id').val();

	$.ajax({
		type: 'POST',
		url: './findServlet',
		data: {
			id: id
		},
		success: function(result) {

			if (result == 11) {
				alert('비밀번호 발송 완료(3분소요)');
				window.location.href = 'http://222.110.22.168:8080/ioc/main.jsp';
			}
			if (result == 333) {
				$('#statusMessagepw').html('계정이 존재하지 않습니다.');
			}

		}
	});
}






