const { ClientRepository } = require('../repositories');

async function createClient(clientData) {
  const clientRepository = new ClientRepository();
  const createdClient = await clientRepository.findOrCreate(clientData);

  return createdClient;
}

module.exports = {
  CreateClient: createClient,
};
