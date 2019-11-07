using GestaoPedidos.Dominios.Entidades;
using GestaoPedidos.Dominios.Interfaces.Servicos;
using GestaoPedidos.Dominios.Repositorios.Interfaces;
using GestaoPedidos.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace GestaoPedidos.Servico
{
    public class PedidoServico : IPedidoServico
    {
        private IRepositorio Repositorio;

        public PedidoServico(IRepositorio _repositorio)
        {
            this.Repositorio = _repositorio;
        }

        public async Task<PedidoModel> InserirPedido(PedidoModel _pedido)
        {
            Pedido pedido = new Pedido();
            try
            {
                pedido.Repositorio = this.Repositorio;

                pedido.Cliente = new Cliente { 
                    Nome = _pedido.Cliente.Nome,
                    Cpf = _pedido.Cliente.Cpf,
                    DataNascimento = _pedido.Cliente.DataNascimento
                };

                pedido.Itens = new List<ItemPedido>();

                foreach (var item in _pedido.Itens)
                {
                    pedido.Itens.Add(new ItemPedido
                    {
                        Produto = new Produto 
                        {
                            Nome = item.Produto.Nome,
                            PrecoSugerido = item.Produto.PrecoSugerido
                        },
                        Preco = item.Preco,
                        Quantidade = item.Quantidade
                    });
                }

                pedido.IsCamposValidos();

                if (!pedido.IsErro)
                {
                    await pedido.Inserir();
                    _pedido.Id = pedido.RetornaIdComoString();
                }

                _pedido.IsErro = pedido.IsErro;
                _pedido.DescricaoErro = pedido.DescricaoErro;
            }
            catch (Exception ex)
            {
                _pedido.IsErro = true;
                _pedido.DescricaoErro = $"Erro ao tentar inserir pedido:\n{ex.Message}"; ;
            }

            return _pedido;
        }

        public async Task<PedidoModel> AtualizarPedido(PedidoModel _pedido)
        {
            Pedido pedido = new Pedido();
            try
            {
                pedido.AdicionarId(_pedido.Id);
                pedido.Repositorio = this.Repositorio;

                pedido.Cliente = new Cliente
                {
                    Nome = _pedido.Cliente.Nome,
                    Cpf = _pedido.Cliente.Cpf,
                    DataNascimento = _pedido.Cliente.DataNascimento
                };

                pedido.Itens = new List<ItemPedido>();

                foreach (var item in _pedido.Itens)
                {
                    pedido.Itens.Add(new ItemPedido
                    {
                        Produto = new Produto
                        {
                            Nome = item.Produto.Nome,
                            PrecoSugerido = item.Produto.PrecoSugerido
                        },
                        Preco = item.Preco,
                        Quantidade = item.Quantidade
                    });
                }

                pedido.IsCamposValidos();

                if (!pedido.IsErro)
                    await pedido.Atualizar();

                _pedido.IsErro = pedido.IsErro;
                _pedido.DescricaoErro = pedido.DescricaoErro;
            }
            catch (Exception ex)
            {
                _pedido.IsErro = true;
                _pedido.DescricaoErro = $"Erro ao tentar atualizar o pedido:\n{ex.Message}"; ;
            }

            return _pedido;
        }

        public async Task<PedidoModel> ExcluirPedido(string id)
        {
            Pedido pedido = new Pedido();
            PedidoModel model = new PedidoModel();

            try
            {
                pedido.AdicionarId(id);
                pedido.Repositorio = this.Repositorio;
                var retorno = await pedido.Excluir();

                if (!retorno)
                {
                    model.IsErro = true;
                    model.DescricaoErro = "Erro ao tentar excluir pedido!";
                }
            }
            catch(Exception ex)
            {
                model.IsErro = true;
                model.DescricaoErro = $"Erro ao tentar excluir o pedido:\n{ex.Message}"; ;
            }

            return model;
        }

        public async Task<PedidosCollectionModel> ObterTodosPedidos()
        {
            Pedido pedido = new Pedido();
            PedidosCollectionModel _pedidos = new PedidosCollectionModel();

            try
            {
                pedido.Repositorio = this.Repositorio;
                var pedidos = await pedido.ObterTodos();

                foreach (var item in pedidos)
                {
                    PedidoModel _pedido = new PedidoModel();
                    _pedido.Id = item.RetornaIdComoString();
                    _pedido.Cliente = new ClienteModel
                    {
                        Nome = item.Cliente.Nome,
                        Cpf = item.Cliente.Cpf,
                        DataNascimento = item.Cliente.DataNascimento
                    };

                    _pedido.Itens = new List<ItemPedidoModel>();

                    foreach (var itemPedido in item.Itens)
                    {
                        _pedido.Itens.Add(new ItemPedidoModel
                        {
                            Produto = new ProdutoModel
                            {
                                Nome = itemPedido.Produto.Nome,
                                PrecoSugerido = itemPedido.Produto.PrecoSugerido
                            },
                            Preco = itemPedido.Preco,
                            Quantidade = itemPedido.Quantidade
                        });
                    }
                    _pedido.Valor = item.Valor;
                    _pedidos.AddItem(_pedido);
                }
            }
            catch(Exception ex)
            {
                _pedidos.IsErro = true;
                _pedidos.DescricaoErro = $"Erro ao tentar retornar todos os pedido:\n{ex.Message}";
            }

            return _pedidos;
        }

        public async Task<PedidoModel> PesquisarPedido(string id)
        {
            Pedido pedido = new Pedido();
            PedidoModel _pedido = new PedidoModel();
            try
            {
                pedido.AdicionarId(id);
                pedido.Repositorio = this.Repositorio;
                var p = await pedido.PesquisarPorId();

                _pedido.Id = p.RetornaIdComoString();

                _pedido.Cliente = new ClienteModel
                {
                    Nome = p.Cliente.Nome,
                    Cpf = p.Cliente.Cpf,
                    DataNascimento = p.Cliente.DataNascimento
                };

                _pedido.Itens = new List<ItemPedidoModel>();

                foreach (var itemPedido in p.Itens)
                {
                    _pedido.Itens.Add(new ItemPedidoModel
                    {
                        Produto = new ProdutoModel
                        {
                            Nome = itemPedido.Produto.Nome,
                            PrecoSugerido = itemPedido.Produto.PrecoSugerido
                        },
                        Preco = itemPedido.Preco,
                        Quantidade = itemPedido.Quantidade
                    });
                }
                _pedido.Valor = p.Valor;
            }
            catch(Exception ex)
            {
                _pedido.IsErro = true;
                _pedido.DescricaoErro = $"Erro ao tentar retornar pedido por Id:\n{ex.Message}";
            }

            return _pedido;
        }
    }
}