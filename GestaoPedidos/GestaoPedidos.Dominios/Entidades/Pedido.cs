using GestaoPedidos.Dominios.Repositorios.Interfaces;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GestaoPedidos.Dominios.Entidades
{
    public class Pedido
    {
        public ObjectId Id { get; set; }
        public Cliente Cliente { get; set; }

        private List<ItemPedido> _itens;
        public List<ItemPedido> Itens 
        { 
            get { return _itens; }
            set 
            {
                _itens = value;
                this.Valor = _itens.Sum(i => i.Preco);
            }
        }
        public decimal Valor { get; set; }
        [BsonIgnore]
        public IRepositorio Repositorio { get; set; }
        [BsonIgnore]
        public bool IsErro { get; private set; }
        [BsonIgnore]
        public string DescricaoErro { get; private set; }

        public void AdicionarId(string _id)
        {
            if (!string.IsNullOrEmpty(_id))
                this.Id = ObjectId.Parse(_id);
        }

        public string RetornaIdComoString()
        {
            if (this.Id != null)
                return this.Id.ToString();

            return string.Empty;
        }

        public void IsCamposValidos()
        {
            string retorno = string.Empty;
            this.IsErro = false;
            this.DescricaoErro = string.Empty;

            if (this.Cliente == null)
                retorno = retorno + "Informe o(s) Cliente(s)\n";

            if (this.Itens == null || this.Itens.Count() == 0)
                retorno = retorno + "Informe o(s) Item(s)\n";

            if (!string.IsNullOrEmpty(retorno))
            {
                this.IsErro = true;
                this.DescricaoErro = retorno;
            }
        }

        public async Task<PedidoCollection> ObterTodos()
        {
            PedidoCollection pedidos = new PedidoCollection();
            try
            {
                var _pedidos = (IEnumerable<Pedido>)await this.Repositorio.ObterTodos();
                pedidos.AddRange(_pedidos);
            }
            catch(Exception ex)
            {
                pedidos.IsErro = true;
                pedidos.DescricaoErro = $"Erro ao retornar todos os pedido:\n{ex.Message}";
            }
            return pedidos;
        }

        public async Task<Pedido> PesquisarPorId()
        {
            try
            {
                return (Pedido)await this.Repositorio.Pesquisar(this.Id);
            }
            catch (Exception ex)
            {
                this.IsErro = true;
                this.DescricaoErro = $"Erro ao pesquisar pedido por Id:\n{ex.Message}";
            }

            return this;
        }

        public async Task<bool> Excluir()
        {
            bool retorno = false;
            try
            {
                retorno = await this.Repositorio.Excluir(this.Id);
                if (!retorno)
                {
                    this.IsErro = true;
                    this.DescricaoErro = "Erro ao tentar excluir o pedido!";
                }
            }
            catch(Exception ex)
            {
                retorno = false;
                this.IsErro = true;
                this.DescricaoErro = $"Erro ao excluir um pedido:\n{ex.Message}";
            }

            return retorno;
        }

        public async Task<Pedido> Inserir()
        {
            try
            {
                return (Pedido)await this.Repositorio.Criar(this);
            }
            catch(Exception ex)
            {
                this.IsErro = true;
                this.DescricaoErro = $"Erro ao tentar inserir um pedido:\n{ex.Message}";
            }

            return this;
        }

        public async Task<Pedido> Atualizar()
        {
            try
            {
                var retorno = await this.Repositorio.Atualizar(this);
                if (!retorno)
                {
                    this.IsErro = true;
                    this.DescricaoErro = "Erro ao tentar atualizar pedido!";
                }
            }
            catch(Exception ex)
            {
                this.IsErro = true;
                this.DescricaoErro = $"Erro ao tentar atualizar pedido:\n{ex.Message}";
            }

            return this;
        }
    }
}