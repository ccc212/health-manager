<template>
  <div class="fitness-record">
    <div class="record-header">
      <el-tooltip
        :content="!hasGeneratedPlan ? '请先创建并生成健身计划' : '添加记录'"
        placement="top"
      >
        <div style="display: inline-block">
          <el-button 
            type="primary" 
            @click="showAddDialog"
            :disabled="!hasGeneratedPlan"
          >添加记录</el-button>
        </div>
      </el-tooltip>
    </div>

    <!-- 记录列表 -->
    <el-table :data="recordList" style="width: 100%" v-loading="loading">
      <el-table-column prop="recordDate" label="日期" width="180">
        <template #default="{ row }">
          {{ formatDate(row.recordDate) }}
        </template>
      </el-table-column>
      <el-table-column label="锻炼项目" width="180">
        <template #default="{ row }">
          {{ getExerciseName(row.exerciseId) }}
        </template>
      </el-table-column>
      <el-table-column prop="duration" label="时长(分钟)" width="120" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="editRecord(row)">编辑</el-button>
          <el-button link type="danger" @click="deleteRecord(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/编辑记录对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加记录' : '编辑记录'"
      width="500px"
    >
      <el-form :model="recordForm" label-width="100px" :rules="rules" ref="recordFormRef">
        <el-form-item label="日期" prop="recordDate">
          <el-date-picker
            v-model="recordForm.recordDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="锻炼项目" prop="exerciseId">
          <el-select 
            v-model="recordForm.exerciseId"
            placeholder="请选择锻炼项目"
            filterable
            @change="handleExerciseChange"
          >
            <el-option
              v-for="item in exerciseList"
              :key="item.exerciseId"
              :label="item.exerciseName"
              :value="item.exerciseId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="运动时长" prop="duration">
          <el-input-number 
            v-model="recordForm.duration" 
            :min="1" 
            :max="480"
            placeholder="请输入运动时长(分钟)"
          />
        </el-form-item>

        <el-form-item label="消耗卡路里" prop="calories">
          <el-input-number 
            v-model="recordForm.calories" 
            :min="0"
            placeholder="请输入消耗的卡路里"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitRecord" :loading="submitting">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'
import exercise from '@/api/exercise'
import { getItem } from '@/utils/storage'
import dayjs from 'dayjs'

const loading = ref(false)
const submitting = ref(false)
const recordList = ref([])
const exerciseList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const recordFormRef = ref<FormInstance>()
const hasGeneratedPlan = ref(false)

const recordForm = reactive({
  recordId: undefined,
  userId: getItem('userId'),
  exerciseId: undefined,
  exerciseName: '',
  duration: 30,
  calories: 0,
  recordDate: dayjs().format('YYYY-MM-DD'),
})

const rules = {
  recordDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  exerciseId: [{ required: true, message: '请选择锻炼项目', trigger: 'change' }],
  duration: [{ required: true, message: '请输入运动时长', trigger: 'blur' }],
}

// 获取记录列表
const getRecordList = async () => {
  loading.value = true
  try {
    const res = await exercise.getFitnessRecordList({
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    if (res.data.code === 200) {
      recordList.value = res.data.rows
      total.value = res.data.total
    }
  } catch (error) {
    ElMessage.error('获取记录失败')
  } finally {
    loading.value = false
  }
}

// 获取锻炼项目列表
const getExerciseList = async () => {
  try {
    const res = await exercise.getAllExercises()
    if (res.data.code === 200) {
      exerciseList.value = res.data.rows
      console.log('锻炼项目列表:', exerciseList.value)
    }
  } catch (error) {
    console.error('获取锻炼项目列表失败:', error)
  }
}

// 根据ID获取锻炼项目名称
const getExerciseName = (exerciseId: number) => {
  const exercise = exerciseList.value.find(e => e.exerciseId === exerciseId)
  return exercise ? exercise.exerciseName : '未知项目'
}

// 处理锻炼项目选择变化
const handleExerciseChange = (exerciseId: number) => {
  const exercise = exerciseList.value.find(e => e.exerciseId === exerciseId)
  if (exercise) {
    recordForm.exerciseName = exercise.exerciseName
  }
  console.log('当前表单:', recordForm) // 添加调试日志
}

// 检查是否有已生成的计划
const checkPlanStatus = async () => {
  try {
    const res = await exercise.checkActivePlan(getItem('userId'))
    if (res.data.code === 200) {
      hasGeneratedPlan.value = res.data.data
    }
  } catch (error) {
    console.error('检查计划状态失败:', error)
  }
}

// 显示添加对话框
const showAddDialog = () => {
  if (!hasGeneratedPlan.value) {
    ElMessage.warning('请先创建并生成健身计划')
    return
  }
  dialogType.value = 'add'
  recordForm.recordId = undefined
  recordForm.exerciseId = undefined
  recordForm.duration = 30
  recordForm.calories = 0
  recordForm.recordDate = dayjs().format('YYYY-MM-DD')
  dialogVisible.value = true
}

// 编辑记录
const editRecord = (row: any) => {
  dialogType.value = 'edit'
  Object.assign(recordForm, row)
  dialogVisible.value = true
}

// 删除记录
const deleteRecord = (row: any) => {
  ElMessageBox.confirm('确认删除该记录吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    try {
      const res = await exercise.deleteFitnessRecord([row.recordId])
      if (res.data.code === 200) {
        ElMessage.success('删除成功')
        getRecordList()
      }
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

// 提交记录
const submitRecord = async () => {
  if (!recordFormRef.value) return
  
  try {
    await recordFormRef.value.validate()
    submitting.value = true
    
    const api = dialogType.value === 'add' ? 
      exercise.addFitnessRecord : 
      exercise.updateFitnessRecord
    
    const res = await api(recordForm)
    if (res.data.code === 200) {
      ElMessage.success(dialogType.value === 'add' ? '添加成功' : '更新成功')
      dialogVisible.value = false
      getRecordList()
      updatePlanProgress(recordForm.userId) // 更新计划进度
    }
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '添加失败' : '更新失败')
  } finally {
    submitting.value = false
  }
}

// 更新计划进度
const updatePlanProgress = async (planId: number) => {
  try {
    const res = await exercise.updatePlanProgress(planId)
    if (res.data.code === 200) {
      ElMessage.success('计划进度更新成功')
    }
  } catch (error) {
    ElMessage.error('计划进度更新失败')
  }
}

// 格式化日期
const formatDate = (date: string) => {
  return dayjs(date).format('YYYY-MM-DD')
}

// 处理每页显示数量变化
const handleSizeChange = (val: number) => {
  pageSize.value = val
  currentPage.value = 1
  getRecordList()
}

// 处理页码变化
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  getRecordList()
}

onMounted(async () => {
  await checkPlanStatus()
  await getExerciseList()
  getRecordList()
})
</script>

<style scoped lang="scss">
.fitness-record {
  padding: 20px;
  
  .record-header {
    margin-bottom: 20px;
  }
  
  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }

  :deep(.el-tag) {
    width: 80px;
    text-align: center;
  }

  :deep(.el-button.is-disabled) {
    cursor: not-allowed;
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>