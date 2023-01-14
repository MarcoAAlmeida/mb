<template>
  <h1 :id="id" class="flex p-6 text-4xl text-primary-800 bg-white sm:rounded-lg justify-center">
    <a v-if="generate" :href="`#${id}`">
      <slot />
    </a>
    <slot v-else />
  </h1>
</template>
  
  <script setup lang="ts">
  import { useRuntimeConfig } from '#imports'
  defineProps<{ id: string }>()
  const heading = 1
  const { anchorLinks } = useRuntimeConfig().public.content
  const generate = anchorLinks?.depth >= heading && !anchorLinks?.exclude.includes(heading)
  </script>