using MongoDB.Bson.Serialization.Attributes;
using System.Collections.Generic;

namespace GestaoPedidos.Dominios.Entidades
{
    public class PedidoCollection : List<Pedido>
    {
        public new void Add(Pedido pedido)
        {
            this.Add(pedido);
        }

        public new void AddRange(List<Pedido> pedidos)
        {
            this.AddRange(pedidos);
        }

        public new void Remove(Pedido pedido)
        {
            this.Remove(pedido);
        }

        [BsonIgnore]
        public bool IsErro { get; set; }
        [BsonIgnore]
        public string DescricaoErro { get; set; }
    }
}