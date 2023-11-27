<script setup>
const links = [{
  label: 'Player1',
  icon: 'i-heroicons-home',
  to: '/'
}, {
  label: 'Game #1223',
  icon: 'i-heroicons-square-3-stack-3d'
}, {
  label: 'Round #1223',
  icon: 'i-heroicons-link'
}]
</script>

<template>
  <UBreadcrumb :links="links" />
</template>