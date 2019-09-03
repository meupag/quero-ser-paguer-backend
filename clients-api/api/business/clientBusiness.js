const { ClientRepository } = require("./../repositories/")

module.exports = {
    CreateClient: createClient
}

async function createClient(clientData) {
    const _rep = new ClientRepository();
    const createdClient = await _rep.findOrCreate(clientData);
    
    return createdClient;
}