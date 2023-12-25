import { defineStore, storeToRefs } from 'pinia'
import { useFetch } from "nuxt/app"

import { useAuthStore } from '~/store/auth'


export const usePlayerStore = defineStore({
    id: 'player',
    state: () => ({
        player: null,
        unfinishedGame: null
    }),
    getters: {
        hasPlayer(state) {
            return state.player !== null;
        },
        hasUnfinishedGame(state) {
            return state.unfinishedGame !== null;
        }

    },
    actions: {
        async getPlayerDetails(username) {
            const authStore = useAuthStore()
            const { token } = storeToRefs(authStore)

            this.player = null
            const { data, status } = await useFetch('/v1/player/byUsername/' + username,{
                method: 'GET',
                baseURL: useRuntimeConfig().public.apiBase,
                headers: {
                    Authorization : 'Bearer ' + token.value

                }
            })

            if (status.value==='error'){
                this.error = true

            } else {
                this.player = data.value

                await this.getUnfinishedGame(this.player.id)
            }
        },
        async getUnfinishedGame(playerId) {
            const authStore = useAuthStore()
            const { token } = storeToRefs(authStore)

            this.unfinishedGame = null
            const { data, status } = await useFetch('/v1/player/' + playerId + '/unfinishedGame',{
                method: 'GET',
                baseURL: useRuntimeConfig().public.apiBase,
                headers: {
                    Authorization : 'Bearer ' + token.value

                }
            })

            if (status.value==='error'){
                this.error = true

            } else {
                this.unfinishedGame = data.value
            }
        }
    }
});