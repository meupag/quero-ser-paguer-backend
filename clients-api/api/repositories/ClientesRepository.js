const BaseRepository = require('./BaseRepository');
const { cliente } = require('./../models');
const uuidv4 = require('uuid/v4');
class ClienteRepository extends BaseRepository {
    constructor() {
        super(cliente);
    }

    findOrCreate(data) {
        if (data.email) {
            data.id = uuidv4();
            return this.model.findOrCreate({ where: { email: data.email }, defaults: { ...data } })
        }
        return null;

    }
}

module.exports = ClienteRepository;