using GestaoPedidos.Dominios.Repositorios.Interfaces;
using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using GestaoPedidos.Dominios.Entidades;

namespace GestaoPedidos.Infraestrutura.Repositorios
{
    public class PedidosRepositorio : IRepositorio
    {
        public async Task<bool> Atualizar(object modelo)
        {
            bool retorno = false;
            var _pedido = (Pedido)modelo;
            Expression<Func<Pedido, bool>> filter = u => u.Id.Equals(_pedido.Id);
            await GestaoPedidoContexto.Instance.Pedidos.ReplaceOneAsync(filter, _pedido);
            retorno = true;
            return retorno;
        }

        public async Task<object> Criar(object modelo)
        {
            var _pedido = (Pedido)modelo;
            await GestaoPedidoContexto.Instance.Pedidos.InsertOneAsync(_pedido);
            return _pedido;
        }

        public async Task<bool> Excluir(ObjectId id)
        {
            bool retorno = false;
            await GestaoPedidoContexto.Instance.Pedidos.DeleteOneAsync(p => p.Id == id);
            retorno = true;
            return retorno;
        }

        public async Task<IEnumerable<object>> ObterTodos()
        {
            return await GestaoPedidoContexto.Instance.Pedidos.Find(p => true).ToListAsync();
        }

        public async Task<object> Pesquisar(ObjectId id)
        {
            return await GestaoPedidoContexto.Instance.Pedidos.Find(p => p.Id == id).FirstOrDefaultAsync();
        }
    }
}