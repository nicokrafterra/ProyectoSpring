<template>
	<div class="form">
		<form @submit.prevent="handleLogin">
			<h2><i class="fas fa-lock"></i>Inicia Sesión</h2>

			<input type="email" v-model="email" placeholder="Email" required />
			<div v-if="loginError" style="color: red">{{ loginError }}</div>

			<input type="password" v-model="password" placeholder="Password" required />
			<div v-if="loginPasswordError" style="color: red">{{ loginPasswordError }}</div>

			<button type="submit">Inicia Sesión</button>
		</form>
		<div class="register-message">
			<p>¿Aún no tienes cuenta?<RouterLink to="/Registrar" class="register-button">Regístrate</RouterLink>
			</p>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import { mapActions } from 'vuex';

export default {
	data() {
		return {
			email: '',
			password: '',
			loginError: '',
			loginPasswordError: ''
		};
	},
	methods: {
		clearErrors() {
			this.loginError = '';
			this.loginPasswordError = '';
		},
		...mapActions(['loginUsuario']),
		async handleLogin() {
			this.clearErrors();
			try {
				const response = await axios.post('http://localhost:8080/usuarios/iniciarSesion', {
					correoElectronico: this.email,
					contraseña: this.password,
				});
				this.loginUsuario(response.data);
			
				Swal.fire({
					icon: 'success',
					title: '¡Bienvenido!',
					text: 'Tu inicio de sesión ha sido exitoso. Redirigiendo...',
					background: '#e0f7fa', 
					color: '#004d40',
					showConfirmButton: false,
					timer: 3000,
					customClass: {
						popup: 'my-swal-popup', 
					}
				});

				setTimeout(() => {
					this.$router.push('/index');
				}, 3000);

			} catch (error) {
				Swal.fire({
					icon: 'error',
					title: 'Error al iniciar sesión',
					text: 'Verifica tus credenciales e intenta nuevamente.',
					background: '#ffebee',
					color: '#b71c1c',
				});
				this.loginError = 'Error al iniciar sesión. Verifica tus credenciales.';
				console.error('Error en inicio de sesión:', error);
			}
		}
	}
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Poppins:300);

.register-message {
	margin-top: 20px;
	color: #707070;
	font-size: 14px;
}

.register-button {
	cursor: pointer;
	font-size: 14px;
	text-decoration: none;
	color: #04325e;

}

.register-button:hover {
	color: #47a7fb;
	text-decoration: underline;
}


body {
	height: 100vh;
	overflow: hidden;
	font-family: "Poppins";
	background: #0e2941;
}

.login-page {
	width: 400px;
	padding: 8% 0 0;
	margin: auto;
}

.form {
	position: relative;
	z-index: 1;
	background: #FFFFFF;
	max-width: 400px;
	margin: 0 auto;
	margin-top: 90px;
	padding: 45px;
	text-align: center;
	border-radius: 15px;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
}

.form input {
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
	-webkit-transition: all 0.3 ease;
	transition: all 0.3 ease;
	cursor: pointer;
}

.form button:hover,
.form button:active,
.form button:focus {
	background: #0e2941;
}

.form .message {
	margin: 15px 0 0;
	color: #b3b3b3;
	font-size: 12px;
}

.form .message a {
	color: #234666;
	text-decoration: none;
}

/* SweetAlert Custom Style */
.my-swal-popup {
	border-radius: 20px;
	box-shadow: 0px 0px 10px 3px rgba(0, 0, 0, 0.2);
}
</style>
