using GestaoPedidos.Dominios.Repositorios.Interfaces;
using GestaoPedidos.Infraestrutura.Repositorios;
using GestaoPedidos.Servico;
using GestaoPedidos.ServicoFactory.Enums;

namespace GestaoPedidos.ServicoFactory
{
    public class ServicoFactory
    {
        public static object ObterInstanciaServico(ServicosEnum opcao)
        {
            switch (opcao)
            {
                case ServicosEnum.Pedido:
                    return InstanciaPedidoServico();
            }

            return null;
        }

        private static object InstanciaPedidoServico()
        {
            IRepositorio repositorio = RepositorioFactory.RepositorioFactory.ObterInstanciaRepositorio<PedidosRepositorio>();
            PedidoServico servico = new PedidoServico(repositorio);

            return servico;
        }
    }
}