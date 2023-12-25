<template>
  <div class="flex justify-center mt-4">
    <UBreadcrumb :links="links" />
  </div>

  <div class="flex text-sm justify-center mt-4">
      <p>game started at Nov 25th 2023 16:45:00</p>
    </div>

    <div class="flex text-sm justify-center">
      <p class="text-sm text-green-500" v-if="errorCount===0">{{ roundCount }} rounds played, 3 mistakes left</p>
      <p class="text-sm text-yellow-500" v-if="errorCount===1">{{ roundCount }} rounds played, 2 mistakes left</p>
      <p class="text-sm animate-pulse text-red-500" v-if="errorCount===2">{{ roundCount }} rounds played, only one mistake left!</p>
    </div>

    <div class="flex justify-center mt-10">
      <h1 class="text-lg font-bold">Which movie is more popular?</h1>
    </div>

    <div class="flex justify-center">
      <div class="grid grid-cols-1 mt-4 md:grid-cols-2 w-full lg:w-2/3">
        <MovieCard :movie="movieLeft" @choose="chooseAny()" :isLeft="true"/>
        <MovieCard :movie="movieRight" @choose="chooseAny()" :isLeft="false"/>
      </div>
    </div>

</template>

<script setup>

definePageMeta({
  layout: 'ongoing',
  middleware: 'login-redirect'
})


const errorCount = ref(0)
const roundCount = ref(0)

const movieLeft = ref({
  title: "Star Wars: Episode IV - A New Hope",
  plot: "An incredibly overrated movie, with nice special effects for its time.",
  poster_url: "https://m.media-amazon.com/images/M/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=320&q=80"
})

const movieRight = ref({
  title: "Star Wars: Episode V - The Empire Strikes Back",
  plot: "better than the first, but still mediocre.",
  poster_url: "https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"
})


const links = [{
  label: 'Player1',
  icon: 'i-heroicons-user-circle',
}, {
  label: 'Game #1223',
  icon: 'i-heroicons-puzzle-piece'
}, {
  label: 'Round #12232123',
  icon: 'i-heroicons-question-mark-circle'
}]

async function chooseAny(){
  errorCount.value = errorCount.value + 1
  roundCount.value = roundCount.value + 1

  if (errorCount.value===3){
    await navigateTo('/gameOver')
  }
}


</script>



<style>
body {
  @apply antialiased font-sans text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900;
}
</style>