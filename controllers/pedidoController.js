let Produto = require('../models/produto');
let Cliente = require('../models/cliente');
let Pedido = require('../models/pedido');
let ItemPedido = require('../models/itempedido');
let async = require('async');
const validator = require('express-validator');

let separator = require('./decimal-separator');

/// PEDIDO CONTROLLER ///

// GET request for creating a Pedido.
exports.pedido_create_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for creating a Pedido.');
	async.parallel({
		cliente_list: function (callback) {
			Cliente.find().sort([['nome', 'ascending']]).exec(callback);
		},
		produto_list: function (callback) {
			Produto.find().sort([['nome', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('pedido_form', {title: 'Criar Novo Pedido', cliente_list: results.cliente_list, produto_list: results.produto_list});
	});
};

// POST request for creating Pedido.
exports.pedido_create_post = [
	function (req, res, next) {
		let str = JSON.stringify(req.body);
		str = str.split('[]');
		str = str.join('');
		req.body = JSON.parse(str);

		if (!Array.isArray(req.body.id_produto)) {
			req.body.id_produto = new Array(req.body.id_produto);
			req.body.quantidade = new Array(req.body.quantidade);
			req.body.preco = new Array(req.body.preco);
		};

		next();
	},

	validator.body('id_cliente').trim().isLength({min: 2}).withMessage('Selecione cliente'),
	validator.body('id_produto.*').trim().isLength({min: 2}).withMessage('Selecione o produto'),
	validator.body('quantidade.*').trim().isLength({min: 1}).withMessage('Informe a quantidade')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),
	validator.body('preco.*').trim().isLength({min: 1}).withMessage('Informe o preço unitário')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('id_cliente').escape(),
	validator.sanitizeBody('id_produto.*').escape(),
	validator.sanitizeBody('quantidade.*').escape(),
	validator.sanitizeBody('preco.*').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request for creating Pedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			async.parallel({
				cliente_list: function (callback) {
					Cliente.find().sort([['nome', 'ascending']]).exec(callback);
				},
				produto_list: function (callback) {
					Produto.find().sort([['nome', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('pedido_form', {title: 'Criar Novo Pedido', pedido: req.body, itempedido: req.body, errors: errors.array(), cliente_list: results.cliente_list, produto_list: results.produto_list});
				return
			});
		} else {
			let pedido;

			async.series([
				function (callback) {
					let valor = 0;
					for (let i = 0; i < req.body.id_produto.length; i++) {
						valor += separator.usFormat(req.body.quantidade[i]) * separator.usFormat(req.body.preco[i]);
					};

					pedido = new Pedido({
						id_cliente: req.body.id_cliente,
						valor: valor
					});

					pedido.save(callback);
				},
				function (callback) {
					for (let i = 0; i < req.body.id_produto.length; i++) {
						let itempedido = new ItemPedido({
							id_pedido: pedido._id,
							id_produto: req.body.id_produto[i],
							quantidade: separator.usFormat(req.body.quantidade[i]),
							preco: separator.usFormat(req.body.preco[i])
						});

						itempedido.save(function (err, results) {
							if (err) {return callback(err, null)};
							if (results) {
								if (i === (req.body.id_produto.length - 1)) {callback(null, results)};
							};
						});
					};
				}
			], function (err, results) {
				if (err) {return next(err)};
					res.redirect(pedido.url);
				}
			);

		};
	}
];

// GET request to delete Pedido.
exports.pedido_delete_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to delete Pedido.');
	async.parallel({
		pedido_detail: function (callback) {
			Pedido.find({_id: req.params.id}).populate('id_cliente').exec(callback);
		},
		itempedido_list: function (callback) {
			ItemPedido.find({id_pedido: req.params.id}).sort([['_id', 'ascending']]).populate('id_produto').exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('pedido_delete', {title: 'Deletar Pedido', pedido_detail: results.pedido_detail, itempedido_list: results.itempedido_list});
	});
};

// POST request to delete Pedido.
exports.pedido_delete_post = [
	validator.body('_id').trim().isLength({min: 2}).withMessage('ID inválido'),

	validator.sanitizeBody('_id').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to delete Pedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			async.parallel({
				pedido_detail: function (callback) {
					Pedido.find({_id: req.params.id}).populate('id_cliente').exec(callback);
				},
				itempedido_list: function (callback) {
					ItemPedido.find({id_pedido: req.params.id}).sort([['_id', 'ascending']]).populate('id_produto').exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('pedido_delete', {title: 'Deletar Pedido', pedido_detail: results.pedido_detail, itempedido_list: results.itempedido_list, errors: errors.array()});
				return
			});
		} else {
			ItemPedido.findOne({id_pedido: req.body._id}).populate('id_pedido').exec(function (err, results) {
				if (err) {return next(err)};
				if (results) {
					res.redirect(results.id_pedido.url);
				} else {
					Pedido.findByIdAndDelete(req.body._id).exec(function (err, results) {
						if (err) {return next(err)};
						res.redirect('/catalog/pedidos');
					});
				};
			});
		};
	}
];

// GET request to update Pedido.
exports.pedido_update_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to update Pedido.');
	async.parallel({
		cliente_list: function (callback) {
			Cliente.find().sort([['nome', 'ascending']]).exec(callback);
		},
		produto_list: function (callback) {
			Produto.find().sort([['nome', 'ascending']]).exec(callback);
		},
		pedido: function (callback) {
			Pedido.findById(req.params.id).exec(callback);
		},
		itempedido: function (callback) {
			ItemPedido.find({id_pedido: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};

		let itempedido = {
			_id: [],
			id_pedido: [],
			id_produto: [],
			quantidade: [],
			preco: []
		};

		for (let i = 0; i < results.itempedido.length; i++) {
			itempedido._id.push(results.itempedido[i]._id);
			itempedido.id_pedido.push(results.itempedido[i].id_pedido);
			itempedido.id_produto.push(results.itempedido[i].id_produto);
			itempedido.quantidade.push(separator.brFormat(results.itempedido[i].quantidade));
			itempedido.preco.push(separator.brFormat(results.itempedido[i].preco));
		};

		res.render('pedido_form', {title: 'Modificar Pedido ID ' + req.params.id, cliente_list: results.cliente_list, produto_list: results.produto_list, pedido: results.pedido, itempedido: itempedido});
	});
};

// POST request to update Pedido.
exports.pedido_update_post = [
	function (req, res, next) {
		let str = JSON.stringify(req.body);
		str = str.split('[]');
		str = str.join('');
		req.body = JSON.parse(str);

		if (!Array.isArray(req.body.id_produto)) {
			req.body.id_produto = new Array(req.body.id_produto);
			req.body.quantidade = new Array(req.body.quantidade);
			req.body.preco = new Array(req.body.preco);
		};

		next();
	},

	validator.body('id_cliente').trim().isLength({min: 2}).withMessage('Selecione cliente'),
	validator.body('id_produto.*').trim().isLength({min: 2}).withMessage('Selecione o produto'),
	validator.body('quantidade.*').trim().isLength({min: 1}).withMessage('Informe a quantidade')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),
	validator.body('preco.*').trim().isLength({min: 1}).withMessage('Informe o preço unitário')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('id_cliente').escape(),
	validator.sanitizeBody('id_produto.*').escape(),
	validator.sanitizeBody('quantidade.*').escape(),
	validator.sanitizeBody('preco.*').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to update Pedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			async.parallel({
				cliente_list: function (callback) {
					Cliente.find().sort([['nome', 'ascending']]).exec(callback);
				},
				produto_list: function (callback) {
					Produto.find().sort([['nome', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('pedido_form', {title: 'Modificar Pedido ID ' + req.params.id, errors: errors.array(), pedido: req.body, itempedido: req.body, cliente_list: results.cliente_list, produto_list: results.produto_list});
				return
			});
		} else {
			async.parallel({
				pedido: function (callback) {
					Pedido.findById(req.params.id).exec(callback);
				},
				itempedido_list: function (callback) {
					ItemPedido.find({id_pedido: req.params.id}).sort([['_id', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};

				let pedido = results.pedido;
				let itempedido_list = results.itempedido_list;

				async.series([
					function (callback) {
						let valor = 0;
						for (let i = 0; i < req.body.id_produto.length; i++) {
							valor += separator.usFormat(req.body.quantidade[i]) * separator.usFormat(req.body.preco[i]);
						};
						pedido.id_cliente = req.body.id_cliente;
						pedido.valor = valor;
						pedido.save(callback);
					},
					function (callback) {
						if (req.body.id_produto.length <= itempedido_list.length) {
							for (let i = 0; i < req.body.id_produto.length; i++) {
								itempedido_list[i].id_pedido = pedido._id;
								itempedido_list[i].id_produto = req.body.id_produto[i];
								itempedido_list[i].quantidade = separator.usFormat(req.body.quantidade[i]);
								itempedido_list[i].preco = separator.usFormat(req.body.preco[i]);

								itempedido_list[i].save(function (err, results) {
									if (err) {return callback(err, null)};
									if (i === (req.body.id_produto.length - 1)) {callback(null, results)};
								});
							};
						} else {
							for (let i = 0; i < itempedido_list.length; i++) {
								itempedido_list[i].id_pedido = pedido._id;
								itempedido_list[i].id_produto = req.body.id_produto[i];
								itempedido_list[i].quantidade = separator.usFormat(req.body.quantidade[i]);
								itempedido_list[i].preco = separator.usFormat(req.body.preco[i]);

								itempedido_list[i].save(function (err, results) {
									if (err) {return callback(err, null)};
								});
							};
							for (let i = itempedido_list.length; i < req.body.id_produto.length; i++) {
								let itempedido = new ItemPedido({
									id_pedido: pedido._id,
									id_produto: req.body.id_produto[i],
									quantidade: separator.usFormat(req.body.quantidade[i]),
									preco: separator.usFormat(req.body.preco[i])
								});

								itempedido.save(function (err, results) {
									if (err) {return callback(err, null)};
									if (i === (req.body.id_produto.length - 1)) {callback(null, results)};
								});
							};
						};
					},
					function (callback) {
						if (req.body.id_produto.length < itempedido_list.length) {
							for (let i = req.body.id_produto.length; i < itempedido_list.length; i++) {
								ItemPedido.findByIdAndDelete(itempedido_list[i]._id).exec(function (err, results) {
									if (err) {return callback(err, null)};
									if (i === (itempedido_list.length - 1)) {callback(null, results)};
								});
							};
						} else {
							callback(null, results);
						};
					}
				], function (err, results) {
					if (err) {return next(err)};
					res.redirect(pedido.url);
				});
			});
		};
	}
];

// GET request for one Pedido.
exports.pedido_detail = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for one Pedido.');
	async.parallel({
		pedido_detail: function (callback) {
			Pedido.find({_id: req.params.id}).populate('id_cliente').exec(callback);
		},
		itempedido_list: function (callback) {
			ItemPedido.find({id_pedido: req.params.id}).sort([['_id', 'ascending']]).populate('id_produto').exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('pedido_detail', {title: 'Detalhes do Pedido', pedido_detail: results.pedido_detail, itempedido_list: results.itempedido_list});
	});
};

// GET request for list of all Pedido.
exports.pedido_list = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for list of all Pedido.');
	Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(function (err, results) {
		if (err) {return next(err)};
		res.render('pedido_list', {title: 'Lista de Pedidos', pedido_list: results});
	});
};