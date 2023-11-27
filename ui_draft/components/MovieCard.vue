<template>
  <div class="max-w-xs overflow-hidden bg-white rounded-lg shadow-lg dark:bg-gray-600 mt-4 md:ml-6">
    <div :class="headerStyle">
      <UButton :class="buttonStyle" @click="$emit('choose')">choose this one</UButton>
    </div>
    <div class="px-4 py-2">
      <h1 class="text-sm font-bold text-gray-800 uppercase dark:text-white">{{ movie.title }}</h1>
      <p class="mt-1 text-sm text-gray-600 dark:text-gray-400  h-[6em]">{{ movie.plot }}</p>
    </div>

    <NuxtImg class="object-fill w-full h-full mt-2" :src="movie.poster_url"/>

  </div>

</template>

<script setup>
const props = defineProps({
  isLeft: {type: Boolean, required: true},
  movie: {type: Object, required: true}
})

const headerStyle = computed(() => {
  return props.isLeft ?
      "flex items-center justify-center px-4 py-2 bg-red-900" :
      "flex items-center justify-center px-4 py-2 bg-blue-900"
})

const buttonStyle = computed(() => {
  return props.isLeft ?
      "bg-red-600" :
      "bg-blue-600"
})

</script>

