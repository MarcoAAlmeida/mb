<script setup>
  defineProps(['description'])
</script>
<template>
  <div class="flex justify-center card-img">
    <slot />
  </div>
  <div class="flex text-xs text-blue-900 justify-center">
    <p class="small">{{description}}</p>
  </div>
</template>

