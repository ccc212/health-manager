<template>
    <div class="fitness-plan">
      <el-card class="plan-form">
        <template #header>
          <div class="card-header">
            <span>个性化健身计划定制</span>
          </div>
        </template>

        <el-form :model="planForm" label-width="120px" ref="formRef">
          <el-form-item label="身高(cm)" prop="height">
            <el-input-number v-model="planForm.height" :min="100" :max="250" />
          </el-form-item>

          <el-form-item label="体重(kg)" prop="weight">
            <el-input-number v-model="planForm.weight" :min="30" :max="200" />
          </el-form-item>

          <el-form-item label="年龄" prop="age">
            <el-input-number v-model="planForm.age" :min="12" :max="100" />
          </el-form-item>

          <el-form-item label="健身目标" prop="goal">
            <el-select v-model="planForm.goal" placeholder="请选择健身目标">
              <el-option label="减重" value="weight_loss" />
              <el-option label="增肌" value="muscle_gain" />
              <el-option label="保持健康" value="maintain_health" />
            </el-select>
          </el-form-item>

          <el-form-item label="运动频率" prop="frequency">
            <el-select v-model="planForm.frequency" placeholder="请选择每周运动频率">
              <el-option label="每周1-2次" value="1-2" />
              <el-option label="每周3-4次" value="3-4" />
              <el-option label="每周5次以上" value="5+" />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="generatePlan">生成计划</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 显示生成的健身计划 -->
      <el-card class="plan-details" v-if="generatedPlan">
        <template #header>
          <div class="card-header">
            <span>您的个性化健身计划</span>
            <el-button type="primary" link @click="savePlan">保存计划</el-button>
          </div>
        </template>

        <div class="plan-content">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in generatedPlan.activities"
              :key="index"
              :timestamp="activity.time"
            >
              <h4>{{ activity.name }}</h4>
              <p>{{ activity.description }}</p>
              <ul>
                <li v-for="(detail, dIndex) in activity.details" :key="dIndex">
                  {{ detail }}
                </li>
              </ul>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-card>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive } from 'vue'
  import { ElMessage } from 'element-plus'
  import type { FormInstance } from 'element-plus'
  import exerciseApi from '@/api/exercise'
  
  const formRef = ref<FormInstance>()
  const planForm = reactive({
    height: 170,
    weight: 65,
    age: 25,
    goal: '',
    frequency: ''
  })
  
  const generatedPlan = ref(null)
  
  const generatePlan = async () => {
    try {
      const response = await exerciseApi.addFitnessPlan(planForm)
      if (response.data.code === 200) {
        generatedPlan.value = response.data.data
        ElMessage.success('健身计划生成成功！')
      }
    } catch (error) {
      ElMessage.error('生成计划失败，请重试')
    }
  }
  
  const savePlan = async () => {
    try {
      if (generatedPlan.value) {
        await exerciseApi.updateFitnessPlan({
          ...planForm,
          planDetails: generatedPlan.value
        })
        ElMessage.success('计划保存成功！')
      }
    } catch (error) {
      ElMessage.error('保存失败，请重试')
    }
  }
  
  const resetForm = () => {
    if (formRef.value) {
      formRef.value.resetFields()
      generatedPlan.value = null
    }
  }
  </script>
  
  <style scoped lang="scss">
  .fitness-plan {
    padding: 20px;
    
    .plan-form, .plan-details {
      margin-bottom: 20px;
      
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
    }
    
    .plan-content {
      margin-top: 20px;
      
      ul {
        margin: 10px 0;
        padding-left: 20px;
      }
    }
  }
  </style>