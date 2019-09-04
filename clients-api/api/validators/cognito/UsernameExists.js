const { CognitoService } = require('./../../services');

function validacaoCognito(username) {
  return CognitoService.userExists(username);
}

async function isValid(payload) {
  if (await validacaoCognito(payload.username)) { return { message: 'Nome de usuário existente', isValid: false, field: 'Telefone' }; }
  return { message: 'Ok', isValid: true, field: 'cpf' };
}

module.exports = isValid;
