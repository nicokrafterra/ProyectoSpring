import { createRouter, createWebHistory } from "vue-router";
import Perfil from "../Perfil.vue";
import Reserva from "../Reserva.vue";
import Platos from "../Platos.vue";
import Pricipal from "../Pricipal.vue";
import Register from "../Register.vue";
import Singin from "../Singin.vue";
import Pqr from "../Pqr.vue";
import Configuracion from "../Configuracion.vue";
import ReservasVer from "../ReservasVer.vue";

const routes = [
    {
        path: "/index",
        name: "index",
        component: Pricipal
    },
    {
        path: "/Perfil",
        name: "Perfil",
        component: Perfil
    },
    {
        path: "/Reservas",
        name: "Reservas",
        component: Reserva
    },
    {
        path: "/Platos",
        name: "Platos",
        component: Platos
    },
    {
        path: '/',
        redirect: '/Registrar',
        meta: { showHeader: true }, 
    },
    {
        path: '/Registrar',
        name: 'Register',
        component: Register,
        meta: { showHeader: true }, 
    },
    {
        path: '/Iniciar',
        name: 'Iniciar',
        component: Singin,
        meta: { showHeader: true },  
    },
    {
        path: '/pqr',
        name: 'pqr',
        component : Pqr,
    },
    {
        path: '/conf',
        name: 'conf',
        component: Configuracion,
    },
    {
        path: '/ResVer',
        name: 'ResVer',
        component: ReservasVer,
    }
];
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;