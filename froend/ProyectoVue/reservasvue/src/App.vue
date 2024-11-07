<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import Headerr from './components/Headerr.vue';

const route = useRoute();
</script>



<template>
	
	<Headerr v-if="route.meta.showHeader"/>
	<RouterView />
</template>

<style>

</style>
