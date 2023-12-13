import { defineStore } from 'pinia'
import { useFetch } from "nuxt/app"

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

            const { data, status } = await useFetch('/token',{
                method: 'POST',
                baseURL: 'http://localhost:8080',
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