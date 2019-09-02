const { cliente } = require('../models');
const { CPFValidator } = require('./../validators');
const { ValidatorsRun } = require('./../helpers');
const uuidv4 = require('uuid/v4');
module.exports = {
    createClient
}

function createClient(req, res) {
    const payload = req.swagger.params.cliente.value;
    const validators = [CPFValidator];
    const errosRequest = ValidatorsRun(payload, validators);
    if(errosRequest.length > 0){
        res.status(403).json(errosRequest);
    }
    res.status(200);
    res.send();
}