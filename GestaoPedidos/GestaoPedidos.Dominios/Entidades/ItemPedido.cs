namespace GestaoPedidos.Dominios.Entidades
{
    public class ItemPedido
    {
        public Produto Produto { get; set; }
        public int Quantidade { get; set; }
        private decimal _preco;
        public decimal Preco 
        {
            get { return _preco; } 
            set
            {
                _preco = value;
                if (_preco == 0)
                    this.Preco = this.Produto.PrecoSugerido;
            }
        }
    }
}