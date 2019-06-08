package com.pag.modelo;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class PedidoId implements Serializable {
	@DynamoDBIgnore
	private static final long serialVersionUID = 1L;
	private String id;
	private String idCliente;
	
	
	public PedidoId() {
		
	}
	
	public PedidoId(String id, String idCliente) {
		this.id = id;
		this.idCliente = idCliente;
	}
	
	@DynamoDBHashKey()
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@DynamoDBRangeKey
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	
}
