package com.bancvue.rest.client;

import com.bancvue.rest.Envelope;
import com.bancvue.rest.exception.ConflictException;
import com.bancvue.rest.exception.ConflictingEntityException;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class ResponseHelper {

	public static final String ENTITY_ALREADY_EXISTS = "Entity already exists";
	
	public static <T> boolean hasData(T entity ){
		if(entity instanceof Envelope){
			return ((Envelope)entity).getData() != null;
		}
		return entity != null;
	}
	
	public static <T> void handleSCConflict(Response clientResponse, EntityResolver resolver, Object typeOrGenericType){
		T entity = resolver.getEntity(clientResponse, typeOrGenericType);
		if(hasData(entity)) {
			throw new ConflictingEntityException(ENTITY_ALREADY_EXISTS, entity);
		}
		throw new ConflictException(ENTITY_ALREADY_EXISTS);
	} 
	
	
}
