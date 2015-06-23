$.getJSON("/sng/domain.json", function (json) {

    json.cards.forEach(function (array) {
        for (var index in array) {
            array[index] = {name: array[index], move: '----'};
        }
    });

    json.menu.forEach(function (object) {
        for (var index in object.values) {
            object.values[index] = {name: object.values[index], checked: true};
        }
    });

    viewModel = ko.mapping.fromJS(json);
    viewModel.checkStreet = function () {
        (ko.unwrap(this.menu)).forEach(function (object) {
            console.log(ko.unwrap(object.name));
        });
    };

    ko.applyBindings(viewModel);
});