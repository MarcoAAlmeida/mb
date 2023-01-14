export default defineNuxtConfig({
  extends: [
    process.env.TYPOGRAPHY_THEME || '@nuxt-themes/typography',
    '@nuxt-themes/elements'
  ],
  modules: [
    '@nuxt/content',
    '@nuxtjs/tailwindcss',
    '@nuxtjs/color-mode',
    'nuxt-icon',
    'nuxt-config-schema',
    '@nuxthq/studio'
  ],
  colorMode: {
    preference: 'light',
    fallback: 'light',
    classSuffix: ''
  },
  content: {
    
    highlight: {
      theme: {
        default: 'min-dark',
        dark: 'min-dark',
        sepia: 'monokai'
      }
    },
    documentDriven: false,
    markdown:{
      anchorLinks:{
        depth: 2
      }
    }
  },
})
