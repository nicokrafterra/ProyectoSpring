<template>
	<button class="back-button" @click="volver">
		<img src="../assets/IMG/arrow-left.svg" alt="Volver" />
	</button>
	<div id="Container-Perfil">
		<h1>Nombre: {{ usuario.nombre }}</h1>
		<p>Rol: {{ usuario.esAdmin? "Administrador":"Usuario" }}</p>
		<p>Correo:{{ usuario.correoElectronico }}</p>
		<p>Telefono: {{ usuario.numeroCelular }}</p>

	</div>
</template>

<script>
import { mapState } from 'vuex';

export default {
	computed: {
		...mapState(['usuario']), // Mapeamos el estado del usuario
	},
	methods: {
		volver() {
			this.$router.back();
		},
	},
};
</script>

<style scoped>
#Container-Perfil {
	text-align: center;
	padding: 20px;
	margin-top: 100px;
	background-color: #ffffffb2;
	width: 300px;
	justify-self: center;
}

h1 {
	margin: 0;
	font-size: 24px;
	color: #000000;
}

p {
	font-size: 18px;
	color: #000000;
}

button {
	padding: 10px 15px;
	background-color: #4caf50;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-weight: bold;
	transition: background-color 0.3s;
}

button:hover {
	background-color: #ff9800;
}
</style>