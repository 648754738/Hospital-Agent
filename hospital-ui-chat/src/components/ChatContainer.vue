<template>
  <div class="bg-white rounded-lg shadow-md p-4 mb-4 h-[500px] overflow-y-auto flex flex-col gap-4" ref="chatContainer">
    <div v-for="message in chatMessages" :key="message.id"
         :class="message.role === 'user' ? 'user-message' : 'assistant-message'">
      <div class="flex items-start gap-3">
        <div class="w-10 h-10 rounded-full flex items-center justify-center text-white font-bold"
             :class="message.role === 'user' ? 'bg-blue-500' : 'bg-green-500'">
          {{ message.role === 'user' ? '你' : '助手' }}
        </div>
        <div class="flex-1">
          <div class="bg-gray-100 p-3 rounded-lg" v-html="message.content"></div>
          <div class="text-xs text-gray-500 mt-1">
            {{ new Date(parseInt(message.id)).toLocaleTimeString() }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useChatStore } from '../stores/chat'

const chatStore = useChatStore()
const chatMessages = computed(() => chatStore.messages)
const chatContainer = ref(null)

// 滚动到底部
const scrollToBottom = () => {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight
  }
}

// 监听消息变化，滚动到底部
watch(chatMessages, scrollToBottom)

onMounted(scrollToBottom)
</script>

<style scoped>
.user-message {
  align-self: flex-end;
  max-width: 90%;
}

.assistant-message {
  align-self: flex-start;
  max-width: 90%;
}
</style>