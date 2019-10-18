package com.spring.boot.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.boot.model.Bus;

/**
 * @author RENATO RODRIGUEZ
 *
 */
@Service("ConverterBus")
public class ConverterBus {

	public List<Bus> converterArrayBusToListBus(Bus[] listaBus) {
		List<Bus> nuevaListaBus = new ArrayList<>();
		for (int i = 0; i < listaBus.length; i++) {
			Bus nuevoBus = new Bus();
			nuevoBus.setId(listaBus[i].getId());
			nuevoBus.setPlaca(listaBus[i].getPlaca());
			nuevoBus.setNumeroDisco(listaBus[i].getNumeroDisco());
			nuevoBus.setCapacidad(listaBus[i].getCapacidad());
			nuevoBus.setIdCooperativa(listaBus[i].getIdCooperativa());
			nuevoBus.setEstado(listaBus[i].getEstado());
			nuevoBus.setOrden(i + 1);
			nuevaListaBus.add(nuevoBus);
		}
		return nuevaListaBus;
	}

	public Bus nuevaBus(ResponseEntity<Bus> busRecuperada, Bus busModel) {
		Bus busModifica = new Bus();
		busModifica.setId(busRecuperada.getBody().getId());
		busModifica.setPlaca(busRecuperada.getBody().getPlaca());
		busModifica.setNumeroDisco(busModel.getNumeroDisco());
		busModifica.setCapacidad(busModel.getCapacidad());
		busModifica.setIdCooperativa(busModel.getIdCooperativa());
		busModifica.setEstado(busRecuperada.getBody().getEstado());

		return busModifica;
	}
}