<template>
  <div class="psychology-test-container">
    <el-card class="test-card">
      <!-- 测试未开始 -->
      <div v-if="!testStarted" class="start-section">
        <h2>心理健康自评量表</h2>
        <p>本测试包含20个问题，请根据最近一周的实际情况作答</p>
        <el-button type="primary" @click="startTest">开始测试</el-button>
      </div>

      <!-- 测试进行中 -->
      <div v-else-if="!testCompleted" class="question-section">
        <el-progress :percentage="progressPercentage" />
        <h3>{{ currentQuestion.questionText }}</h3>
        <el-radio-group v-model="currentAnswer">
          <el-radio v-for="option in currentQuestion.options"
                   :key="option.optionId"
                   :label="option.optionName">
            {{ option.optionText }}
          </el-radio>
        </el-radio-group>
        <div class="button-group">
          <el-button @click="previousQuestion" :disabled="currentIndex === 0">
            上一题
          </el-button>
          <el-button type="primary" @click="nextQuestion">
            {{ currentIndex === questions.length - 1 ? '提交' : '下一题' }}
          </el-button>
        </div>
      </div>

      <!-- 测试完成 -->
      <div v-else class="result-section">
        <h2>测试结果</h2>
        <div class="score">得分：{{ testResult.totalScore }}</div>
        <div class="analysis">{{ testResult.aiAnalysis }}</div>
        <el-button type="primary" @click="restartTest">重新测试</el-button>
      </div>
    </el-card>
  </div>
</template>
  
  <script setup lang="ts">
  import { ref, computed } from 'vue'
  import { ElMessage, ElLoading } from 'element-plus'
  import psychology from '@/api/psychology'
  import { getItem } from '@/utils/storage'

  interface Question {
    questionId: number
    questionText: string
    options: Array<{
      optionId: number
      optionName: string
      optionText: string
    }>
  }

  interface TestResult {
    totalScore: number
    aiAnalysis: string
  }

  const testStarted = ref(false)
  const testCompleted = ref(false)
  const questions = ref<Question[]>([])
  const currentIndex = ref(0)
  const currentAnswer = ref<string>('')
  const answers = ref<Map<number, string>>(new Map())
  const testResult = ref<TestResult>({
    totalScore: 0,
    aiAnalysis: ''
  })

  // 计算当前进度百分比
  const progressPercentage = computed(() => {
    return Math.round((answers.value.size / questions.value.length) * 100)
  })

  // 获取当前题目
  const currentQuestion = computed(() => {
    console.log('Current Index:', currentIndex.value)  // 添加调试日志
    console.log('Questions Array:', questions.value)   // 添加调试日志
    return questions.value[currentIndex.value] || {}
  })

  // 开始测试
  const startTest = async () => {
    try {
      const res = await psychology.startPsychologyTest()
      console.log('API Response:', res)  // 添加调试日志
      if (res.data.code === 200) {
        questions.value = res.data.data
        console.log('Questions:', questions.value)  // 添加调试日志
        testStarted.value = true
        // 重置所有状态
        currentIndex.value = 0
        currentAnswer.value = ''
        answers.value.clear()
        testCompleted.value = false
      } else {
        ElMessage.error(res.data.msg || '获取题目失败')
      }
    } catch (error) {
      console.error('Error:', error)  // 添加错误日志
      ElMessage.error('获取题目失败')
    }
  }

  // 下一题或提交
  const nextQuestion = async () => {
    if (!currentAnswer.value) {
      ElMessage.warning('请选择一个选项')
      return
    }

    // 保存当前答案
    answers.value.set(currentQuestion.value.questionId, currentAnswer.value)

    if (currentIndex.value === questions.value.length - 1) {
      // 最后一题，提交测试
      await submitTest()
    } else {
      // 进入下一题
      currentIndex.value++
      // 显示已选择的答案（如果有）
      currentAnswer.value = answers.value.get(currentQuestion.value.questionId) || ''
    }
  }

  // 上一题
  const previousQuestion = () => {
    if (currentIndex.value > 0) {
      currentIndex.value--
      // 显示已选择的答案（如果有）
      currentAnswer.value = answers.value.get(currentQuestion.value.questionId) || ''
    }
  }

  // 提交测试
  const submitTest = async () => {
    const loading = ElLoading.service({
      lock: true,
      text: '提交中...',
      background: 'rgba(0, 0, 0, 0.7)'
    })

    try {
      const userId = getItem('userId')
      const answersArray = Array.from(answers.value.entries()).map(([questionId, optionName]) => ({
        questionId,
        optionName
      }))
      
      const res = await psychology.submitPsychologyTest({
        userId,
        answers: answersArray
      })
      
      if (res.data.code === 200) {
        testResult.value = res.data.data
        testCompleted.value = true
      } else {
        ElMessage.error(res.data.msg || '提交测试失败')
      }
    } catch (error) {
      ElMessage.error('提交测试失败')
    } finally {
      loading.close()  // 确保在请求完成后关闭加载提示
    }
  }

  // 重新测试
  const restartTest = () => {
    testStarted.value = false
    testCompleted.value = false
    questions.value = []
    currentIndex.value = 0
    currentAnswer.value = ''
    answers.value.clear()
    testResult.value = {
      totalScore: 0,
      aiAnalysis: ''
    }
  }
  </script>
  
  <style scoped lang="scss">
  .psychology-test-container {
    padding: 20px;
    height: calc(100vh - 60px);
  }
  
  .test-card {
    height: 100%;
  }
  
  .card-header {
    font-weight: bold;
    font-size: 16px;
  }
  
  .sub-text {
    color: #909399;
    font-size: 14px;
    margin-top: 8px;
  }
  
  :deep(.el-empty) {
    padding: 80px 0;
  }
  </style>