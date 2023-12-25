<script setup>

import { useAuthStore } from '~/store/auth'
import { usePlayerStore } from '~/store/player'
import {storeToRefs} from "pinia";

const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

const playerStore = usePlayerStore()
const { getPlayerDetails, getUnfinishedGame } = playerStore

const { player, unfinishedGame, hasPlayer, hasUnfinishedGame } = storeToRefs(playerStore)

definePageMeta({
  middleware: 'login-redirect'
})

await onMounted(() => {
  getPlayerDetails(user.value.name)
})

</script>
<template>
   Hello {{ user }} from frontend
  <div v-show="hasPlayer" class="text-red-400">{{ player }}</div>
  <div v-show="hasUnfinishedGame" class="text-red-400">{{ unfinishedGame }}</div>
</template>

<style>
</style>