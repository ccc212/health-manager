<template>
  <div class="aside">
    <template v-if="isProfileRoute">
      <el-menu
        :default-active="activeMenu"
        class="profile-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/profile/info">
          <el-icon><User /></el-icon>
          <span>用户信息</span>
        </el-menu-item>
        <el-menu-item index="/profile/physical">
          <el-icon><DataLine /></el-icon>
          <span>身体数据</span>
        </el-menu-item>
      </el-menu>
    </template>
    
    <template v-if="isPsychologyRoute">
      <el-menu
        :default-active="activeMenu"
        class="profile-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/psychology/counsel">
          <el-icon><ChatLineRound /></el-icon>
          <span>AI心理咨询</span>
        </el-menu-item>
        <el-menu-item index="/psychology/test">
          <el-icon><DocumentChecked /></el-icon>
          <span>心理测试</span>
        </el-menu-item>
        <el-menu-item index="/psychology/records">
          <el-icon><DocumentChecked /></el-icon>
          <span>测试记录</span>
        </el-menu-item>
      </el-menu>
    </template>
    
    <template v-if="isExerciseRoute">
      <el-menu
        :default-active="activeMenu"
        class="profile-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/exercise/library">
          <el-icon><List /></el-icon>
          <span>锻炼项目库</span>
        </el-menu-item>
        <el-menu-item index="/exercise/plan">
          <el-icon><Calendar /></el-icon>
          <span>健身计划</span>
        </el-menu-item>
        <el-menu-item index="/exercise/record">
          <el-icon><Memo /></el-icon>
          <span>锻炼记录</span>
        </el-menu-item>
      </el-menu>
    </template>
    
    <template v-if="isMedicalRoute">
      <el-menu
        :default-active="activeMenu"
        class="profile-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/medical/appointment">
          <el-icon><Calendar /></el-icon>
          <span>预约挂号</span>
        </el-menu-item>
        <el-menu-item index="/medical/record">
          <el-icon><Document /></el-icon>
          <span>就诊记录</span>
        </el-menu-item>
      </el-menu>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { User, DataLine, ChatLineRound, DocumentChecked, Calendar, Memo, List, Guide } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const isProfileRoute = computed(() => {
  return route.path.startsWith('/profile')
})

const isPsychologyRoute = computed(() => {
  return route.path.startsWith('/psychology')
})

const isExerciseRoute = computed(() => route.path.startsWith('/exercise'))

const isMedicalRoute = computed(() => {
  return route.path.startsWith('/medical')
})

const activeMenu = computed(() => route.path)

const handleSelect = (path: string) => {
  router.push(path)
}
</script>

<style scoped lang="scss">
.aside {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #304156;
  width: 100%;
  height: 100%;
  padding: 20px;
}

.profile-menu {
  width: 100%;
  border-right: none;
  background-color: transparent;
  
  :deep(.el-menu-item) {
    color: white;
    
    &:hover, &.is-active {
      background-color: rgba(255, 255, 255, 0.1);
    }
    
    .el-icon {
      color: white;
    }
  }
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
