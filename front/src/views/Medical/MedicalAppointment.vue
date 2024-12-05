<template>
  <div class="medical-appointment">
    <!-- 科室选择 -->
    <div class="filter-container">
      <el-select v-model="selectedDepartment" placeholder="选择科室" clearable @change="handleDepartmentChange">
        <el-option
          v-for="dept in departments"
          :key="dept"
          :label="dept"
          :value="dept"
        />
      </el-select>
    </div>

    <!-- 医生列表 -->
    <el-table :data="doctorList" v-loading="loading" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="name" label="医生姓名" />
      <el-table-column prop="title" label="职称" />
      <el-table-column prop="department" label="科室" />
      <el-table-column prop="introduction" label="简介" show-overflow-tooltip />
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="showSchedule(row)">
            预约
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 预约对话框 -->
    <el-dialog v-model="dialogVisible" title="预约挂号" width="500px" @closed="resetForm">
      <el-form 
        ref="appointmentFormRef"
        :model="appointmentForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="医生姓名">
          <span>{{ selectedDoctor?.name }}</span>
        </el-form-item>
        <el-form-item label="就诊日期" prop="date">
          <el-date-picker
            v-model="appointmentForm.date"
            type="date"
            placeholder="选择日期"
            :disabled-date="disabledDate"
            value-format="YYYY-MM-DD"
            @change="handleDateChange"
          />
        </el-form-item>
        <el-form-item label="就诊时段" prop="period">
          <el-select 
            v-model="appointmentForm.period"
            placeholder="选择时段"
            :disabled="!appointmentForm.date"
          >
            <el-option
              v-for="schedule in availableSchedules"
              :key="schedule.period"
              :label="formatPeriod(schedule.period)"
              :value="schedule.period"
              :disabled="schedule.quota <= 0 || schedule.status !== '0'"
            >
              <span>{{ formatPeriod(schedule.period) }}</span>
              <span style="float: right; color: #8c939d">
                剩余号{{ schedule.quota }}个
              </span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病情描述" prop="description">
          <el-input
            v-model="appointmentForm.description"
            type="textarea"
            placeholder="请简要描述您的症状"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAppointment" :loading="submitting">
          确认预约
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import * as medical from '@/api/medical'
import { getItem } from '@/utils/storage'

const loading = ref(false)
const departments = ['内科', '外科', '骨科', '儿科', '妇科']
const selectedDepartment = ref('')
const doctorList = ref([])
const dialogVisible = ref(false)
const submitting = ref(false)
const appointmentFormRef = ref<FormInstance>()
const selectedDoctor = ref<any>(null)
const availableSchedules = ref([])

const appointmentForm = ref({
  doctorId: 0,
  date: '',
  period: '',
  description: '',
  userId: getItem('userId')
})

const rules = {
  date: [{ required: true, message: '请选择就诊日期', trigger: 'change' }],
  period: [{ required: true, message: '请选择就诊时段', trigger: 'change' }],
  description: [{ required: true, message: '请描述病情', trigger: 'blur' }]
}

// 获取医生列表
const getDoctors = async () => {
  loading.value = true
  try {
    const res = await medical.getDoctorList({ 
      department: selectedDepartment.value,
      status: '0' // 只查询在职医生
    })
    doctorList.value = res.data.rows
  } catch (error) {
    ElMessage.error('获取医生列表失败')
  } finally {
    loading.value = false
  }
}

// 处理科室变化
const handleDepartmentChange = () => {
  getDoctors()
}

// 格式化时段显示
const formatPeriod = (period: string) => {
  const periodMap: Record<string, string> = {
    '1': '上午',
    '2': '下午'
  }
  return periodMap[period] || period
}

// 禁用今天之前的日期
const disabledDate = (time: Date) => {
  return time.getTime() < Date.now() - 8.64e7
}

// 显示预约对话框
const showSchedule = (doctor: any) => {
  selectedDoctor.value = doctor
  appointmentForm.value.doctorId = doctor.doctorId
  dialogVisible.value = true
}

// 处理日期变化
const handleDateChange = async () => {
  if (!appointmentForm.value.date || !selectedDoctor.value) return

  try {
    const res = await medical.getDoctorSchedule(
      selectedDoctor.value.doctorId,
      appointmentForm.value.date
    )
    availableSchedules.value = res.data
    appointmentForm.value.period = ''
  } catch (error) {
    ElMessage.error('获取排班信息失败')
  }
}

// 重置表单
const resetForm = () => {
  if (appointmentFormRef.value) {
    appointmentFormRef.value.resetFields()
  }
  appointmentForm.value = {
    doctorId: 0,
    date: '',
    period: '',
    description: '',
    userId: getItem('userId')
  }
  availableSchedules.value = []
  selectedDoctor.value = null
}

// 提交预约
const submitAppointment = async () => {
  if (!appointmentFormRef.value) return
  
  await appointmentFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        // 获取对应的排班ID
        const schedule = availableSchedules.value.find(
          s => s.period === appointmentForm.value.period
        )
        if (!schedule) {
          ElMessage.error('无效的排班信息')
          return
        }
        
        appointmentForm.value.scheduleId = schedule.scheduleId
        const res = await medical.createAppointment(appointmentForm.value)
        ElMessage.success('预约成功')
        dialogVisible.value = false
      } catch (error) {
        ElMessage.error('预约失败')
      } finally {
        submitting.value = false
      }
    }
  })
}

onMounted(() => {
  getDoctors()
})
</script>

<style lang="scss" scoped>
.medical-appointment {
  padding: 20px;

  .filter-container {
    margin-bottom: 20px;
    
    .el-select {
      width: 200px;
    }
  }
}
</style>