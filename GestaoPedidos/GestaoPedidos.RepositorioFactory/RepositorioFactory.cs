using GestaoPedidos.Dominios.Repositorios.Interfaces;

namespace GestaoPedidos.RepositorioFactory
{
    public class RepositorioFactory
    {
        public static IRepositorio ObterInstanciaRepositorio<T>() where T : IRepositorio, new()
        {
            return new T();
        }
    }
}