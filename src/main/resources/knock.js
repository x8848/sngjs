$.getJSON("/sng/domain.json", function (json) {

    json.cards.forEach(function (array) {
        for(var index in array){
            array[index]={name: array[index], move: ['----']};
        };
    });

    viewModel = ko.mapping.fromJS(json);
    ko.applyBindings(viewModel);
});