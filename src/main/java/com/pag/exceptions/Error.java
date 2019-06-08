package com.pag.exceptions;

public class Error {
	
	private String erro;
	private int codigo;
	
	public Error() {
	
	}
	
	public Error(String erro, int codigo) {
		this.codigo = codigo;
		this.erro = erro;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setErro(String erro) {
		this.erro = erro;
	}
	
	public String getErro() {
		return erro;
	}

}
