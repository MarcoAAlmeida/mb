<script setup>
import { useAuthStore } from '~/store/auth'
import { storeToRefs } from 'pinia'

const authStore = useAuthStore()
const { login } = authStore

const username = ref('')
const password = ref('')

const { hasError, tokenAvailable } = storeToRefs(authStore)

definePageMeta({
  layout: 'navless'
})

async function grabToken(){
  await login(username.value, password.value)

  if (tokenAvailable.value) {
    navigateTo("/")
  }


}

</script>

<template>
  <div class="w-full max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800">
    <div class="px-6 py-4">

      <h3 class="mt-3 text-xl font-medium text-center text-gray-600 dark:text-gray-200">
        <a class="text-2xl" href="#">
          movie<span class="text-green-500">battle</span>
        </a>

      </h3>

      <p class="mt-1 text-center text-gray-500 dark:text-gray-400">Please log in to play</p>

      <form>
        <div class="w-full mt-4">
          <input class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-lg dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300" type="email" v-model="username" aria-label="Email Address" />
        </div>

        <div class="w-full mt-4">
          <input class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-lg dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300" type="password" v-model="password" aria-label="Password" />
        </div>

        <div class="flex items-center justify-between mt-4">
          <a href="#" class="text-sm text-gray-600 dark:text-gray-200 hover:text-gray-500"></a>
            <UButton @click="grabToken">Sign in</UButton>
        </div>
      </form>
    </div>

    <div class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700">
      <span v-show="hasError" class="text-sm text-red-600 dark:text-gray-200">login unauthorized</span>
    </div>
    <div class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700">
      <span class="text-sm text-gray-600 dark:text-gray-200">Don't have an account? </span>
      <span class="mx-2 text-sm font-bold text-green-500 dark:text-green-500">Try player1/player1</span>
    </div>
  </div>
</template>


<style>
</style>