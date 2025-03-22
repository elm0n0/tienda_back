package com.elm0n0.tienda.auth.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.elm0n0.tienda.auth.enums.TipoDireccion;

import lombok.Data;

@Document(collection = "Direcciones")
@Data
public class DireccionDao 
{	
	@Id 
	private String id;
	private String calle;
	private String numero;
	private String codigoPostal;
	private String ciudad;
	private String provincia;
	private String pais;
	private Double latitud;
	private Double longitud;
	private String referencia;
	private TipoDireccion tipoDireccion;
}
