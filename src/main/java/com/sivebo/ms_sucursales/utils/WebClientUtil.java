package com.sivebo.ms_sucursales.utils;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WebClientUtil {

	private final WebClient webClient;

    private void validateMicroService(Long id, String name){
                try {
			webClient.get()
				.uri("api/v1/" + name + "/{id}", id)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		log.info(">>> " + name + " {} validada correctamente (WebCliente)", id);

                } catch (WebClientResponseException.NotFound webException) {
			throw new RuntimeException(
				name + "con id " + id + "no existe en el microservicio.");
                } catch (Exception exception) {
			throw new RuntimeException(
				"No se pudo conectar con el microservicio: " + exception.getMessage());
		}
	}
}
