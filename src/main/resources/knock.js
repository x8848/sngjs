$.getJSON("/sng/domain.json", function (json) {
    viewModel = ko.mapping.fromJS(json);
    ko.applyBindings(viewModel);
});