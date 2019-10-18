$(document).ready(function(){
	$('.table .eBtn').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href, function(bus, status){
			$('.Frm_GestionBus #id').val(bus.id)
			$('.Frm_GestionBus #placa').val(bus.placa)
			$('.Frm_GestionBus #numeroDisco').val(bus.numeroDisco)
			$('.Frm_GestionBus #capacidad').val(bus.capacidad)
			$('.Frm_GestionBus #idCooperativa').val(bus.idCooperativa)			
		});
		$('.Frm_GestionBus #miModalBus').modal();
	});	
});