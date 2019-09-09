-- A instrução de delete é realizada por função. 
-- O Postgres não oferece suporte para delete em cascade, 
-- a iniciantiva de criar uma funcão parte do principio de que 
-- uma tabela de cliente pode escalar para várias outras, 
-- já pensou se tivermos que dar manutenção na aplicação na instrução de delete
--  toda vez que uma entidade for adicionada !?
CREATE OR REPLACE FUNCTION delete_cliente(idCliente uuid) RETURNS void AS $$

BEGIN 
	-- Deleta os itens do pedido
	delete from pedidos.item_pedido where 
		id_pedido = (select id from pedidos.pedido pp where pp.id_cliente = idCliente);
	-- Deleta os pedidos 
	delete from pedidos.pedido pp  where pp.id_cliente = idCliente;
	-- Deleta o cliente 
	delete from clientes.cliente cc where cc.id_cliente = idCliente;
END;

$$ LANGUAGE plpgsql;