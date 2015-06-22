var Hand = [
    'HighCard',
    'OnePair',
    'TwoPairs',
    'Set',
    'Straight',
    'Flush',
    'FullHouse',
    'Quads',
    'StraightFlush'];

var Move = [
    'Check',
    'Call',
    'Raise',
    'Fold'];

var MovesBefore = [
    'NoRaise',
    'Raise',
    'ReRaise',
    'Cap'];

var Position = [
    'Button',
    'SmallBlind',
    'BigBlind',
    'UnderTheGun',
    'UnderTheGunPlus',
    'CutOff'];

var StackState = [
    'PushFold',
    'Average',
    'Monster'];

var Stage = [
    'Early',
    'Middle',
    'Late'];

var Street = [
    'PreFlop',
    'Flop',
    'Turn',
    'River'];

//////////// Cards ////////////

var allPairs = [
    'AA-KK',
    'QQ',
    'JJ',
    'TT',
    '99',
    '88-77',
    '66-22'
];

var allAcesSuited = [
    'AKs',
    'AQs',
    'AJs',
    'ATs',
    'A9s',
    'A8s-A2s'
];

var aceTen = [
    'AK',
    'AQ',
    'AJ',
    'AT'
];

var kingTen = [
    'KQs',
    'KJs',
    'KTs',
    'KQ',
    'KJ',
    'KT'
];

var queenTen = [
    'QJs',
    'QTs',
    'QJ',
    'QT'
];

var jackTen = [
    'JTs',
    'JT'
];

var straightFlashDro = [
    'T9s-65s',
    'T8s-97s',
    'XXs'
];

var allCards = [allPairs, allAcesSuited, aceTen, kingTen, queenTen, jackTen, straightFlashDro];

var allEnums = {
    Stage: Stage,
    StackState: StackState,
    Street: Street,
    Position: Position,
    MovesBefore: MovesBefore
};


/*
 for (var i in allCards) {
 for (var j in allCards[i]) {
 document.write(allCards[i][j] + '; ');
 }
 document.write('<hr>');
 }
 var checkbox = document.createElement("INPUT");
 checkbox.setAttribute("type", "checkbox");
 checkbox.setAttribute("name", "name");
 checkbox.setAttribute("value", "value");
 document.body.appendChild(checkbox);
 var checkbox = document.getElementsByName('name')[0];
 document.write(checkbox.getAttribute('value'));
 */

function Checkbox(value, checked) {
    var self = this;
    self.value = value;
    self.checked = ko.observable(checked);
    self.available = true;
}

function Event(name, values) {
    var self = this;
    self.name = name;
    self.values = values;
}

function BotViewModel() {
    var self = this;

    getObservableCheckboxArray = function (values) {
        var array = ko.observableArray();
        values.forEach(function (value) {
            array.push(new Checkbox(value, false));
        });
        return array;
    };


    getEnums = function () {
        var local = [];
        for (var name in allEnums) {
            //local.push(name, getObservableCheckboxArray(allEnums[name]));
            local.push(name, allEnums[name]);
        }
        return local;
    };

    self.enums = getEnums();
}

ko.applyBindings(new BotViewModel());