<template>
	<button class="back-button" @click="volver">
		<img src="../assets/IMG/arrow-left.svg" alt="Volver" />
	</button>
	<div class="pqr-form">
		<h2>Crear PQR</h2>
		<form @submit.prevent="enviarPqr">
			<div class="form-group">
				<label for="correo">Correo Electrónico:</label>
				<input type="email" id="correo" v-model="correo" required />
			</div>

			<div class="form-group">
				<label for="descripcion">Descripción:</label>
				<textarea id="descripcion" v-model="descripcion" required></textarea>
			</div>

			<button type="submit">Enviar PQR</button>
		</form>
		<p v-if="mensaje" class="mensaje">{{ mensaje }}</p>
	</div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const correo = ref('');
const descripcion = ref('');
const mensaje = ref('');

const store = useStore();
const router = useRouter();

const usuario = computed(() => store.state.usuario);

const volver = () => {
	router.back();
};

const enviarPqr = async () => {
	if (!usuario.value || !usuario.value.id) {
		mensaje.value = 'El usuario no está definido. Inicia sesión para enviar un PQR.';
		return;
	}

	const pqrData = {
		usuario_id: usuario.value.id,
		correo: correo.value,
		descripcion: descripcion.value,
	};
	console.log('Datos a enviar:', pqrData);

	try {
		const response = await fetch('http://localhost:8000/pqr/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(pqrData),
		});

		if (!response.ok) {
			throw new Error('Error al enviar el PQR');
		}

		const data = await response.json();
		mensaje.value = 'PQR enviado exitosamente';

		// Limpiar los campos después de enviar
		correo.value = '';
		descripcion.value = '';
	} catch (error) {
		console.error('Error:', error);
		mensaje.value = 'No se pudo enviar el PQR. Intenta nuevamente.';
	}
};

</script>

<style scoped>
.pqr-form {
	max-width: 400px;
	margin: auto;
	padding: 20px;
	background: #f2f2f2;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
	margin-bottom: 15px;
}

button {
	padding: 10px 15px;
	background-color: #4caf50;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-weight: bold;
}

button:hover {
	background-color: #45a049;
}

.mensaje {
	margin-top: 20px;
	color: #d9534f;
}
</style>