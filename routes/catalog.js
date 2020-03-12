var express = require('express');
var router = express.Router();

// Require controller modules.
var item_pedido_controller = require('../controllers/itempedidoController');
var cliente_controller = require('../controllers/clienteController');
var pedido_controller = require('../controllers/pedidoController');
var produto_controller = require('../controllers/produtoController');

/// ITEMPEDIDO ROUTES ///

// GET catalog home page.
router.get('/', item_pedido_controller.index);

// GET request for creating a ItemPedido. NOTE This must come before routes that display ItemPedido (uses id).
router.get('/itempedido/create', item_pedido_controller.item_pedido_create_get);

// POST request for creating ItemPedido.
router.post('/itempedido/create', item_pedido_controller.item_pedido_create_post);

// GET request to delete ItemPedido.
router.get('/itempedido/:id/delete', item_pedido_controller.item_pedido_delete_get);

// POST request to delete ItemPedido.
router.post('/itempedido/:id/delete', item_pedido_controller.item_pedido_delete_post);

// GET request to update ItemPedido.
router.get('/itempedido/:id/update', item_pedido_controller.item_pedido_update_get);

// POST request to update ItemPedido.
router.post('/itempedido/:id/update', item_pedido_controller.item_pedido_update_post);

// GET request for one ItemPedido.
router.get('/itempedido/:id', item_pedido_controller.item_pedido_detail);

// GET request for list of all ItemPedido items.
router.get('/itempedidos', item_pedido_controller.item_pedido_list);

/// CLIENTE ROUTES ///

// GET request for creating Cliente. NOTE This must come before route for id (i.e. display cliente).
router.get('/cliente/create', cliente_controller.cliente_create_get);

// POST request for creating Cliente.
router.post('/cliente/create', cliente_controller.cliente_create_post);

// GET request to delete Cliente.
router.get('/cliente/:id/delete', cliente_controller.cliente_delete_get);

// POST request to delete Cliente.
router.post('/cliente/:id/delete', cliente_controller.cliente_delete_post);

// GET request to update Cliente.
router.get('/cliente/:id/update', cliente_controller.cliente_update_get);

// POST request to update Cliente.
router.post('/cliente/:id/update', cliente_controller.cliente_update_post);

// GET request for one Cliente.
router.get('/cliente/:id', cliente_controller.cliente_detail);

// GET request for list of all Clientes.
router.get('/clientes', cliente_controller.cliente_list);

/// PEDIDO ROUTES ///

// GET request for creating a Pedido. NOTE This must come before route that displays Pedido (uses id).
router.get('/pedido/create', pedido_controller.pedido_create_get);

//POST request for creating Pedido.
router.post('/pedido/create', pedido_controller.pedido_create_post);

// GET request to delete Pedido.
router.get('/pedido/:id/delete', pedido_controller.pedido_delete_get);

// POST request to delete Pedido.
router.post('/pedido/:id/delete', pedido_controller.pedido_delete_post);

// GET request to update Pedido.
router.get('/pedido/:id/update', pedido_controller.pedido_update_get);

// POST request to update Pedido.
router.post('/pedido/:id/update', pedido_controller.pedido_update_post);

// GET request for one Pedido.
router.get('/pedido/:id', pedido_controller.pedido_detail);

// GET request for list of all Pedido.
router.get('/pedidos', pedido_controller.pedido_list);

/// PRODUTO ROUTES ///

// GET request for creating a Produto. NOTE This must come before route that displays Produto (uses id).
router.get('/produto/create', produto_controller.produto_create_get);

// POST request for creating Produto. 
router.post('/produto/create', produto_controller.produto_create_post);

// GET request to delete Produto.
router.get('/produto/:id/delete', produto_controller.produto_delete_get);

// POST request to delete Produto.
router.post('/produto/:id/delete', produto_controller.produto_delete_post);

// GET request to update Produto.
router.get('/produto/:id/update', produto_controller.produto_update_get);

// POST request to update Produto.
router.post('/produto/:id/update', produto_controller.produto_update_post);

// GET request for one Produto.
router.get('/produto/:id', produto_controller.produto_detail);

// GET request for list of all Produto.
router.get('/produtos', produto_controller.produto_list);

module.exports = router;