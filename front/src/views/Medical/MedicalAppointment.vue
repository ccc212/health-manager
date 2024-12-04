<template>
  <div class="medical-appointment">
    <!-- 科室选择 -->
    <el-select v-model="selectedDepartment" placeholder="请选择科室" @change="handleDepartmentChange">
      <el-option
        v-for="dept in departments"
        :key="dept"
        :label="dept"
        :value="dept"
      />
    </el-select>

    <!-- 医生列表 -->
    <el-table :data="doctorList" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="name" label="医生姓名" />
      <el-table-column prop="title" label="职称" />
      <el-table-column prop="department" label="科室" />
      <el-table-column prop="introduction" label="简介" show-overflow-tooltip />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="showSchedule(row)">
            预约
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 预约对话框 -->
    <el-dialog v-model="dialogVisible" title="预约挂号" width="500px">
      <el-form :model="appointmentForm" label-width="100px" :rules="rules" ref="appointmentFormRef">
        <el-form-item label="就诊日期" prop="date">
          <el-date-picker
            v-model="appointmentForm.date"
            type="date"
            placeholder="选择日期"
            :disabled-date="disabledDate"
          />
        </el-form-item>
        <el-form-item label="就诊时段" prop="period">
          <el-select v-model="appointmentForm.period" placeholder="选择时段">
            <el-option label="上午" value="1" />
            <el-option label="下午" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="病情描述" prop="description">
          <el-input
            v-model="appointmentForm.description"
            type="textarea"
            placeholder="请简要描述您的症状"
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
import dayjs from 'dayjs'

const departments = ['内科', '外科', '骨科', '儿科', '妇科']
const selectedDepartment = ref('')
const doctorList = ref([])
const dialogVisible = ref(false)
const submitting = ref(false)
const appointmentFormRef = ref<FormInstance>()

const appointmentForm = ref({
  doctorId: '',
  date: '',
  period: '',
  description: ''
})

const rules = {
  date: [{ required: true, message: '请选择就诊日期', trigger: 'change' }],
  period: [{ required: true, message: '请选择就诊时段', trigger: 'change' }],
  description: [{ required: true, message: '请描述病情', trigger: 'blur' }]
}

// 获取医生列表
const handleDepartmentChange = async (department: string) => {
  try {
    const res = await medical.getDoctorList({ department })
    doctorList.value = res.data.rows
  } catch (error) {
    ElMessage.error('获取医生列表失败')
  }
}

// 显示预约对话框
const showSchedule = (doctor: any) => {
  appointmentForm.value.doctorId = doctor.doctorId
  dialogVisible.value = true
}

// 禁用过去的日期
const disabledDate = (time: Date) => {
  return time.getTime() < Date.now() - 8.64e7
}

// 提交预约
const submitAppointment = async () => {
  if (!appointmentFormRef.value) return
  
  await appointmentFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await medical.createAppointment(appointmentForm.value)
        if (res.data.code === 200) {
          ElMessage.success('预约成功')
          dialogVisible.value = false
        }
      } catch (error) {
        ElMessage.error('预约失败')
      } finally {
        submitting.value = false
      }
    }
  })
}

onMounted(() => {
  // 可以在这里加载默认科室的医生列表
})
</script>

<style lang="scss" scoped>
.medical-appointment {
  padding: 20px;

  .el-select {
    width: 200px;
  }

  :deep(.el-dialog__body) {
    padding: 20px;
  }
}
</style>