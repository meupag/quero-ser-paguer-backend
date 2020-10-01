let Produto = require('../models/produto');
let Cliente = require('../models/cliente');
let Pedido = require('../models/pedido');
let ItemPedido = require('../models/itempedido');
let async = require('async');
const validator = require('express-validator');

let separator = require('./decimal-separator');

/// PRODUTO CONTROLLER ///

// GET request for creating a Produto.
exports.produto_create_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for creating a Produto.');
	res.render('produto_form', {title: 'Criar Novo Produto'});
};

// POST request for creating Produto.
exports.produto_create_post = [
	validator.body('nome').trim().isLength({min: 2}).withMessage('Informe a descrição do produto'),
	validator.body('preco_sugerido').trim().isLength({min: 1}).withMessage('Informe o preço sugerido')/*.isNumeric().withMessage('Informe apenas números')*/
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('nome').escape(),
	validator.sanitizeBody('preco_sugerido').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request for creating Produto.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			res.render('produto_form', {title: 'Criar Novo Produto', produto: req.body, errors: errors.array()});
			return;
		} else {
			let produto = new Produto({
				nome: req.body.nome,
				preco_sugerido: separator.usFormat(req.body.preco_sugerido)
			});

			Produto.findOne({nome: produto.nome}).exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.url);
				} else {
					produto.save(function (err, results) {
						if (err) {return next(err)};
						res.redirect(produto.url);
					});
				};
			});
		};
	}
];

// GET request to delete Produto.
exports.produto_delete_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to delete Produto.');
	async.parallel({
		produto_detail: function (callback) {
			Produto.find({_id: req.params.id}).exec(callback);
		},
		itempedido_list: function (callback) {
			ItemPedido.find({id_produto: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('produto_delete', {title: 'Deletar Produto', produto_detail: results.produto_detail, itempedido_list: results.itempedido_list});
	});
};

// POST request to delete Produto.
exports.produto_delete_post = [
	validator.body('_id').trim().isLength({min: 2}).withMessage('ID inválido'),

	validator.sanitizeBody('_id').escape(),
	
	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to delete Produto.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			async.parallel({
				produto_detail: function (callback) {
					Produto.find({_id: req.params.id}).exec(callback);
				},
				itempedido_list: function (callback) {
					ItemPedido.find({id_produto: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('produto_delete', {title: 'Deletar Produto', produto_detail: results.produto_detail, itempedido_list: results.itempedido_list, errors: errors.array()});
				return
			});
		} else {
			ItemPedido.findOne({id_produto: req.body._id}).populate('id_produto').exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.id_produto.url);
				} else {
					Produto.findByIdAndDelete(req.body._id).exec(function (err, results) {
						if (err) {return next(err)};
						res.redirect('/catalog/produtos');
					});
				};
			});
		};
	}
];

// GET request to update Produto.
exports.produto_update_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to update Produto.');
	Produto.findById(req.params.id).exec(function (err, results) {
		if (err) {return next(err)};
		let produto = {
			nome: results.nome,
			preco_sugerido: separator.brFormat(results.preco_sugerido)
		};
		res.render('produto_form', {title: 'Modificar Produto ' + results.nome, produto: produto});
	});
};

// POST request to update Produto.
exports.produto_update_post = [
	validator.body('nome').trim().isLength({min: 2}).withMessage('Informe a descrição do produto'),
	validator.body('preco_sugerido').trim().isLength({min: 1}).withMessage('Informe o preço sugerido')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('nome').escape(),
	validator.sanitizeBody('preco_sugerido').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to update Produto.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			res.render('produto_form', {title: 'Modificar Produto ' + req.body.nome, produto: req.body, errors: errors.array()});
			return
		} else {
			let produto = new Produto({
				nome: req.body.nome,
				preco_sugerido: separator.usFormat(req.body.preco_sugerido),
				_id: req.params.id
			});

			Produto.findByIdAndUpdate(req.params.id, produto).exec(function (err, results) {
				if (err) {return next(err)};
				res.redirect(results.url);
			});
		};
	}
];

// GET request for one Produto.
exports.produto_detail = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for one Produto.');
	async.parallel({
		produto_detail: function (callback) {
			Produto.find({_id: req.params.id}).exec(callback);
		},
		itempedido_list: function (callback) {
			ItemPedido.find({id_produto: req.params.id}).sort([['id_pedido', 'ascending']]).populate('id_pedido').exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('produto_detail', {title: 'Detalhes do Produto', produto_detail: results.produto_detail, itempedido_list: results.itempedido_list});
	});
};

// GET request for list of all Produto.
exports.produto_list = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for list of all Produto.');
	Produto.find().sort([['nome', 'ascending']]).exec(function (err, results) {
		if (err) {return next(err)};
		res.render('produto_list', {title: 'Lista de Produtos', produto_list: results});
	});
};