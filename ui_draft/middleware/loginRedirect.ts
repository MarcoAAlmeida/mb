import { useAuthStore } from '~/store/auth'
import { storeToRefs } from 'pinia'

export default defineNuxtRouteMiddleware((to, from) => {
    const authStore = useAuthStore()
    const { tokenAvailable } = storeToRefs(authStore)

    if (to.path === '/login') {
        return
    }

    if (!tokenAvailable.value) {
        return '/login'
    }
})