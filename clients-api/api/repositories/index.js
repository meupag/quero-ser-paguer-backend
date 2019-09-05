const ClientRepository = require('./ClientesRepository');
const { sequelize } = require('./../models');

module.exports = {
  ClientRepository,
  sequelize,
};
