const cpf = require('./cliente/CPF');
const phoneValidation = require('./cliente/PhoneValidator');
const cognitoUserexists = require('./cognito/UsernameExists');

module.exports = {
  CPFValidator: cpf,
  PhoneValidator: phoneValidation,
  CognitoUserExists: cognitoUserexists,
};
