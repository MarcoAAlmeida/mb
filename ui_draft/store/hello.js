import { defineStore, storeToRefs } from 'pinia'
import { useFetch } from "nuxt/app"

import { useAuthStore } from '~/store/auth'


export const useHelloStore = defineStore({
    id: 'hello',
    state: () => ({
        message: null,
        error: null
    }),
    getters: {
        hasMessage(state) {
            return state.message !== null;
        },
        hasError(state) {
            return state.error !== null;
        }

    },
    actions: {
        async getMessage() {
            const authStore = useAuthStore()
            const { token } = storeToRefs(authStore)

            this.message = null
            const { data, status } = await useFetch('/',{
                method: 'GET',
                baseURL: 'http://localhost:8080',
                headers: {
                    Authorization : 'Bearer ' + token.value

                }
            })

            if (status.value==='error'){
                this.error = true

            } else {
                this.message = data.value
            }


        }
    }
});