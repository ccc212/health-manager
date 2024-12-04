<template>
    <div class="counseling-container">
      <div class="chat-container">
        <div class="chat-messages" ref="messagesContainer">
          <div v-for="(message, index) in chatMessages" :key="index" 
               :class="['message', message.type]">
            <div class="message-content">
              <div class="avatar">
                <el-avatar :size="40" :src="message.type === 'user' ? userAvatar : aiAvatar" />
              </div>
              <div class="text-container">
                <div :class="['text', { 'markdown-content': message.type === 'ai' }]" 
                     v-html="message.type === 'ai' ? renderMarkdown(message.content) : message.content">
                </div>
                <el-button 
                  v-if="message.type === 'user' && message.recordId"
                  type="danger" 
                  link
                  size="small"
                  :loading="message.deleting"
                  @click="handleDelete(message.recordId)"
                >
                  删除
                </el-button>
              </div>
            </div>
            <div class="time">{{ message.time }}</div>
          </div>
          <div v-if="thinking" class="message ai">
            <div class="message-content">
              <div class="avatar">
                <el-avatar :size="40" :src="aiAvatar" />
              </div>
              <div class="thinking-dots">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
        </div>
  
        <div class="input-area">
          <el-input
            v-model="userInput"
            type="textarea"
            :rows="3"
            placeholder="请输入您的问题..."
            @keyup.enter.native="handleSend"
          />
          <el-button 
            type="primary" 
            :loading="loading"
            @click="handleSend"
          >发送</el-button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, nextTick, computed } from 'vue'
  import { ElMessage } from 'element-plus'
  import { getItem } from '@/utils/storage'
  import psychologyApi from '@/api/psychology'
  import dayjs from 'dayjs'
import router from '@/router'
import MarkdownIt from 'markdown-it'
  
  const userAvatar = '/src/assets/user-avatar.png'
  const aiAvatar = '/src/assets/ai-avatar.png'
  
  const userInput = ref('')
  const loading = ref(false)
  const messagesContainer = ref()
  const chatMessages = ref<Array<{type: string; content: string; time: string; recordId?: number; deleting?: boolean}>>([
    {
      type: 'ai',
      content: '您好！我是您的AI心理咨询师，请告诉我您想要咨询的问题。',
      time: dayjs().format('YYYY-MM-DD HH:mm:ss')
    }
  ])
  const counselingHistory = ref<Array<any>>([])
  const thinking = ref(false)
  
  const sortedHistory = computed(() => {
    return [...counselingHistory.value].sort((a, b) => {
      return new Date(b.createTime).getTime() - new Date(a.createTime).getTime()
    })
  })
  
  const scrollToBottom = async () => {
    await nextTick()
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  }
  
  const handleSend = async () => {
    if (!userInput.value.trim()) {
      ElMessage.warning('请输入内容')
      return
    }
  
    const userId = getItem('userId')
    if (!userId) {
      ElMessage.error('请先登录')
      router.push('/login')
      return
    }
  
    // 添加用户消息
    chatMessages.value.push({
      type: 'user',
      content: userInput.value,
      time: dayjs().format('YYYY-MM-DD HH:mm:ss')
    })
    await scrollToBottom()
  
    loading.value = true
    thinking.value = true
    try {
      const response = await psychologyApi.getAIAnswer({
        userId: Number(userId),
        question: userInput.value
      })
  
      if (response.data.code === 200) {
        chatMessages.value.push({
          type: 'ai',
          content: response.data.msg,
          time: dayjs().format('YYYY-MM-DD HH:mm:ss')
        })
        await scrollToBottom()
        await loadHistory()
      }
    } catch (error) {
      console.error('发送消息失败:', error)
      ElMessage.error('发送失败，请重试')
    } finally {
      thinking.value = false
      loading.value = false
      userInput.value = ''
    }
  }
  
  const handleDelete = async (recordId: number) => {
    try {
      // 找到对应的消息并设置删除状态
      const message = chatMessages.value.find(m => m.recordId === recordId)
      if (message) {
        message.deleting = true
      }

      await psychologyApi.deleteCounselingRecord([recordId])
      ElMessage.success('删除成功')
      await loadHistory() // 重新加载历史记录
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
  
  const loadHistory = async () => {
    try {
      const userId = getItem('userId')
      const response = await psychologyApi.getCounselingList({ userId })
      if (response.data.code === 200) {
        counselingHistory.value = response.data.rows.map((record: any) => ({
          ...record,
          deleting: false
        }))
        
        // 清空现有聊天消息，只保留欢迎消息
        chatMessages.value = [{
          type: 'ai',
          content: '您好！我是您的AI心理咨询师，请告诉我您想要咨询的问题。',
          time: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }]
        
        // 将历史记录转换为聊天消息格式
        counselingHistory.value.forEach((record: any) => {
          // 添加用户问题，包含recordId用于删除
          chatMessages.value.push({
            type: 'user',
            content: record.question,
            time: record.createTime,
            recordId: record.recordId,
            deleting: false
          })
          // 添加AI回答
          chatMessages.value.push({
            type: 'ai',
            content: record.answer,
            time: record.createTime
          })
        })
      }
    } catch (error) {
      console.error('获取历史记录失败:', error)
      ElMessage.error('获取历史记录失败')
    }
  }
  
  // 初始化 markdown-it
  const md = new MarkdownIt({
    html: true,
    breaks: true,
    linkify: true
  })
  
  // 添加 markdown 渲染函数
  const renderMarkdown = (content: string) => {
    return md.render(content || '')
  }
  
  onMounted(() => {
    loadHistory()
  })
  </script>
  
  <style scoped lang="scss">
  .counseling-container {
    display: flex;
    gap: 20px;
    height: 100%;
    padding: 20px;

    .chat-container {
      flex: 1;
      display: flex;
      flex-direction: column;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
  }
  
  .chat-messages {
    flex: 1;
    padding: 20px;
    overflow-y: auto;
  
    .message {
      margin-bottom: 20px;
  
      .message-content {
        display: flex;
        gap: 10px;
      }
  
      .text-container {
        display: flex;
        align-items: flex-start;
        gap: 8px;
        max-width: 80%;
  
        .text {
          flex: 1;
          padding: 10px;
          border-radius: 8px;
        }
  
        .el-button {
          opacity: 0;
          transition: opacity 0.3s;
        }
      }
  
      &:hover {
        .text-container {
          .el-button {
            opacity: 1;
          }
        }
      }
  
      .time {
        font-size: 12px;
        color: #999;
        margin-top: 5px;
        margin-left: 50px;
      }
  
      &.user {
        .message-content {
          flex-direction: row-reverse;
          
          .text-container {
            flex-direction: row-reverse;
          }
        }
        .text {
          background-color: #e3f2fd;
        }
        .time {
          text-align: right;
          margin-right: 50px;
          margin-left: 0;
        }
      }
  
      &.ai .text {
        background-color: #f5f5f5;
      }
    }
  }
  
  .input-area {
    padding: 20px;
    border-top: 1px solid #eee;
  
    .el-button {
      width: 100%;
      margin-top: 10px;
    }
  }
  
  .thinking-dots {
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 8px;
    display: flex;
    gap: 4px;
    align-items: center;
    
    span {
      display: inline-block;
      width: 8px;
      height: 8px;
      background-color: #999;
      border-radius: 50%;
      animation: thinking 1.4s infinite ease-in-out;
      
      &:nth-child(1) { animation-delay: 0s; }
      &:nth-child(2) { animation-delay: 0.2s; }
      &:nth-child(3) { animation-delay: 0.4s; }
    }
  }
  
  @keyframes thinking {
    0%, 80%, 100% { 
      transform: scale(0);
      opacity: 0.3;
    }
    40% { 
      transform: scale(1);
      opacity: 1;
    }
  }
  
  .record-header {
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  
  .history-card {
    :deep(.el-timeline) {
      padding-top: 0;
    }
    
    :deep(.el-card__header) {
      padding: 10px 15px;
    }
  }
  
  .markdown-content {
    :deep(p) {
      margin: 0.5em 0;
    }

    :deep(ul), :deep(ol) {
      padding-left: 20px;
      margin: 0.5em 0;
    }

    :deep(h1), :deep(h2), :deep(h3), :deep(h4), :deep(h5), :deep(h6) {
      margin: 0.5em 0;
      font-weight: bold;
    }

    :deep(code) {
      background-color: #f5f5f5;
      padding: 2px 4px;
      border-radius: 4px;
      font-family: monospace;
    }

    :deep(pre) {
      background-color: #f5f5f5;
      padding: 1em;
      border-radius: 4px;
      overflow-x: auto;
      
      code {
        background-color: transparent;
        padding: 0;
      }
    }

    :deep(blockquote) {
      margin: 0.5em 0;
      padding-left: 1em;
      border-left: 4px solid #ddd;
      color: #666;
    }

    :deep(a) {
      color: #409eff;
      text-decoration: none;
      
      &:hover {
        text-decoration: underline;
      }
    }

    :deep(table) {
      border-collapse: collapse;
      margin: 0.5em 0;
      
      th, td {
        border: 1px solid #ddd;
        padding: 8px;
      }
      
      th {
        background-color: #f5f5f5;
      }
    }
  }
  </style>