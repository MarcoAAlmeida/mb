// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    devtools: {enabled: true},
    modules: [
        '@nuxt/ui',
        'nuxt-icon',
        '@nuxt/image'
    ],
    image: {
        domains: ['m.media-amazon.com']
    }
})
