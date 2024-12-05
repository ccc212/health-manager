<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入医生姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入职称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室" prop="department">
        <el-input
          v-model="queryParams.department"
          placeholder="请输入科室"
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
          v-hasPermi="['manage:doctor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:doctor:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:doctor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:doctor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doctorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="医生ID" align="center" prop="doctorId" />
      <el-table-column label="医生姓名" align="center" prop="name" />
      <el-table-column label="职称" align="center" prop="title" />
      <el-table-column label="科室" align="center" prop="department" />
      <el-table-column label="个人简介" align="center" prop="introduction" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:doctor:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:doctor:remove']">删除</el-button>
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

    <!-- 添加或修改医生信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="doctorRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="医生姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入医生姓名" />
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="form.title" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="科室" prop="department">
          <el-input v-model="form.department" placeholder="请输入科室" />
        </el-form-item>
        <el-form-item label="个人简介" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
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

<script setup name="Doctor">
import { listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor } from "@/api/manage/doctor";

const { proxy } = getCurrentInstance();

const doctorList = ref([]);
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
    name: null,
    title: null,
    department: null,
    introduction: null,
    status: null,
  },
  rules: {
    name: [
      { required: true, message: "医生姓名不能为空", trigger: "blur" }
    ],
    title: [
      { required: true, message: "职称不能为空", trigger: "blur" }
    ],
    department: [
      { required: true, message: "科室不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询医生信息列表 */
function getList() {
  loading.value = true;
  listDoctor(queryParams.value).then(response => {
    doctorList.value = response.rows;
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
    doctorId: null,
    name: null,
    title: null,
    department: null,
    introduction: null,
    status: null,
    createTime: null,
    updateTime: null
  };
  proxy.resetForm("doctorRef");
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
  ids.value = selection.map(item => item.doctorId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加医生信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _doctorId = row.doctorId || ids.value
  getDoctor(_doctorId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改医生信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["doctorRef"].validate(valid => {
    if (valid) {
      if (form.value.doctorId != null) {
        updateDoctor(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDoctor(form.value).then(response => {
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
  const _doctorIds = row.doctorId || ids.value;
  proxy.$modal.confirm('是否确认删除医生信息编号为"' + _doctorIds + '"的数据项？').then(function() {
    return delDoctor(_doctorIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/doctor/export', {
    ...queryParams.value
  }, `doctor_${new Date().getTime()}.xlsx`)
}

getList();
</script>
