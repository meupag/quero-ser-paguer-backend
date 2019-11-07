using MongoDB.Bson;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace GestaoPedidos.Dominios.Repositorios.Interfaces
{
    public interface IRepositorio
    {
        Task<IEnumerable<object>> ObterTodos();
        Task<object> Pesquisar(ObjectId id);
        Task<bool> Atualizar(object modelo);
        Task<object> Criar(object modelo);
        Task<bool> Excluir(ObjectId id);
    }
}