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
    ko.applyBindings(viewModel);

    viewModel.checkStreet = function () {
        alert(this.menu); // TODO: doesn't get data from model
    };
});