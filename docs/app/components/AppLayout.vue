<script setup lang="ts">
const { cover } = useAppConfig()
</script>

<template>
  <div class="sm:pt-6 sm:pb-10">
    <Html lang="en" />
    <Meta property="og:image" :content="cover" />
    <Meta name="twitter:card" content="summary_large_image" />
    <slot />
  </div>
</template>

<style lang="postcss">
body {
  @apply bg-primary-50 dark:bg-primary-900;
}
</style>
