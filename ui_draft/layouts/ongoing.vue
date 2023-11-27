<template>
  <UContainer class="min-h-screen w-full">

    <Navbar :links="links"/>

    <slot/>

    <Footer/>
  </UContainer>

</template>

<script setup>
const links = [{
  label: 'End Game',
  icon: 'i-heroicons-puzzle-piece',
  to: '/gameOver'
}, {
  label: 'logout',
  icon: 'i-heroicons-puzzle-piece',
  to: '/'
}]

</script>