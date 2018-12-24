package br.com.tracknme.ws.exception.service;

public class LocationServiceException extends RuntimeException {

	private static final long serialVersionUID = -8690398104473680739L;

	public LocationServiceException(String message) {
		super(message);
	}
}