
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
			} if (E == '3') {
				$('#moduleE').html('🔴');
			}

		}
	});
}