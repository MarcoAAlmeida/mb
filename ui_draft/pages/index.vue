<script setup>

import { useAuthStore } from '~/store/auth'
import { useHelloStore } from '~/store/hello'
import {storeToRefs} from "pinia";

const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

const helloStore = useHelloStore()
const { getMessage } = helloStore

const { message, hasMessage, hasError } = storeToRefs(helloStore)

definePageMeta({
  middleware: 'login-redirect'
})

await onMounted(() => {
  getMessage()
})

</script>
<template>
   Hello {{ user }} from frontend

  <div v-show="hasError" class="text-red-400">token error</div>
  <div v-show="hasMessage" class="text-green-400">{{ message }} from server</div>
</template>


<style>
</style>