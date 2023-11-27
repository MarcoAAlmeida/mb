<template>
  <UContainer class="container">

    <slot/>

  </UContainer>

</template>

<script setup lang="ts">
</script>

<style scoped>
.container{
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}
</style>