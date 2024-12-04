<template>
    <div class="fitness-record">
      <div class="record-header">
        <el-button type="primary" @click="showAddDialog">添加记录</el-button>
      </div>
  
      <!-- 记录列表 -->
      <el-table :data="recordList" style="width: 100%" v-loading="loading">
        <el-table-column prop="recordDate" label="日期" width="180" />
        <el-table-column prop="exerciseType" label="运动类型" width="180" />
        <el-table-column prop="duration" label="时长(分钟)" width="120" />
        <el-table-column prop="intensity" label="强度" width="120" />
        <el-table-column prop="calories" label="消耗卡路里" width="120" />
        <el-table-column prop="notes" label="备注" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button link type="primary" @click="editRecord(scope.row)">编辑</el-button>
            <el-button link type="danger" @click="deleteRecord(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          @current-change="handlePageChange"
          layout="total, prev, pager, next"
        />
      </div>
  
      <!-- 添加/编辑记录对话框 -->
      <el-dialog
        v-model="dialogVisible"
        :title="dialogType === 'add' ? '添加记录' : '编辑记录'"
        width="500px"
      >
        <el-form :model="recordForm" label-width="100px" ref="recordFormRef">
          <el-form-item label="日期" prop="recordDate">
            <el-date-picker
              v-model="recordForm.recordDate"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          
          <el-form-item label="运动类型" prop="exerciseType">
            <el-select v-model="recordForm.exerciseType" placeholder="请选择运动类型">
              <el-option label="跑步" value="running" />
              <el-option label="游泳" value="swimming" />
              <el-option label="力量训练" value="strength" />
              <el-option label="瑜伽" value="yoga" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
  
          <el-form-item label="运动时长" prop="duration">
            <el-input-number v-model="recordForm.duration" :min="1" :max="480" />
          </el-form-item>
  
          <el-form-item label="运动强度" prop="intensity">
            <el-select v-model="recordForm.intensity" placeholder="请选择运动强度">
              <el-option label="低强度" value="low" />
              <el-option label="中等强度" value="medium" />
              <el-option label="高强度" value="high" />
            </el-select>
          </el-form-item>
  
          <el-form-item label="消耗卡路里" prop="calories">
            <el-input-number v-model="recordForm.calories" :min="0" />
          </el-form-item>
  
          <el-form-item label="备注" prop="notes">
            <el-input
              v-model="recordForm.notes"
              type="textarea"
              :rows="2"
              placeholder="请输入备注信息"
            />
          </el-form-item>
        </el-form>
        
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitRecord">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive, onMounted } from 'vue'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import type { FormInstance } from 'element-plus'
  import exerciseApi from '@/api/exercise'
  
  const loading = ref(false)
  const recordList = ref([])
  const currentPage = ref(1)
  const pageSize = ref(10)
  const total = ref(0)
  const dialogVisible = ref(false)
  const dialogType = ref<'add' | 'edit'>('add')
  const recordFormRef = ref<FormInstance>()
  
  const recordForm = reactive({
    id: '',
    recordDate: '',
    exerciseType: '',
    duration: 30,
    intensity: '',
    calories: 0,
    notes: ''
  })
  
  // 获取记录列表
  const getRecordList = async () => {
    loading.value = true
    try {
      const res = await exerciseApi.getFitnessRecordList({
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
  
  // 显示添加对话框
  const showAddDialog = () => {
    dialogType.value = 'add'
    Object.keys(recordForm).forEach(key => {
      recordForm[key] = ''
    })
    recordForm.duration = 30
    recordForm.calories = 0
    dialogVisible.value = true
  }
  
  // 编辑记录
  const editRecord = (row: any) => {
    dialogType.value = 'edit'
    Object.keys(recordForm).forEach(key => {
      recordForm[key] = row[key]
    })
    dialogVisible.value = true
  }
  
  // 删除记录
  const deleteRecord = (row: any) => {
    ElMessageBox.confirm('确认删除该记录吗？', '提示', {
      type: 'warning'
    }).then(async () => {
      try {
        await exerciseApi.deleteFitnessRecord([row.id])
        ElMessage.success('删除成功')
        getRecordList()
      } catch (error) {
        ElMessage.error('删除失败')
      }
    })
  }
  
  // 提交记录
  const submitRecord = async () => {
    if (!recordFormRef.value) return
    
    try {
      const valid = await recordFormRef.value.validate()
      if (valid) {
        if (dialogType.value === 'add') {
          await exerciseApi.addFitnessRecord(recordForm)
        } else {
          await exerciseApi.updateFitnessRecord(recordForm)
        }
        ElMessage.success(dialogType.value === 'add' ? '添加成功' : '更新成功')
        dialogVisible.value = false
        getRecordList()
      }
    } catch (error) {
      ElMessage.error(dialogType.value === 'add' ? '添加失败' : '更新失败')
    }
  }
  
  // 页码改变
  const handlePageChange = (page: number) => {
    currentPage.value = page
    getRecordList()
  }
  
  onMounted(() => {
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
  }
  
  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
  }
  </style>