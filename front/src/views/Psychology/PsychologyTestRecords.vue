<template>
  <div class="test-records-container">
    <!-- 测试记录列表 -->
    <el-card>
      <template #header>
        <div class="card-header">
          <span>心理测试记录</span>
        </div>
      </template>
      
      <el-table :data="testRecords" style="width: 100%">
        <el-table-column prop="testDate" label="测试日期" width="180">
          <template #default="scope">
            {{ dayjs(scope.row.createTime).format('YYYY-MM-DD HH:mm:ss') }}
          </template>
        </el-table-column>
        <el-table-column prop="totalScore" label="得分" width="100">
          <template #default="scope">
            {{ scope.row.totalScore.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button @click="viewDetails(scope.row)" type="primary" size="small">
              查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 30, 50]"
          layout="total, sizes, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 详情对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="测试详情"
      width="60%"
      :before-close="handleClose"
    >
      <div v-if="currentRecord" class="test-details">
        <div class="detail-item">
          <h3>测试日期：{{ dayjs(currentRecord.createTime).format('YYYY-MM-DD HH:mm:ss') }}</h3>
          <h3>总分：{{ currentRecord.totalScore.toFixed(2) }}</h3>
        </div>
        <div class="analysis" v-html="formattedAnalysis"></div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {ref, computed, onMounted} from 'vue'
import { ElMessage } from 'element-plus'
import psychology from '@/api/psychology'
import MarkdownIt from 'markdown-it'
import dayjs from 'dayjs'

const md = new MarkdownIt()

interface TestRecord {
  testId: number
  testDate: string
  createTime: string
  totalScore: number
  aiAnalysis: string
}

const testRecords = ref<TestRecord[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const currentRecord = ref<TestRecord | null>(null)

// 格式化 Markdown 分析结果
const formattedAnalysis = computed(() => {
  if (!currentRecord.value) return ''
  return md.render(currentRecord.value.aiAnalysis)
})

// 获取测试记录
const fetchTestRecords = async () => {
  try {
    const res = await psychology.getTestRecords()
    testRecords.value = res.data.rows
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取测试记录失败')
  }
}

// 查看详情
const viewDetails = (record: TestRecord) => {
  currentRecord.value = record
  dialogVisible.value = true
}

// 处理对话框关闭
const handleClose = () => {
  dialogVisible.value = false
  currentRecord.value = null
}

// 处理分页大小变化
const handleSizeChange = (val: number) => {
  pageSize.value = val
  fetchTestRecords()
}

// 处理页码变化
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  fetchTestRecords()
}

// 初始化加载数据
onMounted(() => {
  fetchTestRecords()
})
</script>

<style scoped lang="scss">
.test-records-container {
  padding: 20px;

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .test-details {
    .detail-item {
      margin-bottom: 20px;
      h3 {
        margin: 10px 0;
      }
    }

    .analysis {
      :deep(h1, h2, h3) {
        margin: 16px 0;
      }
      :deep(p) {
        margin: 8px 0;
        line-height: 1.6;
      }
      :deep(ul, ol) {
        padding-left: 20px;
        margin: 8px 0;
      }
    }
  }
}
</style>