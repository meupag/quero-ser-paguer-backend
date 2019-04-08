package com.orders.exception;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
 
@XmlRootElement(name = "error")
@AllArgsConstructor // Automaticamente cria um construtor com todas as propriedades
@NoArgsConstructor // Automaticamente cria um construtor vazio
@Data // Cria toStrings, equals, hashCode, getters e setters
@EqualsAndHashCode
public class ErrorResponse
{ 
    //Erros Genericos
    private String message;
 
    //Erros especificados
    private List<String> details;

}