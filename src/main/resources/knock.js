var model;

$.getJSON("/sng/domain.json", function (json) {

    json.cards.forEach(function (array) {
        for (var index in array) {
            array[index] = {name: array[index], move: 'Fold'};
        }
    });

  /*  json.menu.forEach(function (object) {
        for (var index in object.values) {
            object.values[index] = {name: object.values[index], checked: true};
        }
    });*/

    model = json;

    viewModel = ko.mapping.fromJS(json);

    viewModel.check = function () {
        alert("hello");
        /*(ko.unwrap(this.menu)).forEach(function (object) {
            console.log(ko.unwrap(object.name));
        });*/
    };

    ko.applyBindings(viewModel);
});

/*
$(document).ready(function () {
    $("select").on("change", function () {
        var color = $(this.selectedOptions).css("background-color");
        $(this).css("background-color", color);
    });
});*/
