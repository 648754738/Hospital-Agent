<template>
  <div class="bg-white rounded-lg shadow-md p-4">
    <div class="flex gap-2">
      <textarea 
        v-model="messages"
        placeholder="请输入您的问题..." 
        class="flex-1 border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none"
        rows="3"
        @keyup.enter.exact="sendMessage"
      ></textarea>
      <button 
        @click="sendMessage" 
        class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg transition-all duration-300 transform hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed"
        :disabled="!messages.trim()"
      >
        发送
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue'

const emits = defineEmits(['sendMessage'])
const messages = ref('')

const sendMessage = () => {
  if (messages.value.trim()) {
    emits('sendMessage', messages.value.trim())
    messages.value = ''
  }
}
</script>    