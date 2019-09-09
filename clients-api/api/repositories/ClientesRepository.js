const uuidv4 = require('uuid/v4');
const { Cliente, sequelize } = require('./../models');

class ClienteRepository {
  constructor() {
    this.model = Cliente;
    this.sequelize = sequelize;
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

  deleteClient(idCliente) {
    return this.sequelize.query(`select delete_cliente('${idCliente}')`);
  }
}

module.exports = ClienteRepository;
