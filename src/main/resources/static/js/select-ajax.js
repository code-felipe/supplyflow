$(document).ready(function() {
	// Patient
	$('.select2-patient').select2({
		width: '100%',
		ajax: {
			url: function() {
				return $(this).data('ajax-url');
			},
			dataType: 'json',
			delay: 250,
			data: function(params) {
				return {
					term: params.term
				};
			},
			processResults: function(data) {
				return {
					results: data.map(function(item) {
						return {
							id: item.id,
							text: item.fullName
						};
					})
				};
			},
			cache: true
		},
		templateResult: item => item.text,
		templateSelection: item => item.text || item.id
	});

	// GenChemical
	$('.select2-genChemical').select2({
		width: '100%',
		ajax: {
			url: function() {
				return $(this).data('ajax-url');
			},
			dataType: 'json',
			delay: 250,
			data: function(params) {
				return {
					term: params.term
				};
			},
			processResults: function(data) {
				return {
					results: data.map(function(item) {
						return {
							id: item.id,
							text: item.testName
						};
					})
				};
			},
			cache: true
		},
		templateResult: item => item.text,
		templateSelection: item => item.text || item.id
	});

	// Salmonella
	$('.select2-salmonella').select2({
		width: '100%',
		ajax: {
			url: function() {
				return $(this).data('ajax-url');
			},
			dataType: 'json',
			delay: 250,
			data: function(params) {
				return {
					term: params.term
				};
			},
			processResults: function(data) {
				return {
					results: data.map(function(item) {
						return {
							id: item.id,
							text: item.testName
						};
					})
				};
			},
			cache: true
		},
		templateResult: item => item.text,
		templateSelection: item => item.text || item.id
	});

	// Salmonella Widal
	$('.select2-salmonella-widal').select2({
		width: '100%',
		ajax: {
			url: function() {
				return $(this).data('ajax-url');
			},
			dataType: 'json',
			delay: 250,
			data: function(params) {
				return {
					term: params.term
				};
			},
			processResults: function(data) {
				return {
					results: data.map(function(item) {
						return {
							id: item.id,
							text: item.testName
						};
					})
				};
			},
			cache: true
		},
		templateResult: item => item.text,
		templateSelection: item => item.text || item.id
	});

	// Dengue
	$('.select2-dengue').select2({
		width: '100%',
		ajax: {
			url: function() {
				return $(this).data('ajax-url');
			},
			dataType: 'json',
			delay: 250,
			data: function(params) {
				return {
					term: params.term
				};
			},
			processResults: function(data) {
				return {
					results: data.map(function(item) {
						return {
							id: item.id,
							text: item.testName
						};
					})
				};
			},
			cache: true
		},
		templateResult: item => item.text,
		templateSelection: item => item.text || item.id
	});
});
