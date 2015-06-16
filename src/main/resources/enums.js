function Enum() {
    for (var i in arguments) {
        this[arguments[i]] = i;
    }
}

var Hand = new Enum(
    'HighCard',
    'OnePair',
    'TwoPairs',
    'Set',
    'Straight',
    'Flush',
    'FullHouse',
    'Quads',
    'StraightFlush');

var Move = new Enum(
    'Check',
    'Call',
    'Raise',
    'Fold');

var MovesBefore = new Enum(
    'NoRaise',
    'Raise',
    'ReRaise',
    'Cap');

var Position = new Enum(
    'Button',
    'SmallBlind',
    'BigBlind',
    'UnderTheGun',
    'UnderTheGunPlus',
    'CutOff');

var StackState = new Enum(
    'PushFold',
    'Average',
    'Monster');

var Stage = [
    'Early',
    'Middle',
    'Late'];

var Street = new Enum(
    'PreFlop',
    'Flop',
    'Turn',
    'River');

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

var allEnums = [Stage, StackState, Street, Position, MovesBefore];

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

function BotViewModel() {
    var self = this;

    getObservableCheckboxArray = function (values) {
        var array = ko.observableArray();
        values.forEach(function (value) {
            array.push(new Checkbox(value, false));
        });
        return array;
    };

    self.stages = getObservableCheckboxArray(Stage);
}

ko.applyBindings(new BotViewModel());