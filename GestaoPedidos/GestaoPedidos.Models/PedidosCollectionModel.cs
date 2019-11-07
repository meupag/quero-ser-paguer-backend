using System.Collections.Generic;

namespace GestaoPedidos.Models
{
    public class PedidosCollectionModel : List<PedidoModel>
    {
        public bool IsErro { get; set; }
        public string DescricaoErro { get; set; }

        public void AddItem(PedidoModel pedido)
        {
            this.Add(pedido);
        }

        public void AddRangeItens(List<PedidoModel> pedidos)
        {
            this.AddRange(pedidos);
        }

        public void RemoveItem(PedidoModel pedido)
        {
            this.Remove(pedido);
        }
    }
}