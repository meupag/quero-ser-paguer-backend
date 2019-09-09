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

async function listClients({ limit, offset }) {
  const clientRepository = new ClientRepository();
  const clientList = await clientRepository.listClients({ limit, offset });
  const totalClients = await clientRepository.countClients();

  return {
    total: totalClients,
    list: clientList,
  };
}

async function deleteClient({ idClient }) {
  const clienteRepository = new ClientRepository();
  try {
    const client = await clienteRepository.getClientById(idClient);
    if (client) {
      const { username } = client;
      if (username) {
        await CognitoService.deleteUser(client);
      }
      clienteRepository.deleteClient(client.id);
    }
  } catch (error) {
    return false;
  }

  return true;
}
module.exports = {
  CreateClient: createClient,
  GetClientById: getClientById,
  ListClients: listClients,
  DeleteClient: deleteClient,

};
