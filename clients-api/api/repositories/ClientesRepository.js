const BaseRepository = require('./BaseRepository');
const clienteModel = require('./../models/cliente');

//Essa classe utiliza uma variação sintática do JS, seu comportamento é como um objeto comum do JS
class ClienteRepository extends BaseRepository {
    constructor(){
        super(clienteModel);
    }
}

module.exports = ClienteRepository;