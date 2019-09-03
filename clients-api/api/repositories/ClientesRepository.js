const uuidv4 = require('uuid/v4');
const BaseRepository = require('./BaseRepository');
const { cliente } = require('./../models');

class ClienteRepository extends BaseRepository {
  constructor() {
    super(cliente);
  }

  findOrCreate(data) {
    if (data.email) {
      const dataInsert = {
        id: uuidv4(),
        ...data,
      };
      return this.model.findOrCreate(
        { where: { email: dataInsert.email }, defaults: { ...dataInsert } },
      );
    }
    return null;
  }
}

module.exports = ClienteRepository;
