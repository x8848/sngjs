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
    'CAP'); // TODO check move ?!

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

var Stage = new Enum(
    'Early',
    'Middle',
    'Late');

var Street = new Enum(
    'PreFlop',
    'Flop',
    'Turn',
    'River');

