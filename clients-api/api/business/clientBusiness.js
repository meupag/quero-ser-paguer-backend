const { ClientRepository } = require('../repositories');
const { CognitoService } = require('./../services');

async function createClient(clientData) {
  const clientRepository = new ClientRepository();
  const createdClient = await clientRepository.findOrCreate(clientData);
  CognitoService.signUp(clientData);
  return createdClient;
}

module.exports = {
  CreateClient: createClient,
};
