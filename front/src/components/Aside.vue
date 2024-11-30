<template>
  <div class="aside">
    <el-button type="primary" @click="createVisible = true" class="action-btn">
      <el-icon><Plus /></el-icon>
      创建表
    </el-button>
    <el-button type="danger" @click="deleteVisible = true" class="action-btn">
      <el-icon><Delete /></el-icon>
      删除表
    </el-button>
    <el-button type="info" @click="tagManageVisible = true" class="action-btn">
      <el-icon><Collection /></el-icon>
      管理标签
    </el-button>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped lang="scss">
.aside {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: green;
  width: 100%;
  height: 100%;
  padding: 20px;
}

.action-btn {
  margin-bottom: 15px;
  width: 100%;
}

.table-button-container {
  margin-top: 10px;
  width: 100%;
}

.table-name-btn {
  width: 100%;
  text-align: left;
  background-color: #ffffff;
  border: 1px solid #dcdfe6;
  color: #606266;
  transition: all 0.3s;

  &:hover, &.active {
    background-color: #ecf5ff;
    color: #409eff;
  }
}
</style>
