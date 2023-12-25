<template>
  <p class="text-2xl font-bold text-center m-4">LeaderBoard</p>
  <UTable :rows="people"/>
</template>

<script setup>
definePageMeta({
  layout: 'default',
  middleware: 'login-redirect'
})

const people = [{
  ranking: 1,
  player: 'Player1',
  correct: 95,
  answered: 100
}, {
  ranking: 2,
  player: 'Player3',
  correct: 90,
  answered: 100
}, {
  ranking: 3,
  player: 'Player2',
  correct: 9,
  answered: 10
}
]

</script>

<style>
</style>