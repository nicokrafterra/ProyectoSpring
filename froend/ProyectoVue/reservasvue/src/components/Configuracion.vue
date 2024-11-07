<template>
	<button class="back-button" @click="volver">
		<img src="../assets/IMG/arrow-left.svg" alt="Volver" />
	</button>
	<div class="configuracion">
		<div class="menu">
			<h2>Configuración</h2>
			<button class="config-button" @click="cambiarFoto">Cambiar Foto</button>
			<button class="config-button eliminar-button" @click="eliminarCuenta">Eliminar Cuenta</button>
		</div>
	</div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import axios from 'axios';
import Swal from 'sweetalert2';

const router = useRouter();
const store = useStore();

const volver = () => {
	router.back();
};

const usuario = computed(() => store.state.usuario);

const cambiarFoto = () => {
	Swal.fire({
		icon: 'info',
		title: 'Funcionalidad en desarrollo',
		text: 'Pronto podrás cambiar tu foto de perfil.',
	});
};

const eliminarCuenta = async () => {
	try {
		await axios.delete(`http://localhost:8080/usuarios/${usuario.value.id}`);
		store.commit('logoutUsuario');
		Swal.fire({
			icon: 'success',
			title: 'Cuenta eliminada exitosamente',
			text: 'Redirigiendo...',
			timer: 2000,
			showConfirmButton: false,
		}).then(() => {
			router.push('/'); 
		});
	} catch (error) {
		console.error("Error al eliminar la cuenta:", error);
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'No se pudo eliminar la cuenta.',
		});
	}
};
</script>

<style scoped>

.back-button {
	position: absolute;
	top: 20px;
	left: 20px;
	background: none;
	border: none;
	cursor: pointer;
}

.back-button img {
	width: 24px;
	height: 24px;
}

/* Contenedor central */
.configuracion {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background: #0e2941;
}

/* Menú de configuración */
.menu {
	width: 400px;
	padding: 45px;
	background: #ffffff;
	text-align: center;
	border-radius: 15px;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
}

/* Título */
.menu h2 {
	margin-bottom: 25px;
	color: #04325e;
	font-weight: bold;
	font-size: 1.5em;
}

/* Botón de configuración */
.config-button {
	display: block;
	width: 100%;
	padding: 15px;
	margin: 10px 0;
	color: #ffffff;
	background-color: #234666;
	border: none;
	border-radius: 7px;
	font-size: 14px;
	text-transform: uppercase;
	font-weight: bold;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

/* Hover en botón de configuración */
.config-button:hover {
	background-color: #0e2941;
}

/* Botón de eliminar cuenta */
.eliminar-button {
	background-color: #ff4b4b;
}

.eliminar-button:hover {
	background-color: #ff3333;
}
</style>