const { CognitoService } = require('./../../services');

function validacaoCognito(username) {
  return CognitoService.userExists(username);
}

async function isValid(payload) {
  if (await validacaoCognito(payload.username)) { return { message: 'Username em uso', isValid: false, field: 'Username' }; }
  return { message: 'Ok', isValid: true, field: 'username' };
}

module.exports = isValid;
