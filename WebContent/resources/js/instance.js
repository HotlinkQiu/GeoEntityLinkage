$(document).ready(init);

var word;
var sentenceList;

function init()	{
	requestForInstance();
}

function requestForInstance() {
	$.getJSON('instance', function callback(json) {
		parseJSON(json);
		console.log(json);
	});
}

function parseJSON(json) {
	word = json['Word'];
	sentenceList = json['SentenceList'];
	
	constructWordPane(word);
	constructSentenceUl(sentenceList);
	constructSaveButton();
}

function constructWordPane(word) {
	var divElement = $('#wordPane');
	divElement.append($('<h3>'+word+'</h3>'));
	divElement.append($('<hr />'))
}

function constructSentenceUl(sentenceList) {
	var ulElement = $('#sentenceUl');

	for(var i = 0; i < sentenceList.length; i ++) {
		var sentence = sentenceList[i];
		ulElement.append(constructSentenceLi(sentence, i));
	}
}

function constructSentenceLi(sentence, index) {
	var liElement = $('<a>', {
		class : 'list-group-item',
		id : 'li'+index
	});

	var spanElement = $('<span>', {
		html : sentence
	});

	liElement.append(spanElement);
	liElement.append(constructButton(index));
	return liElement;
}

function constructButton(index) {
	var buttonElement = $('<button>', {
		html : 'No',
		class : 'btn btn-danger btn-xs pull-right',
		id : 'button'+index,
	});
	buttonElement.bind('click', function(event) {
		if($(this).hasClass('btn-danger')) {
			$(this).removeClass('btn-danger').addClass('btn-success');
			$(this).html('Yes');
		} else if($(this).hasClass('btn-success')) {
			$(this).removeClass('btn-success').addClass('btn-danger');
			$(this).html('No');
		}
	});
	
	return buttonElement;
}

function constructSaveButton() {
	var divElement = $('#savePane');

	divElement.append($('<hr />'));

	var aElement = $('<a>', {
		html : 'save',
		class : 'pull-right',
		href : '#'
	});
	aElement.bind('click', function(event) {
		var resultList='';
		for(var i = 0; i < sentenceList.length; i ++) {
			if($('#button'+i).hasClass('btn-danger')) {
				resultList += '0;';
			} else if($('#button'+i).hasClass('btn-success')) {
				resultList += '1;';
			}
		}
		$.get('save', {Word : encodeURI(word), ResultList : resultList}, function() {
			window.location.href = './instance.html';
		});
	});
	divElement.append(aElement);
}