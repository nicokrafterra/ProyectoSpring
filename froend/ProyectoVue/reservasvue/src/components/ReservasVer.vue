<template>
	<button class="back-button" @click="volver">
		<img src="../assets/IMG/arrow-left.svg" alt="Volver" />
	</button>
	<div class="reservas-container">
		<h2>Mis Reservas</h2>
		<table v-if="reservas.length > 0">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Tipo de Reserva</th>
					<th>Pagada</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="reserva in reservas" :key="reserva.id">
					<td>{{ reserva.fecha }}</td>
					<td>{{ reserva.tipo_Reserva }}</td>
					<td>{{ reserva.pagada ? "Sí" : "No" }}</td>
				</tr>
			</tbody>
		</table>
		<p v-else>No tienes reservas registradas.</p>
	</div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import axios from 'axios';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const reservas = ref([]);
const router = useRouter();
// Obtener el id del usuario desde el estado de Vuex
const usuario = computed(() => store.state.usuario);


const volver = () => {
	router.back();
};


const obtenerReservas = async () => {
	try {
		const response = await axios.get(`http://localhost:8000/reservas/?usuario_id=${usuario.value.id}`);
		reservas.value = response.data;
	} catch (error) {
		console.error("Error al obtener las reservas:", error);
	}
};

// Cargar las reservas cuando el componente se monta
onMounted(obtenerReservas);
</script>

<style scoped>
.reservas-container {
	width: 80%;
	margin: auto;
	margin-top: 20px;
	background-color: #f8f9fa;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

h2 {
	text-align: center;
	color: #234666;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th,
td {
	padding: 10px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #234666;
	color: #fff;
}

tr:hover {
	background-color: #f1f1f1;
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
