const { ClientRepository } = require('../repositories');
const { CognitoService } = require('./../services');

async function createClient(clientData) {
  const clientRepository = new ClientRepository();
  const createdClient = await clientRepository.findOrCreate(clientData);
  if (createdClient[1]) {
    CognitoService.signUp(clientData);
    return createdClient[0];
  }
  return [
    {
      message: 'Usuário ja existente na base',
      type: 'BUSINESS_VALIDATION',
    },
  ];
}

module.exports = {
  CreateClient: createClient,
};
