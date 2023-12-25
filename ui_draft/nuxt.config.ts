// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    devtools: {enabled: true},
    modules: [
        '@nuxt/ui',
        'nuxt-icon',
        '@pinia/nuxt',
        '@nuxt/image'
    ],
    image: {
        domains: ['m.media-amazon.com']
    },
    runtimeConfig: {
        public: {
            apiBase: process.env.NUXT_PUBLIC_API_BASE || 'http://localhost:8080'
        }
    },


})
