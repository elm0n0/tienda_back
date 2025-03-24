package com.elm0n0.tienda.api.auth.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Telefonos")
@Data
public class TelefonoDao
{
	@Id 
	private String id;
	private String prefijo;
	private Integer numero;
}
