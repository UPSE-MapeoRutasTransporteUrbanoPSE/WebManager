$(document).ready(function(){
	$('.table .eBtn').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href, function(coop, status){
			$('.Frm_GestionCooperativa #id').val(coop.id)
			$('.Frm_GestionCooperativa #nombre').val(coop.nombre)
			$('.Frm_GestionCooperativa #descripcion').val(coop.descripcion)
			$('.Frm_GestionCooperativa #direccion').val(coop.direccion)
			$('.Frm_GestionCooperativa #telefono').val(coop.telefono)
			$('.Frm_GestionCooperativa #email').val(coop.email)
		});
		$('.Frm_GestionCooperativa #miModalCooperativa').modal();
	});
});
