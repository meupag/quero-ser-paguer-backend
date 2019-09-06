const uuidv4 = require('uuid/v4');
const BaseRepository = require('./BaseRepository');
const { Cliente } = require('./../models');

class ClienteRepository extends BaseRepository {
  constructor() {
    super(Cliente);
  }

  findOrCreate(data, transaction) {
    if (data.email) {
      const dataInsert = {
        id: uuidv4(),
        ...data,
      };
      return this.model.findOrCreate(
        { where: { cpf: dataInsert.cpf }, defaults: { ...dataInsert }, transaction },
      );
    }
    return null;
  }

  getClientById(id, transaction) {
    return this.model.findOne({ where: { id }, transaction });
  }

  listClients({ limit, offset }) {
    return this.model.findAll({ limit, offset });
  }

  countClients() {
    return this.model.count();
  }
}

module.exports = ClienteRepository;
