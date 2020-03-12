let Produto = require('../models/produto');
let Cliente = require('../models/cliente');
let Pedido = require('../models/pedido');
let ItemPedido = require('../models/itempedido');
let async = require('async');
const validator = require('express-validator');

let separator = require('./decimal-separator');

/// ITEMPEDIDO CONTROLLER ///

// GET catalog home page.
exports.index = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET catalog home page.');
	async.parallel({
		produto_count: function (callback) {
			Produto.countDocuments().exec(callback);
		},
		cliente_count: function (callback) {
			Cliente.countDocuments().exec(callback);
		},
		pedido_count: function (callback) {
			Pedido.countDocuments().exec(callback);
		},
		itempedido_count: function (callback) {
			ItemPedido.countDocuments().exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('index', {title: 'Paguer Backend Home', data: results});
	})
};

// GET request for creating a ItemPedido.
exports.item_pedido_create_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for creating a ItemPedido.');
	async.parallel({
		pedido_list: function (callback) {
			Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(callback);
		},
		produto_list: function (callback) {
			Produto.find().sort([['nome', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('itempedido_form', {title: 'Criar Novo Item', pedido_list: results.pedido_list, produto_list: results.produto_list});
	});
};

// POST request for creating ItemPedido.
exports.item_pedido_create_post = [
	validator.body('id_pedido').trim().isLength({min: 2}).withMessage('Selecione o pedido'),
	validator.body('id_produto').trim().isLength({min: 2}).withMessage('Selecione produto'),
	validator.body('quantidade').trim().isLength({min: 1}).withMessage('Informe a quantidade')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),
	validator.body('preco').trim().isLength({min: 1}).withMessage('Informe o preço unitário')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('id_pedido').escape(),
	validator.sanitizeBody('id_produto').escape(),
	validator.sanitizeBody('quantidade').escape(),
	validator.sanitizeBody('preco').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request for creating ItemPedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
				async.parallel({
					pedido_list: function (callback) {
						Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(callback);
					},
					produto_list: function (callback) {
						Produto.find().sort([['nome', 'ascending']]).exec(callback);
					}
				}, function (err, results) {
					if (err) {return next(err)};
					res.render('itempedido_form', {title: 'Criar Novo Item', itempedido: req.body, errors: errors.array(), pedido_list: results.pedido_list, produto_list: results.produto_list});
					return
				});
		} else {
			let itempedido = new ItemPedido({
				id_pedido: req.body.id_pedido,
				id_produto: req.body.id_produto,
				quantidade: separator.usFormat(req.body.quantidade),
				preco: separator.usFormat(req.body.preco)
			});

			itempedido.save(function (err, results) {
				if (err) {return next(err)};
				
				Pedido.findById(itempedido.id_pedido).exec(function (err, results) {
					if (err) {return next(err)};
					results.valor += (itempedido.quantidade * itempedido.preco);

					results.save(function (err, results) {
						if (err) {return next(err)};
						res.redirect(itempedido.url);
					});
				});
			});
		};
	}
];

// GET request to delete ItemPedido.
exports.item_pedido_delete_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to delete ItemPedido.');
	ItemPedido.find({_id: req.params.id}).populate('id_produto').populate({path: 'id_pedido', populate: {path: 'id_cliente'}})
	.exec(function (err, results) {
		if (err) {return next(err)};
		res.render('itempedido_delete', {title: 'Deletar Item', itempedido_detail: results});
	});
};

// POST request to delete ItemPedido.
exports.item_pedido_delete_post = [
	validator.body('_id').trim().isLength({min: 2}).withMessage('ID inválido'),

	validator.sanitizeBody('_id').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to delete ItemPedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			ItemPedido.find({_id: req.params.id}).populate('id_produto').populate({path: 'id_pedido', populate: {path: 'id_cliente'}})
			.exec(function (err, results) {
				if (err) {return next(err)};
				res.render('itempedido_delete', {title: 'Deletar Item', itempedido_detail: results, errors: errors.array()});
				return
			});
		} else {
			let valor = 0;
			ItemPedido.findByIdAndDelete(req.body._id).exec(function (err, results) {
				if (err) {return next(err)};
				valor = results.quantidade * results.preco;

				Pedido.findById(results.id_pedido).exec(function (err, results) {
					if (err) {return next(err)};
					results.valor -= valor;

					if (results.valor !== 0) {
						results.save(function (err, results) {
							if (err) {return next(err)};
							res.redirect(results.url);
						});
					} else {
						Pedido.findByIdAndDelete(results._id).exec(function (err, results) {
							if (err) {return next(err)};
							res.redirect('/catalog/itempedidos');
						})
					};
				});
			});
		};
	}
];

// GET request to update ItemPedido.
exports.item_pedido_update_get = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request to update ItemPedido.');
	async.parallel({
		pedido_list: function (callback) {
			Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(callback);
		},
		produto_list: function (callback) {
			Produto.find().sort([['nome', 'ascending']]).exec(callback);
		},
		itempedido: function (callback) {
			ItemPedido.findById(req.params.id).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		let itempedido = {
			id_pedido: results.itempedido.id_pedido,
			id_produto: results.itempedido.id_produto,
			quantidade: separator.brFormat(results.itempedido.quantidade),
			preco: separator.brFormat(results.itempedido.preco)
		};
		res.render('itempedido_form', {title: 'Modificar Item ID ' + results.itempedido._id, pedido_list: results.pedido_list, produto_list: results.produto_list, itempedido: itempedido});
	});
};

// POST request to update ItemPedido.
exports.item_pedido_update_post = [
	validator.body('id_pedido').trim().isLength({min: 2}).withMessage('Selecione o pedido'),
	validator.body('id_produto').trim().isLength({min: 2}).withMessage('Selecione o produto'),
	validator.body('quantidade').trim().isLength({min: 1}).withMessage('Informe a quantidade')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),
	validator.body('preco').trim().isLength({min: 1}).withMessage('Informe o preço unitário')
	.isCurrency({thousands_separator: '', decimal_separator: ',', allow_decimal: true, require_decimal: false, digits_after_decimal: [2]}).withMessage('Informe apenas números'),

	validator.sanitizeBody('id_pedido').escape(),
	validator.sanitizeBody('id_produto').escape(),
	validator.sanitizeBody('quantidade').escape(),
	validator.sanitizeBody('preco').escape(),

	function (req, res, next) {
		//res.send('NOT IMPLEMENTED: POST request to update ItemPedido.');
		const errors = validator.validationResult(req);

		if (!errors.isEmpty()) {
			async.parallel({
				pedido_list: function (callback) {
					Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(callback);
				},
				produto_list: function (callback) {
					Produto.find().sort([['nome', 'ascending']]).exec(callback);
				}
			}, function (err, results) {
				if (err) {return next(err)};
				res.render('itempedido_form', {title: 'Modificar Item ID ' + req.params.id, pedido_list: results.pedido_list, produto_list: results.produto_list, itempedido: req.body, errors: errors.array()});
				return
			});
		} else {
			let itempedido = new ItemPedido({
				id_pedido: req.body.id_pedido,
				id_produto: req.body.id_produto,
				quantidade: separator.usFormat(req.body.quantidade),
				preco: separator.usFormat(req.body.preco),
				_id: req.params.id
			});

			async.series([
				function (callback) {
					ItemPedido.findById(req.params.id).exec(function (err, results) {
						if (err) {return callback(err, null)};
						let oldItem = results;

						Pedido.findById(oldItem.id_pedido).exec(function (err, results) {
							if (err) {return callback(err, null)};
							results.valor -= oldItem.valor_total;
							results.save(callback);
						});
					});
				},
				function (callback) {
					ItemPedido.findByIdAndUpdate(req.params.id, itempedido).exec(function (err, results) {
						if (err) {return callback(err, null)};

						Pedido.findById(itempedido.id_pedido).exec(function (err, results) {
							if (err) {return callback(err, null)};
							results.valor += itempedido.quantidade * itempedido.preco;
							results.save(callback);
						});
					});
				}
				], function (err, results) {
					if (err) {return next(err)};
					res.redirect(itempedido.url);
			});
		
		};
	}
];

// GET request for one ItemPedido.
exports.item_pedido_detail = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for one ItemPedido.');
	ItemPedido.find({_id: req.params.id}).populate('id_produto').populate({path: 'id_pedido', populate: {path: 'id_cliente'}})
	.exec(function (err, results) {
		if (err) {return next(err)};
		res.render('itempedido_detail', {title: 'Detalhes do Item', itempedido_detail: results});
	});
};

// GET request for list of all ItemPedido items.
exports.item_pedido_list = function (req, res, next) {
	//res.send('NOT IMPLEMENTED: GET request for list of all ItemPedido items.');
	async.parallel({
		pedido_list: function (callback) {
			Pedido.find().populate('id_cliente').sort([['_id', 'ascending']]).exec(callback);
		},
		itempedido_list: function (callback) {
			ItemPedido.find().populate('id_produto').sort([['_id', 'ascending']]).exec(callback);
		}
	}, function (err, results) {
		if (err) {return next(err)};
		res.render('itempedido_list', {title: 'Lista de Itens Solicitados', pedido_list: results.pedido_list, itempedido_list: results.itempedido_list});
	});
};