function CustomValidation(input) {
    this.invalidities = [];
    this.validityChecks = [];

    this.inputNode = input;

    this.registerListener();
}

CustomValidation.prototype = {
    addInvalidity: function(message) {
        this.invalidities.push(message);
    },
    getInvalidities: function() {
        return this.invalidities.join('. \n');
    },
    checkValidity: function(input) {
        for ( var i = 0; i < this.validityChecks.length; i++ ) {

            var isInvalid = this.validityChecks[i].isInvalid(input);
            if (isInvalid) {
                this.addInvalidity(this.validityChecks[i].invalidityMessage);
            }

            var requirementElement = this.validityChecks[i].element;

            if (requirementElement) {
                if (isInvalid) {
                    requirementElement.classList.add('invalid');
                    requirementElement.classList.remove('valid');
                } else {
                    requirementElement.classList.remove('invalid');
                    requirementElement.classList.add('valid');
                }

            }
        }
    },
    checkInput: function() {

        this.inputNode.CustomValidation.invalidities = [];
        this.checkValidity(this.inputNode);

        if ( this.inputNode.CustomValidation.invalidities.length === 0 && this.inputNode.value !== '' ) {
            this.inputNode.setCustomValidity('');
        } else {
            var message = this.inputNode.CustomValidation.getInvalidities();
            this.inputNode.setCustomValidity(message);
        }
    },
    registerListener: function() { //register the listener here

        var CustomValidation = this;

        this.inputNode.addEventListener('keyup', function() {
            CustomValidation.checkInput();
        });


    }

};


var superheroNameValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 3;
        },

        invalidityMessage: 'This input needs to be at least 3 characters',
        element: document.querySelector('label[for="name"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/[^a-zA-Z0-9]/g);
            return illegalCharacters ? true : false;
        },
        invalidityMessage: 'Only letters and numbers are allowed',
        element: document.querySelector('label[for="name"] .input-requirements li:nth-child(2)')
    }
];

var universeValidatorCheck = [
    {
        isInvalid: function (input) {
            var universe = input.value.toLowerCase()
            if(universe.localeCompare("marvel")){
                if(universe.localeCompare("dc")){
                    return true
                }
            } else {
                return false
            }
        },

        invalidMessage: 'Marvel or Dc Universe',
        element: document.querySelector('label[for="universe"] .input-requirements li:nth-child(1)')
    }
]

var powerValidatorChecks = [
    {
        isInvalid: function (input) {
            return input.value < 0 || input.value > 100;
        },

        invalidMessage: 'Power ranges from 0 to 100',
        element: document.querySelector('label[for="power"] .input-requirements li:nth-child(1)')
    }
]

var descriptionValidatorChecks = [
    {
        isInvalid:function (input) {
            return !input.value.localeCompare("")
        },

        invalidMessage: 'Can not be empty',
        element: document.querySelector('label[for="description"] .input-requirements li:nth-child(1)')
    }
]

var aliveValidatorChecks = [
    {
        isInvalid: function (input) {
            var alive = input.value.toLowerCase()
            if(alive.localeCompare("true")){
                if(alive.localeCompare("false")){
                    return true
                }
            } else {
                return false
            }

        },

        invalidMessage: 'True or false',
        element: document.querySelector('label[for="alive"] .input-requirements li:nth-child(1)')

    }
]


superheroNameInput = document.getElementById('name');
universeInput = document.getElementById('universe');
powerInput = document.getElementById('power');
descriptionInput = document.getElementById('description');
aliveInput = document.getElementById('alive');

superheroNameInput.CustomValidation = new CustomValidation(superheroNameInput);
superheroNameInput.CustomValidation.validityChecks = superheroNameValidityChecks;

universeInput.CustomValidation = new CustomValidation(universeInput);
universeInput.CustomValidation.validityChecks = universeValidatorCheck;

powerInput.CustomValidation = new CustomValidation(powerInput);
powerInput.CustomValidation.validityChecks = powerValidatorChecks;

descriptionInput.CustomValidation = new CustomValidation(descriptionInput);
descriptionInput.CustomValidation.validityChecks = descriptionValidatorChecks;

aliveInput.CustomValidation = new CustomValidation(aliveInput);
aliveInput.CustomValidation.validityChecks = aliveValidatorChecks;

var inputs = document.querySelectorAll('input:not([type="submit"])');

var submit = document.querySelector('input[type="submit"');
var form = document.getElementById('registration');

function validate() {
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].CustomValidation.checkInput();
    }
}

submit.addEventListener('click', validate);
form.addEventListener('submit', validate);