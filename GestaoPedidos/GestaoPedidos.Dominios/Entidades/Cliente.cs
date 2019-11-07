using System;

namespace GestaoPedidos.Dominios.Entidades
{
    public class Cliente
    {
        public string Nome { get; set; }
        public string Cpf { get; set; }
        public DateTime? DataNascimento { get; set; }
    }
}