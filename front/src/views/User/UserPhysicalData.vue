<template>
  <div class="app-container">
    <el-card v-if="!hasPhysicalData">
      <div class="empty-data">
        <el-empty description="暂无身体数据">
          <el-button type="primary" @click="showAddDialog">添加身体数据</el-button>
        </el-empty>
      </div>
    </el-card>

    <el-card v-else>
      <template #header>
        <div class="card-header">
          <span>身体数据</span>
          <el-button type="primary" @click="showEditDialog">修改数据</el-button>
        </div>
      </template>
      
      <el-descriptions :column="2" border>
        <el-descriptions-item label="年龄">{{ physicalData.age }}岁</el-descriptions-item>
        <el-descriptions-item label="性别">
          {{ physicalData.gender === '0' ? '男' : physicalData.gender === '1' ? '女' : '未知' }}
        </el-descriptions-item>
        <el-descriptions-item label="身高">{{ physicalData.height }}cm</el-descriptions-item>
        <el-descriptions-item label="体重">{{ physicalData.weight }}kg</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogType === 'add' ? '添加身体数据' : '修改身体数据'"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="1" :max="120" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
            <el-radio label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身高(cm)" prop="height">
          <el-input-number 
            v-model="form.height" 
            :min="50" 
            :max="250" 
            :precision="1"
            :step="0.1"
          />
        </el-form-item>
        <el-form-item label="体重(kg)" prop="weight">
          <el-input-number 
            v-model="form.weight" 
            :min="20" 
            :max="200" 
            :precision="1"
            :step="0.1"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="loading">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import request from '@/request'
import { getItem } from '@/utils/storage';

const hasPhysicalData = ref(false)
const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const loading = ref(false)
const formRef = ref<FormInstance>()

const physicalData = ref({
  userId: null,
  age: 0,
  gender: '2',
  height: 0,
  weight: 0
})

const form = ref({
  userId: null,
  age: 25,
  gender: '2',
  height: 170,
  weight: 60
})

const rules = {
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  height: [{ required: true, message: '请输入身高', trigger: 'blur' }],
  weight: [{ required: true, message: '请输入体重', trigger: 'blur' }]
}

// 获取用户身体数据
const fetchPhysicalData = async () => {
  try {
    const userId = getItem('userId')
    if (!userId) return
    
    const response = await request.get(`/manage/healthuser/data/${userId}`)
    if (response.data.code === 200 && response.data.data) {
      physicalData.value = response.data.data
      hasPhysicalData.value = true
    }
  } catch (error) {
    console.error('获取身体数据失败:', error)
  }
}

const showAddDialog = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
}

const showEditDialog = () => {
  dialogType.value = 'edit'
  form.value = { ...physicalData.value }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    try {
      const userId = getItem('userId')
      form.value.userId = Number(userId)
      
      const url = '/manage/healthuser/data'
      const method = dialogType.value === 'add' ? 'post' : 'put'
      const response = await request[method](url, form.value)
      
      if (response.data.code === 200) {
        ElMessage.success(dialogType.value === 'add' ? '添加成功' : '修改成功')
        dialogVisible.value = false
        await fetchPhysicalData()
      }
    } catch (error) {
      console.error('提交失败:', error)
      ElMessage.error('操作失败')
    } finally {
      loading.value = false
    }
  })
}

onMounted(() => {
  fetchPhysicalData()
})
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.empty-data {
  padding: 40px 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

:deep(.el-descriptions) {
  margin-top: 20px;
}

:deep(.el-input-number) {
  width: 180px;
}
</style>