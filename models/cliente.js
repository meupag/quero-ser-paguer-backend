var mongoose = require('mongoose');
let moment = require('moment');

var Schema = mongoose.Schema;

var ClienteSchema = new Schema(
  {
    nome: 				{type: String, required: true, trim: true, maxlength: 100},
    cpf: 				{type: String, required: true, trim: true, maxlength: 11},
    data_nascimento: 	{type: Date, required: true, max: Date.now}
  }
);

// Virtual for cliente's URL
ClienteSchema
.virtual('url')
.get(function () {
  return '/catalog/cliente/' + this._id;
});

// Virtual for cliente's birth
ClienteSchema
.virtual('data_nascimento_us')
.get(function () {
  return moment.utc(this.data_nascimento).format('YYYY/MM/DD');
});

// Virtual for cliente's birth
ClienteSchema
.virtual('data_nascimento_br')
.get(function () {
  return moment.utc(this.data_nascimento).format('DD/MM/YYYY');
});

//Export model
module.exports = mongoose.model('Cliente', ClienteSchema);