var mongoose = require('mongoose');
let separator = require('../controllers/decimal-separator');

var Schema = mongoose.Schema;

var PedidoSchema = new Schema(
  {
    id_cliente: {type: Schema.Types.ObjectId, ref: 'Cliente', required: true},
    valor: 		{type: Number}
  }
);

// Virtual for pedido's URL
PedidoSchema
.virtual('url')
.get(function () {
  return '/catalog/pedido/' + this._id;
});

// Virtual for pedido's value BR
PedidoSchema
.virtual('valor_br')
.get(function () {
    return separator.brFormat(this.valor);
});

//Export model
module.exports = mongoose.model('Pedido', PedidoSchema);