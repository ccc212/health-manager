<template>
  <div class="fitness-plan">
    <div class="action-bar">
      <el-button type="primary" @click="createPlan">创建新计划</el-button>
    </div>
    
    <el-table :data="planList" style="width: 100%">
      <el-table-column prop="goals" label="健身目标" />
      <el-table-column prop="initial_status" label="初始状态" />
      <el-table-column prop="current_status" label="当前状态" />
      <el-table-column prop="progress" label="完成进度">
        <template #default="{ row }">
          <el-progress :percentage="row.progress" />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="primary" @click="viewDetails(row)">查看详情</el-button>
          <el-button type="success" @click="generatePlan(row)">生成计划</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 创建计划对话框 -->
    <el-dialog v-model="dialogVisible" title="创建健身计划">
      <el-form :model="planForm" label-width="100px">
        <el-form-item label="健身目标">
          <el-input v-model="planForm.goals" type="textarea" />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-input v-model="planForm.initial_status" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPlan">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getFitnessPlanList, createFitnessPlan, generateFitnessPlan } from '@/api/exercise'

const planList = ref([])
const dialogVisible = ref(false)
const planForm = ref({
  goals: '',
  initial_status: ''
})

const fetchPlanList = async () => {
  try {
    const res = await getFitnessPlanList()
    planList.value = res.rows
  } catch (error) {
    ElMessage.error('获取计划列表失败')
  }
}

const createPlan = () => {
  dialogVisible.value = true
}

const submitPlan = async () => {
  try {
    await createFitnessPlan(planForm.value)
    ElMessage.success('创建成功')
    dialogVisible.value = false
    fetchPlanList()
  } catch (error) {
    ElMessage.error('创建失败')
  }
}

const generatePlan = async (row) => {
  try {
    await generateFitnessPlan(row.plan_id)
    ElMessage.success('计划生成成功')
    fetchPlanList()
  } catch (error) {
    ElMessage.error('计划生成失败')
  }
}

onMounted(() => {
  fetchPlanList()
})
</script>