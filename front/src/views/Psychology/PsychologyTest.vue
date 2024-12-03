<template>
    <div class="psychology-test-container">
      <el-card class="test-card">
        <template #header>
          <div class="card-header">
            <span>心理测试</span>
          </div>
        </template>
        
        <el-empty
          description="心理测试功能开发中..."
        >
          <template #image>
            <el-icon :size="60" style="margin-bottom: 15px"><Tools /></el-icon>
          </template>
          <template #description>
            <div>
              <p>该功能正在开发中，敬请期待！</p>
              <p class="sub-text">您可以先体验AI心理咨询功能</p>
            </div>
          </template>
          <el-button type="primary" @click="goToCounseling">
            前往AI心理咨询
          </el-button>
        </el-empty>
      </el-card>
    </div>
  </template>
  
  <script setup lang="ts">
  import { Tools } from '@element-plus/icons-vue'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  const goToCounseling = () => {
    router.push('/psychology/counsel')
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