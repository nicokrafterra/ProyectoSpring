<template>
	<div class="form">
		<form @submit.prevent="handleRegister">
			<h2><i class="fas fa-lock"></i> Registrate</h2>

			<input type="text" v-model="nombre" placeholder="Nombre *" required />
			<div v-if="nombreError" style="color: red">{{ nombreError }}</div>

			<input type="text" v-model="apellido" placeholder="Apellido *" required />
			<div v-if="apellidoError" style="color: red">{{ apellidoError }}</div>

			<input type="email" v-model="email" placeholder="Email *" required />
			<div v-if="emailError" style="color: red">{{ emailError }}</div>

			<input type="password" v-model="password" placeholder="Password *" required />
			<div v-if="passwordError" style="color: red">{{ passwordError }}</div>

			<input type="password" v-model="vpassword" placeholder="Confirmar Password *" required />
			<div v-if="vpasswordError" style="color: red">{{ vpasswordError }}</div>

			<input type="text" v-model="numeroCelular" placeholder="Número de Celular *" required />
			<div v-if="celularError" style="color: red">{{ celularError }}</div>

			<button type="submit">Crear</button>
		</form>
		<div class="register-message">
			<p>¿Ya tienes una cuenta?<RouterLink to="/Iniciar" class="register-button">Inicia Sesión</RouterLink>
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
			nombre: '',
			apellido: '',
			email: '',
			password: '',
			vpassword: '',
			numeroCelular: '',
			nombreError: '',
			apellidoError: '',
			emailError: '',
			passwordError: '',
			vpasswordError: '',
			celularError: '',
		};
	},
	methods: {
		clearErrors() {
			this.nombreError = '';
			this.apellidoError = '';
			this.emailError = '';
			this.passwordError = '';
			this.vpasswordError = '';
			this.celularError = '';
		},
		validateNombre(nombre) {
			const nombreRegex = /^[a-zA-Z\s]+$/;
			return nombreRegex.test(nombre);
		},
		validateEmail(email) {
			const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
			return emailPattern.test(email);
		},
		validatePassword(password) {
			const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d{3,})(?=.*[!@#$%^&*_-])[A-Za-z\d!@#$%^&*_-]{8,}$/;
			return passwordRegex.test(password);
		},
		validateNumeroCelular(numero) {
			return numero.length >= 10;
		},
		...mapActions(['registerUsuario']),
		async handleRegister() {
			this.clearErrors();

			let valid = true;

			if (!this.nombre) {
				this.nombreError = 'El nombre es obligatorio.';
				valid = false;
			} else if (!this.validateNombre(this.nombre) || this.nombre.length <= 1) {
				this.nombreError = 'El nombre debe contener al menos 2 letras.';
				valid = false;
			}

			if (!this.apellido) {
				this.apellidoError = 'El apellido es obligatorio.';
				valid = false;
			} else if (!this.validateNombre(this.apellido) || this.apellido.length <= 1) {
				this.apellidoError = 'El apellido debe contener al menos 2 letras.';
				valid = false;
			}

			if (!this.email) {
				this.emailError = 'El correo electrónico es obligatorio.';
				valid = false;
			} else if (!this.validateEmail(this.email)) {
				this.emailError = 'Por favor, introduce un correo electrónico válido.';
				valid = false;
			}

			if (!this.password) {
				this.passwordError = 'La contraseña es obligatoria.';
				valid = false;
			} else if (!this.validatePassword(this.password)) {
				this.passwordError =
					'La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una minúscula, 3 números y un carácter especial.';
				valid = false;
			}

			if (this.password !== this.vpassword) {
				this.vpasswordError = 'Las contraseñas no coinciden.';
				valid = false;
			}


			if (!this.numeroCelular || !this.validateNumeroCelular(this.numeroCelular)) {
				this.celularError = 'Por favor, introduce un número de celular válido (mínimo 10 dígitos).';
				valid = false;
			}

			if (valid) {

				const esAdmin = this.email.endsWith('.ad');

				try {
					const response = await axios.post('http://localhost:8080/usuarios', {
						nombre: this.nombre,
						apellido: this.apellido,
						correoElectronico: this.email,
						contraseñaUsuario: this.password,
						numeroCelular: this.numeroCelular,
						edad: this.edad,
						esAdmin: esAdmin,
					});

					this.registerUsuario(response.data);
					Swal.fire({
						icon: 'success',
						title: '¡Bienvenido!',
						text: 'Te has registrado exitosamente. Redirigiendo...',
						background: '#e0f7fa',
						color: '#004d40',
						showConfirmButton: false,
						timer: 3000,
						customClass: {
							popup: 'my-swal-popup',
						},
					});

					setTimeout(() => {
						this.$router.push('/index');
					}, 3000);
				} catch (error) {
					console.error('Error al registrar el usuario:', error);
					Swal.fire({
						icon: 'error',
						title: 'Error al registrar',
						text: 'Ocurrió un error al intentar registrar el usuario',
						background: '#ffebee',
						color: '#b71c1c',
					});
				}
			}
		},
	},
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
	height: 100%;
	overflow: hidden;
	font-family: "Poppins";
	background: #0e2941;
	display: flex;
	justify-content: center;
	align-items: center;
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

.my-swal-popup {
	border-radius: 20px;
	box-shadow: 0px 0px 10px 3px rgba(0, 0, 0, 0.2);
}
</style>