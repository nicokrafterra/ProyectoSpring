<template>
	<button class="back-button" @click="volver">
		<img src="../assets/IMG/arrow-left.svg" alt="Volver" />
	</button>
	<div class="reserva-form">
		<div class="form">
			<h2>Formulario de Reserva</h2>
			<form @submit.prevent="hacerReserva">
				<div class="form-group">
					<label for="fecha">Fecha de Reserva:</label>
					<input type="date" id="fecha" v-model="fecha" :min="minFecha" required />
				</div>

				<div class="form-group">
					<label for="tipo_Reserva">Tipo de Reserva:</label>
					<select id="tipo_Reserva" v-model="tipo_Reserva" required>
						<option disabled value="">Selecciona un tipo de reserva</option>
						<option value="basica">Reserva Básica</option>
						<option value="premium">Reserva Premium</option>
					</select>
				</div>

				<button type="submit">Hacer Reserva</button>
			</form>
			<!-- Botón para ver todas las reservas -->
			<button class="ver-reservas-button" @click="verReservas">Ver todas mis reservas</button>
		</div>
	</div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';

const store = useStore();
const router = useRouter();

const usuario = computed(() => store.state.usuario);

// Campos de la reserva
const fecha = ref('');
const tipo_Reserva = ref('');
const minFecha = ref(new Date().toISOString().split('T')[0]);  // Formato YYYY-MM-DD

// Función para volver atrás
const volver = () => {
	router.back();
};

// Función para hacer la reserva
const hacerReserva = async () => {
	const reservaData = {
		usuario_id: usuario.value.id,
		fecha: fecha.value,
		tipo_Reserva: tipo_Reserva.value,
		pagada: false,
	};

	console.log("Datos enviados:", reservaData);  // Verificar datos en consola

	try {
		const response = await fetch('http://localhost:8000/reservas/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(reservaData),
		});

		if (!response.ok) {
			const errorData = await response.json();  // Intentar obtener mensaje de error detallado
			console.error("Detalles del error:", errorData);
			throw new Error('Error al hacer la reserva');
		}

		const data = await response.json();
		console.log('Reserva exitosa:', data);

		fecha.value = '';
		tipo_Reserva.value = '';

		// Mostrar alerta de SweetAlert
		Swal.fire({
			icon: 'success',
			title: 'Reserva exitosa',
			text: 'Tu reserva ha sido realizada con éxito.',
			confirmButtonColor: '#3085d6',
		});

	} catch (error) {
		console.error('Error:', error);
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'No se pudo realizar la reserva. Intenta nuevamente.',
			confirmButtonColor: '#d33',
		});
	}
};


const verReservas = () => {
	router.push('/ResVer');
};
</script>

<style scoped>
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

.reserva-message {
	margin-top: 20px;
	color: #707070;
	font-size: 14px;
}

.reserva-button {
	cursor: pointer;
	font-size: 14px;
	text-decoration: none;
	color: #04325e;
}

.reserva-button:hover {
	color: #47a7fb;
	text-decoration: underline;
}

body {
	height: 100vh;
	overflow: hidden;
	font-family: "Poppins", sans-serif;
	background: #0e2941;
}

.reserva-form {
	width: 400px;
	margin: auto;
}

.form {
	position: relative;
	z-index: 1;
	background: #FFFFFF;
	max-width: 400px;
	margin: 0 auto;
	padding: 45px;
	text-align: center;
	border-radius: 15px;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
}

.form input,
.form select {
	font-family: "Poppins", sans-serif;
	outline: 0;
	background: #f2f2f2;
	width: 100%;
	border: 0;
	border-radius: 7px;
	margin: 0 0 15px;
	padding: 15px;
	box-sizing: border-box;
	font-size: 14px;
}

.form button {
	font-family: "Poppins", sans-serif;
	text-transform: uppercase;
	outline: 0;
	background: #234666;
	width: 100%;
	border: 0;
	padding: 15px;
	color: #FFFFFF;
	border-radius: 7px;
	font-size: 14px;
	transition: all 0.3s ease;
	cursor: pointer;
}

.form button:hover,
.form button:active,
.form button:focus {
	background: #0e2941;
}

.form .ver-reservas-button {
	margin-top: 20px;
	background-color: #3f51b5;
	color: white;
	font-weight: bold;
	transition: background-color 0.3s;
}

.form .ver-reservas-button:hover {
	background-color: #303f9f;
}
</style>
