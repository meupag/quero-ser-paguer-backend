namespace GestaoPedidos.Models
{
    public class ItemPedidoModel
    {
        public ProdutoModel Produto { get; set; }
        public int Quantidade { get; set; }
        public decimal Preco { get; set; }
    }
}
