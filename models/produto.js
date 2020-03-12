var mongoose = require('mongoose');
let separator = require('../controllers/decimal-separator');

var Schema = mongoose.Schema;

var ProdutoSchema = new Schema(
  {
    nome:           {type: String, required: true, trim: true, maxlength: 100},
    preco_sugerido: {type: Number, required: true}
  }
);

// Virtual for produto's URL
ProdutoSchema
.virtual('url')
.get(function () {
  return '/catalog/produto/' + this._id;
});

// Virtual for produto's price BR
ProdutoSchema
.virtual('preco_sugerido_br')
.get(function () {
    return separator.brFormat(this.preco_sugerido);
});

//Export model
module.exports = mongoose.model('Produto', ProdutoSchema);