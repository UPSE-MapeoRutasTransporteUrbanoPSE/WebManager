package com.spring.boot.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.boot.model.Cooperativa;

/**
 * @author RENATO RODRIGUEZ
 *
 */
@Service("ConverterCooperativa")
public class ConverterCooperativa {

	public List<Cooperativa> converterArrayCooperativaToListCooperativa(Cooperativa[] listaCooperativa) {
		List<Cooperativa> nuevaListaCooperativa = new ArrayList<>();
		for (int i = 0; i < listaCooperativa.length; i++) {
			Cooperativa nuevaCooperativa = new Cooperativa();
			nuevaCooperativa.setId(listaCooperativa[i].getId());
			nuevaCooperativa.setNombre(listaCooperativa[i].getNombre());
			nuevaCooperativa.setDescripcion(listaCooperativa[i].getDescripcion());
			nuevaCooperativa.setDireccion(listaCooperativa[i].getDireccion());
			nuevaCooperativa.setTelefono(listaCooperativa[i].getTelefono());
			nuevaCooperativa.setEmail(listaCooperativa[i].getEmail());
			nuevaCooperativa.setEstado(listaCooperativa[i].getEstado());
			nuevaCooperativa.setOrden(i + 1);
			nuevaListaCooperativa.add(nuevaCooperativa);
		}
		return nuevaListaCooperativa;
	}

	public Cooperativa nuevaCooperativa(ResponseEntity<Cooperativa> cooperativaRecuperada,
			Cooperativa cooperativaModel) {
		Cooperativa CooperativaModifica = new Cooperativa();
		CooperativaModifica.setId(cooperativaRecuperada.getBody().getId());
		CooperativaModifica.setNombre(cooperativaModel.getNombre());
		CooperativaModifica.setDireccion(cooperativaModel.getDireccion());
		CooperativaModifica.setDescripcion(cooperativaModel.getDescripcion());
		CooperativaModifica.setTelefono(cooperativaModel.getTelefono());
		CooperativaModifica.setEmail(cooperativaModel.getEmail());
		CooperativaModifica.setEstado(cooperativaRecuperada.getBody().getEstado());

		return CooperativaModifica;
	}
}
