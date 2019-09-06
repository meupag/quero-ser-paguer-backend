const { ClientRepository, sequelize } = require('../repositories');
const { CognitoService } = require('./../services');

async function createClient(clientData) {
  const clientRepository = new ClientRepository();
  const transaction = await sequelize.transaction();
  try {
    const createdClient = await clientRepository.findOrCreate(clientData, transaction);
    const userIsCreated = createdClient[1];
    const clientReturnDb = createdClient[0];
    if (userIsCreated) {
      await CognitoService.signUp(clientData);
      transaction.commit();
      return clientReturnDb;
    }
    transaction.commit();
    return [
      {
        message: 'Usuário ja existente na base',
        type: 'BUSINESS_VALIDATION',
      },
    ];
  } catch (err) {
    transaction.rollback();
    return [
      {
        message: 'Não foi possível cadastrar o usuário',
        stack: JSON.stringify(err),
        type: 'UNHANDLED_ERROR',
      },
    ];
  }
}

async function getClientById(id) {
  const clientRepository = new ClientRepository();
  const client = await clientRepository.getClientById(id);
  return client;
}

module.exports = {
  CreateClient: createClient,
  GetClientById: getClientById,

  // DeleteClient - Admin
  // GetClients
  // GetSelfClient
  // UpdateClient

};
