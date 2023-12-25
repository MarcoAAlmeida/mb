<script setup>
const links = [{
  label: 'Leader Board',
  icon: 'i-heroicons-user-circle',
  to: '/leaderBoard'
}, {
  label: 'New Game',
  icon: 'i-heroicons-puzzle-piece',
  to: '/nextRound'
}]
</script>
<template>
  <UContainer class="min-h-screen w-full">

    <Navbar :links="links"/>

    <slot/>

    <Footer/>
  </UContainer>

</template>

