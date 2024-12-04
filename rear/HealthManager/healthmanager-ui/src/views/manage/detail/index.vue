<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划ID" prop="planId">
        <el-input
            v-model="queryParams.planId"
            placeholder="请输入计划ID"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="锻炼项目ID" prop="exerciseId">
        <el-input
            v-model="queryParams.exerciseId"
            placeholder="请输入锻炼项目ID"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="锻炼时长" prop="duration">
        <el-input
            v-model="queryParams.duration"
            placeholder="请输入锻炼时长"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="锻炼强度" prop="intensity">
        <el-input
            v-model="queryParams.intensity"
            placeholder="请输入锻炼强度"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="锻炼频率" prop="frequency">
        <el-input
            v-model="queryParams.frequency"
            placeholder="请输入锻炼频率"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['manage:detail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:detail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:detail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:detail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="计划详情ID" align="center" prop="detailId" />
      <el-table-column label="计划ID" align="center" prop="planId" />
      <el-table-column label="锻炼项目ID" align="center" prop="exerciseId" />
      <el-table-column label="锻炼时长" align="center" prop="duration" />
      <el-table-column label="锻炼强度" align="center" prop="intensity">
        <template #default="scope">
          <dict-tag :options="excersize_intensity" :value="scope.row.intensity"/>
        </template>
      </el-table-column>
      <el-table-column label="锻炼频率" align="center" prop="frequency" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:detail:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:detail:remove']">删除</el-button>
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

    <!-- 添加或修改健身计划详情对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="detailRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="计划ID" prop="planId">
          <el-input v-model="form.planId" placeholder="请输入计划ID" />
        </el-form-item>
        <el-form-item label="锻炼项目ID" prop="exerciseId">
          <el-input v-model="form.exerciseId" placeholder="请输入锻炼项目ID" />
        </el-form-item>
        <el-form-item label="锻炼时长" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入锻炼时长" />
        </el-form-item>
        <el-form-item label="锻炼强度" prop="intensity">
          <el-input v-model="form.intensity" placeholder="请输入锻炼强度" />
        </el-form-item>
        <el-form-item label="锻炼频率" prop="frequency">
          <el-input v-model="form.frequency" placeholder="请输入锻炼频率" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Detail">
import { listDetail, getDetail, delDetail, addDetail, updateDetail } from "@/api/manage/detail";

const { proxy } = getCurrentInstance();

const detailList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    planId: null,
    exerciseId: null,
    duration: null,
    intensity: null,
    frequency: null
  },
  rules: {
    planId: [
      { required: true, message: "计划ID不能为空", trigger: "blur" }
    ],
    exerciseId: [
      { required: true, message: "锻炼项目ID不能为空", trigger: "blur" }
    ],
    duration: [
      { required: true, message: "锻炼时长不能为空", trigger: "blur" }
    ],
    intensity: [
      { required: true, message: "锻炼强度不能为空", trigger: "blur" }
    ],
    frequency: [
      { required: true, message: "锻炼频率不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询健身计划详情列表 */
function getList() {
  loading.value = true;
  listDetail(queryParams.value).then(response => {
    detailList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    detailId: null,
    planId: null,
    exerciseId: null,
    duration: null,
    intensity: null,
    frequency: null
  };
  proxy.resetForm("detailRef");
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

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.detailId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加健身计划详情";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _detailId = row.detailId || ids.value
  getDetail(_detailId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改健身计划详情";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["detailRef"].validate(valid => {
    if (valid) {
      if (form.value.detailId != null) {
        updateDetail(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDetail(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _detailIds = row.detailId || ids.value;
  proxy.$modal.confirm('是否确认删除健身计划详情编号为"' + _detailIds + '"的数据项？').then(function() {
    return delDetail(_detailIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/detail/export', {
    ...queryParams.value
  }, `detail_${new Date().getTime()}.xlsx`)
}

getList();
</script>
