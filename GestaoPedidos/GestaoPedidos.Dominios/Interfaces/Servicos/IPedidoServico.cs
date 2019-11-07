using GestaoPedidos.Models;
using System.Threading.Tasks;

namespace GestaoPedidos.Dominios.Interfaces.Servicos
{
    public interface IPedidoServico
    {
        Task<PedidoModel> InserirPedido(PedidoModel pedido);
        Task<PedidoModel> AtualizarPedido(PedidoModel pedido);
        Task<PedidoModel> ExcluirPedido(string id);
        Task<PedidosCollectionModel> ObterTodosPedidos();
        Task<PedidoModel> PesquisarPedido(string id);
    }
}