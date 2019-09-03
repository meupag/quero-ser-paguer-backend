module.exports = (sequelize, DataTypes) => {
  const Cliente = sequelize.define('cliente', {
    id: {
      type: DataTypes.UUID,
      allowNull: true,
      primaryKey: true,
    },
    nome: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    cpf: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    data_nascimento: {
      type: DataTypes.STRING,
      allowNull: false,
      field: 'data_nascimento',
    },
  }, {
    schema: 'clientes',
    tableName: 'cliente',
  });

  return Cliente;
};
