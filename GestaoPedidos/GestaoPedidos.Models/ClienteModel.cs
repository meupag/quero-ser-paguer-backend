using System;

namespace GestaoPedidos.Models
{
    public class ClienteModel
    {
        public string Nome { get; set; }
        public string Cpf { get; set; }
        public DateTime? DataNascimento { get; set; }
    }
}