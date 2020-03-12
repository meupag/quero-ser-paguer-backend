var mongoose = require('mongoose');
let separator = require('../controllers/decimal-separator');

var Schema = mongoose.Schema;

var ItemPedidoSchema = new Schema(
  {
    id_pedido: 	{type: Schema.Types.ObjectId, ref: 'Pedido', required: true},
    id_produto: {type: Schema.Types.ObjectId, ref: 'Produto', required: true},
    quantidade: {type: Number, required: true},
    preco: {type: Number, required: true}
  }
);

// Virtual for itempedido's URL
ItemPedidoSchema
.virtual('url')
.get(function () {
  return '/catalog/itempedido/' + this._id;
});

// Virtual for itempedido's total value
ItemPedidoSchema
.virtual('valor_total')
.get(function () {
  return (this.quantidade * this.preco).toFixed(2);
});

// Virtual for itempedido's price BR
ItemPedidoSchema
.virtual('preco_br')
.get(function () {
    return separator.brFormat(this.preco)
});

// Virtual for itempedido's total value BR
ItemPedidoSchema
.virtual('valor_total_br')
.get(function () {
    return separator.brFormat(this.valor_total)
});

//Export model
module.exports = mongoose.model('ItemPedido', ItemPedidoSchema);