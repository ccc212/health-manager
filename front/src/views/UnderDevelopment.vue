<template>
    <div class="under-development">
      <el-empty description="功能开发中，敬请期待...">
        <el-button type="primary" @click="goBack">返回</el-button>
      </el-empty>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  const goBack = () => {
    router.back()
  }
  </script>
  
  <style scoped lang="scss">
  .under-development {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding: 20px;
  }
  </style>