<script setup>
import { useAuthStore } from '~/store/auth'
import {storeToRefs} from "pinia";

const authStore = useAuthStore()
const { logout } = authStore
const { user } = storeToRefs(authStore)

const props = defineProps({
  links: {type: Object, required: true}
})

const isOpen = ref(false)

function leave(){
  logout()
  navigateTo('/login')

}

</script>

<template>
  <nav class="bg-white dark:bg-gray-800">
    <div class="container px-6 py-4 mx-auto">
      <div class="lg:flex lg:items-center">
        <div class="flex items-center justify-between">
          <NuxtLink class="text-2xl" to="/">
            movie<span class="text-green-500">battle</span>
          </NuxtLink>

          <!-- Mobile menu button -->
          <div class="flex lg:hidden">
            <button v-cloak @click="isOpen = !isOpen" type="button" class="text-gray-500 dark:text-gray-200 hover:text-gray-600 dark:hover:text-gray-400 focus:outline-none focus:text-gray-600 dark:focus:text-gray-400" aria-label="toggle menu">
              <svg v-show="!isOpen" xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M4 8h16M4 16h16" />
              </svg>

              <svg v-show="isOpen" xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
        </div>

        <div v-cloak :class="[isOpen ? 'translate-x-0 opacity-100 ' : 'opacity-0 -translate-x-full']" class="absolute inset-x-0 z-20 flex-1 w-full px-6 py-4 transition-all duration-300 ease-in-out bg-gray-100 dark:bg-gray-800 lg:mt-0 lg:p-0 lg:top-0 lg:relative lg:bg-transparent lg:w-auto lg:opacity-100 lg:translate-x-0 lg:flex lg:items-center lg:justify-between">
          <div class="flex flex-col text-gray-600 dark:text-gray-300 lg:flex lg:px-16 lg:-mx-4 lg:flex-row lg:items-center">
          Hello, {{ user.name }} !!!
          </div>



          <div class="flex justify-center mt-6 lg:flex lg:mt-0 lg:-mx-2">
            <UButton v-for="link in links" :key="link.label" class="m-1" :to="link.to">{{ link.label }} </UButton>
            <UButton key="logout" class="m-1" @click="leave">logout</UButton>
          </div>
        </div>
      </div>
    </div>
  </nav>

</template>


