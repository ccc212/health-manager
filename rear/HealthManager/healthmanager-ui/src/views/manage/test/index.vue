<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总分" prop="totalScore">
        <el-input
          v-model="queryParams.totalScore"
          placeholder="请输入总分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试日期" prop="testDate">
        <el-date-picker clearable
          v-model="queryParams.testDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择测试日期">
        </el-date-picker>
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
          v-hasPermi="['manage:test:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:test:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:test:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:test:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="testList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="测试ID" align="center" prop="testId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="总分" align="center" prop="totalScore" />
      <el-table-column label="AI分析报告" align="center" prop="aiAnalysis" />
      <el-table-column label="测试日期" align="center" prop="testDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.testDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:test:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:test:remove']">删除</el-button>
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

    <!-- 添加或修改心理测试记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="testRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="总分" prop="totalScore">
          <el-input v-model="form.totalScore" placeholder="请输入总分" />
        </el-form-item>
        <el-form-item label="AI分析报告" prop="aiAnalysis">
          <el-input v-model="form.aiAnalysis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="测试日期" prop="testDate">
          <el-date-picker clearable
            v-model="form.testDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择测试日期">
          </el-date-picker>
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

<script setup name="Test">
import { listTest, getTest, delTest, addTest, updateTest } from "@/api/manage/test";

const { proxy } = getCurrentInstance();

const testList = ref([]);
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
    userId: null,
    totalScore: null,
    aiAnalysis: null,
    testDate: null,
  },
  rules: {
    userId: [
      { required: true, message: "用户ID不能为空", trigger: "blur" }
    ],
    totalScore: [
      { required: true, message: "总分不能为空", trigger: "blur" }
    ],
    testDate: [
      { required: true, message: "测试日期不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询心理测试记录列表 */
function getList() {
  loading.value = true;
  listTest(queryParams.value).then(response => {
    testList.value = response.rows;
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
    testId: null,
    userId: null,
    totalScore: null,
    aiAnalysis: null,
    testDate: null,
    createTime: null,
    updateTime: null
  };
  proxy.resetForm("testRef");
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
  ids.value = selection.map(item => item.testId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加心理测试记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _testId = row.testId || ids.value
  getTest(_testId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改心理测试记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["testRef"].validate(valid => {
    if (valid) {
      if (form.value.testId != null) {
        updateTest(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTest(form.value).then(response => {
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
  const _testIds = row.testId || ids.value;
  proxy.$modal.confirm('是否确认删除心理测试记录编号为"' + _testIds + '"的数据项？').then(function() {
    return delTest(_testIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/test/export', {
    ...queryParams.value
  }, `test_${new Date().getTime()}.xlsx`)
}

getList();
</script>
