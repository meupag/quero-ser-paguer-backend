using GestaoPedidos.Dominios.Entidades;
using MongoDB.Driver;
using System;

namespace GestaoPedidos.Infraestrutura
{
    public class GestaoPedidoContexto
    {
        private static GestaoPedidoContexto instance;
        private IMongoDatabase Database { get; }

        private static readonly object padlock = new object();
        public static string ConnectionString { get; set; }
        public static string DatabaseName { get; set; }
        public static bool IsSSL { get; set; }

        public static GestaoPedidoContexto Instance
        {
            get
            {
                if (instance == null)
                {
                    lock (padlock)
                    {
                        if (instance == null)
                        {
                            instance = new GestaoPedidoContexto();
                        }
                    }
                }
                return instance;
            }
        }

        private GestaoPedidoContexto()
        {
            try
            {
                MongoClientSettings settings = MongoClientSettings.FromUrl(new MongoUrl(ConnectionString));

                if (IsSSL)
                    settings.SslSettings = new SslSettings { EnabledSslProtocols = System.Security.Authentication.SslProtocols.Tls12 };

                var mongoClient = new MongoClient(settings);
                Database = mongoClient.GetDatabase(DatabaseName);
            }
            catch (Exception ex)
            {
                throw new Exception("Não foi possível se conectar ao banco de dados.", ex);
            }
        }

        public IMongoCollection<Pedido> Pedidos
        {
            get { return Database.GetCollection<Pedido>("Pedidos"); }
        }
    }
}