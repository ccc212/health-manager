<template>
  <div class="fitness-plan">
    <div class="action-bar">
      <el-tooltip
        :content="hasActivePlan ? '您已有进行中的计划，完成后才能创建新计划' : '创建新计划'"
        placement="top"
      >
        <div style="display: inline-block">
          <el-button 
            type="primary" 
            @click="createPlan"
            :disabled="hasActivePlan"
          >创建新计划</el-button>
        </div>
      </el-tooltip>
    </div>
    
    <el-table :data="planList" style="width: 100%">
      <el-table-column prop="goals" label="健身目标" />
      <el-table-column prop="initialStatus" label="初始状态" />
      <el-table-column prop="currentStatus" label="当前状态" />
      <el-table-column prop="progress" label="完成进度">
        <template #default="{ row }">
          <el-progress :percentage="Number(row.progress) || 0" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="viewDetails(row)">查看详情</el-button>
          <el-button 
            type="success" 
            size="small" 
            @click="generatePlan(row)"
            :loading="row.generating"
          >生成计划</el-button>
          <el-button 
            type="danger" 
            size="small" 
            @click="handleDelete(row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 创建计划对话框 -->
    <el-dialog v-model="dialogVisible" title="创建健身计划" width="500px">
      <el-form :model="planForm" label-width="100px" :rules="rules" ref="planFormRef">
        <el-form-item label="健身目标">
          <el-select 
            v-model="selectedGoal" 
            placeholder="选择预设目标" 
            clearable
            @change="handleGoalChange"
            class="goal-select"
          >
            <el-option
              v-for="item in goalOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="目标详情" prop="goals">
          <el-input 
            v-model="planForm.goals" 
            type="textarea" 
            placeholder="请描述您的健身目标，可以修改预设内容"
          />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-select 
            v-model="selectedStatus" 
            placeholder="选择预设状态" 
            clearable
            @change="handleStatusChange"
            class="status-select"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态详情" prop="initialStatus">
          <el-input 
            v-model="planForm.initialStatus" 
            type="textarea" 
            placeholder="请描述您的当前身体状况，可以修改预设内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPlan" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>

    <!-- 计划详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="计划详情" width="60%">
      <el-table :data="planDetails" style="width: 100%">
        <el-table-column prop="exerciseName" label="锻炼项目" show-overflow-tooltip />
        <el-table-column label="强度" width="100">
          <template #default="{ row }">
            <el-tag :type="getIntensityType(row.intensity)">
              {{ getIntensityLabel(row.intensity) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="时长" width="120">
          <template #default="{ row }">
            {{ row.duration }} 分钟
          </template>
        </el-table-column>
        <el-table-column label="频率" width="120">
          <template #default="{ row }">
            每周 {{ row.frequency }} 次
          </template>
        </el-table-column>
        <el-table-column prop="notes" label="备注" show-overflow-tooltip />
      </el-table>
      
      <!-- 分页器 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, ElLoading } from 'element-plus'
import type { FormInstance } from 'element-plus'
import exercise from '@/api/exercise'
import { getItem } from '@/utils/storage';

const planList = ref([])
const exerciseList = ref([])
const dialogVisible = ref(false)
const detailDialogVisible = ref(false)
const submitting = ref(false)
const planDetails = ref([])
const planFormRef = ref<FormInstance>()
const selectedStatus = ref('')
const selectedGoal = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const currentPlanId = ref<number | null>(null)

const planForm = ref({
  userId: getItem('userId'),
  goals: '',
  initialStatus: ''
})

const rules = {
  goals: [{ required: true, message: '请输入健身目标', trigger: 'blur' }],
  initialStatus: [{ required: true, message: '请输入当前状态', trigger: 'blur' }]
}

// 预设的状态选项
const statusOptions = [
  {
    label: '缺乏运动',
    value: '长期久坐，缺乏运动。体能较差，容易疲劳，希望通过运动改善身体状况。'
  },
  {
    label: '体重偏高',
    value: '体重偏高，存在一定脂肪堆积。希望通过运动减重，改善体型。'
  },
  {
    label: '体重偏低',
    value: '体重偏低，肌肉含量较少。希望通过运动增重，增加肌肉量。'
  },
  {
    label: '运动基础',
    value: '有一定运动基础，能够进行基本的体育锻炼。希望通过系统训练提高运动水平。'
  },
  {
    label: '运动达人',
    value: '经常进行体育锻炼，有较好的体能基础。希望通过专业训练进一步提升。'
  },
  {
    label: '康复训练',
    value: '正在进行康复训练，需要循序渐进地恢复身体机能。希望通过适当运动加快康复。'
  },
  {
    label: '产后恢复',
    value: '产后需要科学恢复身体机能，希望通过适当运动改善体型和体能。'
  },
  {
    label: '老年健身',
    value: '年龄较大，需要适合老年人的运动方式。希望通过适度运动保持身体机能。'
  },
  {
    label: '青少年成长',
    value: '正处于生长发育阶段，需要科学的运动指导。希望通过运动促进健康成长。'
  },
  {
    label: '职业运动',
    value: '从事专业运动训练，有较高的运动水平。需要针对性的专业训练计划。'
  },
  {
    label: '亚健康状态',
    value: '存在一些亚健康症状，如失眠、疲劳等。希望通过运动改善身心状态。'
  },
  {
    label: '慢性病管理',
    value: '有一些慢性病症状，如高血压、糖尿病等。需要在医生指导下进行适量运动。'
  }
]

// 预设的健身目标选项
const goalOptions = [
  {
    label: '增肌',
    value: '通过力量训练和高蛋白饮食增加肌肉量,提升整体力量水平。'
  },
  {
    label: '减脂',
    value: '通过有氧运动和控制饮食减少体脂,塑造健康体型。'
  },
  {
    label: '提高耐力', 
    value: '通过长时间有氧运动提高心肺功能和身体耐力,增强体能。'
  },
  {
    label: '增强灵活性',
    value: '通过瑜伽和拉伸训练提高身��灵活性和关节活动度。'
  },
  {
    label: '增强力量',
    value: '通过器械训练和自重训练增强肌肉力量,提高爆发力。'
  },
  {
    label: '改善体态',
    value: '通过核心训练和姿势矫正改善不良体态,塑造优美体型。'
  },
  {
    label: '康复训练',
    value: '通过科学合理的训练方案促进身体康复,恢复机能。'
  },
  {
    label: '运动减压',
    value: '通过适度运动缓解压力,改善心理状态,提升生活质量。'
  },
  {
    label: '塑形美体',
    value: '通过针对性训练塑造理想体型,提升形体美感。'
  },
  {
    label: '增强免疫力',
    value: '通过适量运动增强抵抗力,提高免疫系统功能。'
  },
  {
    label: '综合健康',
    value: '通过多种运动方式全面提升身体素质,促进身心健康。'
  }
]

// 计算当前页的数据
const currentPageDetails = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value
  const endIndex = startIndex + pageSize.value
  return planDetails.value.slice(startIndex, endIndex)
})

// 处理状态选择变化
const handleStatusChange = (value: string) => {
  if (value) {
    planForm.value.initialStatus = value
  }
}

// 处理目标选择变化
const handleGoalChange = (value: string) => {
  if (value) {
    planForm.value.goals = value
  }
}

// 获取计划列表
const fetchPlanList = async () => {
  try {
    const res = await exercise.getFitnessPlanList()
    if (res.data.code === 200) {
      planList.value = res.data.rows
    }
  } catch (error) {
    ElMessage.error('获取计划列表失败')
  }
}

// 检查是否有进行中的计划
const hasActivePlan = computed(() => {
  return planList.value.some(plan => {
    const progress = Number(plan.progress) || 0
    return progress < 100 // 进度小于100%表示计划还在进行中
  })
})

// 创建计划方法
const createPlan = () => {
  if (hasActivePlan.value) {
    ElMessage.warning('您已有进行中的计划，请完成当前计划后再创建新计划')
    return
  }
  dialogVisible.value = true
}

// 提交计划
const submitPlan = async () => {
  if (!planFormRef.value) return
  
  await planFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await exercise.createFitnessPlan(planForm.value)
        if (res.data.code === 200) {
          ElMessage.success('创建成功')
          dialogVisible.value = false
          fetchPlanList()
        }
      } catch (error) {
        ElMessage.error('创建失败')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 生成计划
const generatePlan = async (row: any) => {
  const loading = ElLoading.service({
    lock: true,
    text: '正在生成计划...',
    background: 'rgba(0, 0, 0, 0.7)'
  })

  try {
    const res = await exercise.generateFitnessPlan(row.planId)
    if (res.data.code === 200) {
      ElMessage.success('计划生成成功')
      fetchPlanList()
    } else {
      ElMessage.error(res.data.msg || '计划生成失败')
    }
  } catch (error) {
    ElMessage.error('计划生成失败')
  } finally {
    loading.close()
  }
}

// 获取锻炼项目列表
const fetchExerciseList = async () => {
  try {
    const res = await exercise.getAllExercises()
    console.log('获取锻炼项目列表响应:', res)
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

// 获取计划详情
const fetchPlanDetails = async () => {
  if (!currentPlanId.value) return
  
  try {
    const res = await exercise.getFitnessPlanDetail(currentPlanId.value, {
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    
    if (res.data.code === 200) {
      planDetails.value = res.data.rows.map(detail => ({
        ...detail,
        exerciseName: getExerciseName(detail.exerciseId)
      }))
      total.value = res.data.total
    }
  } catch (error) {
    ElMessage.error('获取计划详情失败')
  }
}

// 查看详情
const viewDetails = async (row: any) => {
  currentPlanId.value = row.planId
  currentPage.value = 1 // 重置页码
  pageSize.value = 10 // 重置每页数量
  await fetchPlanDetails()
  detailDialogVisible.value = true
}

// 删除计划
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    '确定要删除这个健身计划吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      try {
        const res = await exercise.deleteFitnessPlan(row.planId)
        if (res.data.code === 200) {
          ElMessage.success('删除成功')
          fetchPlanList()
        }
      } catch (error) {
        ElMessage.error('删除失败')
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 获取强度标签
const getIntensityLabel = (intensity: string) => {
  switch (intensity) {
    case '0':
      return '低强度'
    case '1':
      return '中等强度'
    case '2':
      return '高强度'
    default:
      return '未知强度'
  }
}

// 获取强度标签类型
const getIntensityType = (intensity: string) => {
  switch (intensity) {
    case '0':
      return 'info'
    case '1':
      return 'warning'
    case '2':
      return 'danger'
    default:
      return ''
  }
}

// 处理每页显示数量变化
const handleSizeChange = (val: number) => {
  pageSize.value = val
  currentPage.value = 1 // 重置到第一页
  fetchPlanDetails()
}

// 处理页码变化
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  fetchPlanDetails()
}

onMounted(async () => {
  await fetchExerciseList()
  fetchPlanList()
})
</script>

<style lang="scss" scoped>
.fitness-plan {
  padding: 20px;

  .action-bar {
    margin-bottom: 20px;
  }

  .goal-select, .status-select {
    width: 100%;
    margin-bottom: 10px;
  }

  :deep(.el-tag) {
    width: 70px;
    text-align: center;
  }

  :deep(.el-textarea__inner) {
    font-size: 14px;
    line-height: 1.5;
  }
  
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }

  :deep(.el-button.is-disabled) {
    cursor: not-allowed;
  }
}

// 对话框内的表格样式
:deep(.el-dialog__body) {
  padding-bottom: 0;
}
</style>