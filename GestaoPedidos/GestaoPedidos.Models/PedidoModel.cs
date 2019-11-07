using System.Collections.Generic;

namespace GestaoPedidos.Models
{
    public class PedidoModel
    {
        public string Id { get; set; }
        public ClienteModel Cliente { get; set; }
        public List<ItemPedidoModel> Itens { get; set; }
        public decimal Valor { get; set; }
        public bool IsErro { get; set; }
        public string DescricaoErro { get; set; }
    }
}