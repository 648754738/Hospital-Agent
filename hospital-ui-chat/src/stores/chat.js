import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useChatStore = defineStore('chat', () => {
  const messages = ref([])
  const memoryId = ref(null)
  
  // 初始化记忆ID
  if (!memoryId.value) {
    memoryId.value = generateMemoryId()
  }

  // 生成唯一记忆ID
  function generateMemoryId() {
    return 'mem-' + Date.now() + '-' + Math.random().toString(36).substring(2, 10)
  }

  // 添加消息
  function addMessage(message) {
    messages.value.push(message)
  }

  // 发送消息到服务器
  async function sendMessage(content) {
    // 添加用户消息
    const userMessage = {
      id: Date.now().toString(),
      role: 'user',
      content: content
    }
    addMessage(userMessage)

    // 添加正在输入状态
    const typingId = 'typing-' + Date.now()
    addMessage({
      id: typingId,
      role: 'assistant',
      content: '<div class="typing-indicator"><span></span><span></span><span></span></div>'
    })

    try {
      // 发送消息到服务器
      const response = await fetch('/api/agent/chat', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          memoryId: memoryId.value,
          messages: content
        })
      })

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }

      // 移除正在输入状态
      messages.value = messages.value.filter(msg => msg.id !== typingId)

      // 处理流式响应
      const reader = response.body.getReader()
      const decoder = new TextDecoder()
      let assistantContent = ''
      const assistantId = Date.now().toString()

      while (true) {
        const { done, value } = await reader.read()
        if (done) {
          break
        }
        // 解码新的文本块并添加到内容中
        assistantContent += decoder.decode(value, { stream: true })
        // 更新消息内容
        const msgIndex = messages.value.findIndex(msg => msg.id === assistantId)
        if (msgIndex !== -1) {
          messages.value[msgIndex].content = assistantContent
        } else {
          // 添加新消息
          addMessage({
            id: assistantId,
            role: 'assistant',
            content: assistantContent
          })
        }
      }
    } catch (error) {
      console.error('Error:', error)
      // 移除正在输入状态
      messages.value = messages.value.filter(msg => msg.id !== typingId)
      // 添加错误消息
      addMessage({
        id: Date.now().toString(),
        role: 'system',
        content: '抱歉，与服务器通信时出现错误。请稍后再试。'
      })
      throw error
    }
  }

  return {
    messages,
    memoryId,
    addMessage,
    sendMessage
  }
})    