<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>

<script setup>
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
  to: '/'
}, {
  label: 'Game #1223',
  icon: 'i-heroicons-puzzle-piece'
}, {
  label: 'Round #12232123',
  icon: 'i-heroicons-question-mark-circle'
}]
</script>



<style>
body {
  @apply antialiased font-sans text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900;
}
</style>