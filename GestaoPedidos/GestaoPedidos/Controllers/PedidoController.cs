using System.Threading.Tasks;
using GestaoPedidos.Dominios.Interfaces.Servicos;
using GestaoPedidos.Models;
using Microsoft.AspNetCore.Mvc;

namespace GestaoPedidos.Api.Controllers
{
    //[Route("api/[controller]")]
    [ApiController]
    public class PedidoController : ControllerBase
    {
        IPedidoServico servico = ServicoFactory.ServicoFactory.ObterInstanciaServico(ServicoFactory.Enums.ServicosEnum.Pedido) as IPedidoServico;

        [Route("api/Pedido")]
        [HttpGet]
        public async Task<PedidosCollectionModel> Get()
        {
            return await servico.ObterTodosPedidos();
        }

        [Route("api/Pedido/PesquisarPedido/{id}")]
        [HttpGet("{id}")]
        public async Task<PedidoModel> PesquisarPedido(string id)
        {
            return await servico.PesquisarPedido(id);
        }

        [Route("api/Pedido/ExcluirPedido/{id}")]
        [HttpDelete("{id}")]
        public async Task<PedidoModel> ExcluirPedido(string id)
        {
            return await servico.ExcluirPedido(id);
        }

        [Route("api/Pedido/InserirPedido")]
        [HttpPost]
        public async Task<PedidoModel> InserirPedido([FromBody]PedidoModel pedido)
        {
            return await servico.InserirPedido(pedido);
        }

        [Route("api/Pedido/AlterarPedido/{id}")]
        [HttpPut("{id}")]
        public async Task<PedidoModel> AlterarPedido(string id, [FromBody]PedidoModel pedido)
        {
            pedido.Id = id;
            return await servico.AtualizarPedido(pedido);
        }
    }
}