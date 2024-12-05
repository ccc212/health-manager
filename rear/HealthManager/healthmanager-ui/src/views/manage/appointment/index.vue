<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入患者姓名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="预约状态" clearable style="width: 200px">
          <el-option
            v-for="dict in appointmentStatusOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="appointmentList">
      <el-table-column label="预约编号" align="center" prop="appointmentId" />
      <el-table-column label="患者姓名" align="center" prop="userName" />
      <el-table-column label="预约时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊时段" align="center">
        <template #default="scope">
          <dict-tag :options="periodOptions" :value="scope.row.period"/>
        </template>
      </el-table-column>
      <el-table-column label="病情描述" align="center" prop="description" :show-overflow-tooltip="true"/>
      <el-table-column label="预约状态" align="center">
        <template #default="scope">
          <dict-tag :options="appointmentStatusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            v-if="scope.row.status === '0'"
            type="success"
            link
            icon="Check"
            @click="handleComplete(scope.row)"
            v-hasPermi="['manage:appointment:edit']"
          >完成就诊</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            type="danger"
            link
            icon="Close"
            @click="handleCancel(scope.row)"
            v-hasPermi="['manage:appointment:edit']"
          >取消预约</el-button>
          <el-button
            type="primary"
            link
            icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['manage:appointment:query']"
          >查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 查看详情对话框 -->
    <el-dialog title="预约详情" v-model="viewDialog" width="500px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="患者姓名">{{ form.userName }}</el-descriptions-item>
        <el-descriptions-item label="预约时间">{{ parseTime(form.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="就诊时段">
          <dict-tag :options="periodOptions" :value="form.period"/>
        </el-descriptions-item>
        <el-descriptions-item label="病情描述">{{ form.description }}</el-descriptions-item>
        <el-descriptions-item label="预约状态">
          <dict-tag :options="appointmentStatusOptions" :value="form.status"/>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="viewDialog = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Appointment">
import { listAppointment, getAppointment, updateAppointment } from "@/api/manage/appointment";

const { proxy } = getCurrentInstance();

const appointmentStatusOptions = ref([
  { label: '待就诊', value: '0' },
  { label: '已完成', value: '1' },
  { label: '已取消', value: '2' }
]);

const periodOptions = ref([
  { label: '上午', value: '1' },
  { label: '下午', value: '2' },
  { label: '晚上', value: '3' }
]);

// 遮罩层
const loading = ref(false);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 预约表格数据
const appointmentList = ref([]);
// 查看对话框
const viewDialog = ref(false);
// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  userName: undefined,
  status: undefined
});
// 表单参数
const form = ref({});

/** 查询预约列表 */
function getList() {
  loading.value = true;
  listAppointment(queryParams.value).then(response => {
    appointmentList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 完成就诊操作 */
function handleComplete(row) {
  proxy.$modal.confirm('是否确认完成该预约的就诊？').then(function() {
    return updateAppointment({ 
      appointmentId: row.appointmentId,
      status: '1'
    });
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("操作成功");
  }).catch(() => {});
}

/** 取消预约操作 */
function handleCancel(row) {
  proxy.$modal.confirm('是否确认取消该预约？').then(function() {
    return updateAppointment({ 
      appointmentId: row.appointmentId,
      status: '2'
    });
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("操作成功");
  }).catch(() => {});
}

/** 查看详情按钮操作 */
function handleView(row) {
  getAppointment(row.appointmentId).then(response => {
    form.value = response.data;
    viewDialog.value = true;
  });
}

getList();
</script>
