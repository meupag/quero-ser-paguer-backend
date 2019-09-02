module.exports = (sequelize, DataTypes) => {
  const Cliente = sequelize.define('cliente', {
    id: {
      type: DataTypes.UUID,
      allowNull: false,
      primaryKey: true
    },
    nome: {
      type: DataTypes.STRING,
      allowNull: false
    },
    cpf: {
      type: DataTypes.STRING,
      allowNull: false
    },
    dataNascimento: {
      type: DataTypes.STRING,
      allowNull: false,
      field: 'data_nascimento'
    },
  }, {
      schema: 'clientes',
      tableName: 'cliente'
    });

  return Cliente;
}