  <template>
  <div class="min-h-screen bg-gray-50 font-sans">
    <!-- 头部 -->
    <header class="bg-white shadow-sm">
      <div class="container mx-auto px-4">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center">
            <div class="bg-primary w-10 h-10 rounded-full flex items-center justify-center">
              <i class="fa fa-user-md text-white"></i>
            </div>
            <h1 class="ml-3 text-xl font-bold text-gray-800 hidden sm:block">预约挂号智能小助手</h1>
            <h1 class="ml-3 text-xl font-bold text-gray-800 sm:hidden">挂号助手</h1>
          </div>

          <div class="flex items-center">
            <span class="flex h-3 w-3 mr-2">
              <span class="animate-ping absolute inline-flex h-3 w-3 rounded-full bg-green-400 opacity-75"></span>
              <span class="relative inline-flex rounded-full h-3 w-3 bg-green-500"></span>
            </span>
            <span class="text-sm text-gray-600 hidden md:block">在线</span>
          </div>
        </div>
      </div>
    </header>

    <!-- 主体内容 -->
    <main class="flex-grow container mx-auto px-4 py-6">
      <div class="max-w-4xl mx-auto">
        <div class="bg-white rounded-xl shadow-lg overflow-hidden">
          <!-- 助手介绍区域 -->
          <div class="p-6 border-b border-gray-200 gradient-bg">
            <div class="flex items-center">
              <div class="bg-secondary w-14 h-14 rounded-full flex items-center justify-center">
                <i class="fa fa-robot text-white text-2xl"></i>
              </div>
              <div class="ml-4">
                <h2 class="text-xl font-bold text-gray-800">智能挂号助手</h2>
                <p class="text-gray-600">随时为您解答挂号相关问题</p>
              </div>
            </div>
          </div>

          <!-- 聊天区域 -->
          <div class="p-4 md:p-6">
            <ChatContainer />
            <UserInput @sendMessage="handleSendMessage" class="mt-6" />
          </div>
        </div>
      </div>
    </main>

    <!-- 页脚 -->
    <footer class="py-6 text-center text-gray-500 text-sm bg-white border-t">
      <div class="container mx-auto px-4">
        <p>© 2025 预约挂号智能小助手 - 您的健康管家</p>
        <p class="mt-1 text-xs">本系统严格遵守医疗信息保密原则，保护您的隐私安全</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ChatContainer from './components/ChatContainer.vue'
import UserInput from './components/UserInput.vue'
import { useChatStore } from './stores/chat'

const chatStore = useChatStore()

const handleSendMessage = async (message) => {
  try {
    await chatStore.sendMessage(message)
  } catch (error) {
    console.error('Error sending message:', error)
    chatStore.addMessage({
      id: Date.now().toString(),
      role: 'system',
      content: '抱歉，发送消息时出现错误。请稍后再试。'
    })
  }
}
</script>

<style scoped>
/* 定义颜色变量 */
:deep(.bg-primary) {
  background-color: #165DFF;
}

:deep(.bg-secondary) {
  background-color: #36D399;
}

:deep(.gradient-bg) {
  background: linear-gradient(135deg, #f0f4ff 0%, #e6f7ff 100%);
}

/* 消息气泡样式 */
:deep(.message-bubble) {
  @apply rounded-2xl px-4 py-3 max-w-[85%] shadow-sm;
}

:deep(.user-message) {
  @apply bg-primary text-white rounded-tr-none;
}

:deep(.bot-message) {
  @apply bg-white text-gray-700 rounded-tl-none border border-gray-200;
}

/* 滚动条样式 */
:deep(.scrollbar-hide::-webkit-scrollbar) {
  display: none;
}

:deep(.scrollbar-hide) {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

/* 打字动画 */
:deep(.typing-animation) {
  display: inline-block;
  overflow: hidden;
  border-right: 0.15em solid;
  white-space: nowrap;
  margin: 0;
  animation: typing 3.5s steps(40, end), blink-caret 0.75s step-end infinite;
}

@keyframes typing {
  from { width: 0 }
  to { width: 100% }
}

@keyframes blink-caret {
  from, to { border-color: transparent }
  50% { border-color: currentColor }
}
</style>
