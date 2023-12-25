import { defineStore } from 'pinia'
import {useFetch, useRuntimeConfig} from "nuxt/app"

export const useAuthStore = defineStore({
    id: 'auth',
    state: () => ({
        token: null,
        user: {},
        error: null
    }),
    getters: {
        tokenAvailable(state) {
            return state.token !== null;
        },
        hasError(state) {
            return state.error !== null;
        }

    },
    actions: {
        async login(username, password) {
            this.token = null
            this.error = null

            const { data, status } = await useFetch('v1/token',{
                method: 'POST',
                baseURL: useRuntimeConfig().public.apiBase,
                headers: {
                    Authorization : 'Basic ' + btoa(username + ":" + password)
                },
                body: {
                    username: username,
                    password: password
                }
            })

            if (status.value==='error'){
                this.error = true

            } else {
                this.user = {name: username}
                this.token = data.value

            }


        },
        logout() {
            this.token = null;
        }
    }
});