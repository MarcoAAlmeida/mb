<template>
  <h2 :id="id" class="flex p-8 m-12 text-4xl text-primary-800 bg-white sm:rounded-lg justify-center">
    <a v-if="generate" :href="`#${id}`" class="content-center">
      <slot />
    </a>
    <slot v-else />
  </h2>
</template>
  
<script setup lang="ts">
  import { useRuntimeConfig } from '#imports'
  defineProps<{ id: string }>()
  const heading = 2
  const { anchorLinks } = useRuntimeConfig().public.content
  const generate = anchorLinks?.depth >= heading && !anchorLinks?.exclude.includes(heading)
</script>

