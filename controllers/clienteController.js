let Produto = require('../models/produto');
let Cliente = require('../models/cliente');
let Pedido = require('../models/pedido');
let ItemPedido = require('../models/itempedido');
let async = require('async');
const validator = require('express-validator');
let moment = require('moment');

/// CLIENTE CONTROLLER ///

// GET request for creating Cliente.
exports.cliente_create_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for creating Cliente.');
	res.render('cliente_form', {title: 'Criar Novo Cliente'});
};

// POST request for creating Cliente.
exports.cliente_create_post = [
	validator.body('nome').trim().isLength({min: 2}).withMessage('Informe o nome do cliente'),
	validator.body('cpf').trim().isLength({min: 11, max: 11}).withMessage('CPF inválido').isNumeric().withMessage('Informe apenas números no CPF'),
	validator.body('data_nascimento').isISO8601().isBefore((new Date).toISOString()).withMessage('Data inválida'),

	validator.sanitizeBody('nome').escape(),
	validator.sanitizeBody('cpf').escape(),
	validator.sanitizeBody('data_nascimento').toDate(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request for creating Cliente.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			req.body.data_nascimento = moment.utc(req.body.data_nascimento).format('YYYY-MM-DD');
			res.render('cliente_form', {title: 'Criar Novo Cliente', cliente: req.body, errors: errors.array()});
			return
		} else {
			let cliente = new Cliente({
				nome: req.body.nome,
				cpf: req.body.cpf,
				data_nascimento: req.body.data_nascimento
			});

			Cliente.findOne({cpf: cliente.cpf}).exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.url);
				} else {
					cliente.save(function (err, results) {
						if (err) {return next(err)};
						res.redirect(cliente.url);
					});
				};
			});
		};
	}
];

// GET request to delete Cliente.
exports.cliente_delete_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to delete Cliente.');
	async.parallel({
		cliente_detail: function (callback) {
			Cliente.find({_id: req.params.id}).exec(callback);
		},
		pedido_list: function (callback) {
			Pedido.find({id_cliente: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('cliente_delete', {title: 'Deletar Cliente', cliente_detail: results.cliente_detail, pedido_list: results.pedido_list});
	});
};

// POST request to delete Cliente.
exports.cliente_delete_post = [
	validator.body('_id').trim().isLength({min: 2}).withMessage('ID inválido'),

	validator.sanitizeBody('_id').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to delete Cliente.');
		const errors = validator.validationResult('req');

		if (!errors.isEmpty()) {
			async.parallel({
				cliente_detail: function (callback) {
					Cliente.find({_id: req.params.id}).exec(callback);
				},
				pedido_list: function (callback) {
					Pedido.find({id_cliente: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('cliente_delete', {title: 'Deletar Cliente', cliente_detail: results.cliente_detail, pedido_list: results.pedido_list, errors: errors.array()});
				return
			});
		} else {
			Pedido.findOne({id_cliente: req.body._id}).populate('id_cliente').exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.id_cliente.url);
				} else {
					Cliente.findByIdAndDelete(req.body._id).exec(function (err, results) {
						if (err) {return next(err)};
						res.redirect('/catalog/clientes');
					});
				};
			});
		};
	}
];

// GET request to update Cliente.
exports.cliente_update_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to update Cliente.');
	Cliente.findById(req.params.id).exec(function (err, results) {
		if (err) {return next(err)};
		let cliente = {
			nome: results.nome,
			cpf: results.cpf,
			data_nascimento: moment.utc(results.data_nascimento).format('YYYY-MM-DD')
		};
		res.render('cliente_form', {title: 'Modificar Cliente ' + results.nome, cliente: cliente});
	});
};

// POST request to update Cliente.
exports.cliente_update_post = [
	validator.body('nome').trim().isLength({min: 2}).withMessage('Informe o nome do cliente'),
	validator.body('cpf').trim().isLength({min: 11, max: 11}).withMessage('CPF inválido').isNumeric().withMessage('Informe apenas números no CPF'),
	validator.body('data_nascimento').trim().isISO8601().isBefore((new Date).toISOString()).withMessage('Data inválida'),

	validator.sanitizeBody('nome').escape(),
	validator.sanitizeBody('cpf').escape(),
	validator.sanitizeBody('data_nascimento').toDate(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to update Cliente.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			req.body.data_nascimento = moment.utc(req.body.data_nascimento).format('YYYY-MM-DD');
			res.render('cliente_form', {title: 'Modificar Cliente ' + req.body.nome, cliente: req.body, errors: errors.array()})
			return
		} else {
			let cliente = new Cliente({
				nome: req.body.nome,
				cpf: req.body.cpf,
				data_nascimento: req.body.data_nascimento,
				_id: req.params.id
			});

			Cliente.findOne({cpf: cliente.cpf}).exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.url);
				} else {
					Cliente.findByIdAndUpdate(req.params.id, cliente).exec(function (err, results) {
						if (err) {return next(err)};
						res.redirect(results.url);
					});
				};
			});
		};
	}
];

// GET request for one Cliente.
exports.cliente_detail = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for one Cliente.');
	async.parallel({
		cliente_detail: function (callback) {
			Cliente.find({_id: req.params.id}).exec(callback);
		},
		pedido_list: function (callback) {
			Pedido.find({id_cliente: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('cliente_detail', {title: 'Detalhes do Cliente', cliente_detail: results.cliente_detail, pedido_list: results.pedido_list});
	});
};

// GET request for list of all Clientes.
exports.cliente_list = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for list of all Clientes.');
	Cliente.find().sort([['nome', 'ascending']]).exec(function (err, results) {
		if (err) {return next(err)};
		res.render('cliente_list', {title: 'Lista de Clientes', cliente_list: results});
	});
};