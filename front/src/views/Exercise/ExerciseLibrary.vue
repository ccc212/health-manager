<template>
    <div class="exercise-library">
      <!-- 搜索和筛选区域 -->
      <div class="filter-section">
        <el-form :inline="true" :model="queryParams">
          <el-form-item label="锻炼部位">
            <el-select 
              v-model="queryParams.partId" 
              placeholder="选择锻炼部位" 
              clearable
              :value-key="'partId'"
              class="part-select"
            >
              <el-option
                v-for="part in bodyParts"
                :key="part.partId"
                :label="part.partName"
                :value="part.partId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input
              v-model="queryParams.exerciseName"
              placeholder="搜索项目名称"
              clearable
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
            <el-button @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
  
      <!-- 锻炼项目展示区域 -->
      <div class="exercise-grid">
        <el-card v-for="exercise in exerciseList" :key="exercise.exerciseId" class="exercise-card">
          <template #header>
            <div class="card-header">
              <span>{{ exercise.exerciseName }}</span>
              <div class="part-tags">
                <el-tag 
                  v-for="part in exercise.bodyParts" 
                  :key="part.partId" 
                  size="small" 
                  class="part-tag"
                >
                  {{ part.partName }}
                </el-tag>
              </div>
            </div>
          </template>
          <div class="card-content">
            <div class="exercise-description">
              {{ exercise.description }}
            </div>
            <div class="exercise-actions">
              <el-button type="primary" size="small" @click="showDetail(exercise)">查看详情</el-button>
            </div>
          </div>
        </el-card>
      </div>
  
      <!-- 详情对话框 -->
      <el-dialog
        v-model="dialogVisible"
        :title="currentExercise?.exerciseName"
        width="50%"
      >
        <div class="exercise-detail" v-if="currentExercise">
          <h4>锻炼部位</h4>
          <p>
            <el-tag 
              v-for="part in currentExercise.bodyParts" 
              :key="part.partId" 
              size="small" 
              class="part-tag"
            >
              {{ part.partName }}
            </el-tag>
          </p>
          
          <h4>动作描述</h4>
          <p>{{ currentExercise.description }}</p>
          
          <h4>注意事项</h4>
          <!-- <p>{{ currentExercise.precautions }}</p> -->
          <p>无</p>
        </div>
      </el-dialog>
  
      <!-- 分页组件 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { ElMessage } from 'element-plus'
  import type { Exercise, BodyPart } from '@/types'
  import exercise from '@/api/exercise'
  
  // 查询参数
  const queryParams = ref({
    pageNum: 1,
    pageSize: 10,
    partId: undefined,
    exerciseName: ''
  })
  
  // 数据列表
  const exerciseList = ref<Exercise[]>([])
  const total = ref(0)
  const bodyParts = ref<BodyPart[]>([])
  const dialogVisible = ref(false)
  const currentExercise = ref<Exercise | null>(null)
  
  // 获取锻炼项目列表
  const getExerciseList = async () => {
    try {
      const res = await exercise.getExerciseList(queryParams.value)
      if (res.data.code === 200) {
        exerciseList.value = res.data.data.rows
        total.value = res.data.data.total
      }
    } catch (error) {
      ElMessage.error('获取锻炼项目列表失败')
    }
  }
  
  // 获取身体部位列表
  const getBodyParts = async () => {
    try {
      const res = await exercise.getBodyPartList()
      if (res.data.code === 200) {
        bodyParts.value = res.data.rows
      }
    } catch (error) {
      ElMessage.error('获取身体部位列表失败')
    }
  }
  
  // 根据部位ID获取部位名称
  const getPartName = (partId: number) => {
    const part = bodyParts.value.find(p => p.partId === partId)
    return part?.partName || '未知部位'
  }
  
  // 查询按钮点击事件
  const handleQuery = () => {
    queryParams.value.pageNum = 1 // 搜索时重置为第一页
    getExerciseList()
  }
  
  // 重置按钮点击事件
  const resetQuery = () => {
    queryParams.value = {
      pageNum: 1,
      pageSize: 10,
      partId: undefined,
      exerciseName: ''
    }
    getExerciseList()
  }
  
  // 显示详情
  const showDetail = (exercise: Exercise) => {
    currentExercise.value = exercise
    dialogVisible.value = true
  }
  
  // 处理每页显示数量变化
  const handleSizeChange = (val: number) => {
    queryParams.value.pageSize = val
    queryParams.value.pageNum = 1 // 切换每页数量时重置为第一页
    getExerciseList()
  }
  
  // 处理页码变化
  const handleCurrentChange = (val: number) => {
    queryParams.value.pageNum = val
    getExerciseList()
  }
  
  onMounted(() => {
    getBodyParts()
    getExerciseList()
  })
  </script>
  
  <style lang="scss" scoped>
  .exercise-library {
    padding: 20px;
  
    .filter-section {
      margin-bottom: 20px;
  
      .part-select {
        width: 100px;
      }
    }
  
    .exercise-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 20px;
    }
  
    .exercise-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
  
      .card-content {
        .exercise-info {
          margin-bottom: 10px;
        }
  
        .exercise-description {
          margin-bottom: 15px;
          color: #666;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          -webkit-box-orient: vertical;
          overflow: hidden;
        }
  
        .exercise-actions {
          text-align: right;
        }
      }
    }
  
    .exercise-detail {
      h4 {
        margin: 15px 0 5px;
        color: #333;
      }
  
      p {
        margin: 0 0 15px;
        color: #666;
        line-height: 1.6;
      }
    }
  
    .part-tags {
      display: flex;
      flex-wrap: wrap;
      gap: 5px;
      
      .part-tag {
        margin-right: 5px;
        margin-bottom: 5px;
      }
    }
  }
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
  </style>