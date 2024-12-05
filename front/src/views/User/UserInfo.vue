<template>
    <div class="user-profile">
      <el-card class="profile-card">
        <template #header>
          <div class="card-header">
            <span>个人信息</span>
            <div>
              <el-button 
                type="primary" 
                @click="toggleEdit" 
                style="margin-right: 10px"
              >
                {{ isEditing ? '取消编辑' : '编辑信息' }}
              </el-button>
              <el-button type="danger" @click="handleLogout">退出登录</el-button>
            </div>
          </div>
        </template>
  
        <div class="profile-content">
          <!-- 头像部分 -->
          <!-- <div class="avatar-container">
            <el-avatar
              :size="100"
              :src="userInfo.avatar || defaultAvatar"
              @error="handleAvatarError"
            />
            <el-upload
              class="avatar-uploader"
              action="/api/manage/user/avatar"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <el-button size="small" type="primary">更换头像</el-button>
            </el-upload>
          </div> -->
  
          <!-- 用户信息部分 -->
          <el-form
            ref="formRef"
            :model="userInfo"
            :rules="rules"
            label-width="100px"
            class="user-form"
          >
            <el-form-item label="用户账号">
              <el-input v-model="userInfo.username" disabled />
            </el-form-item>
  
            <el-form-item label="用户昵称" prop="name">
              <el-input v-model="userInfo.name" :disabled="!isEditing" />
            </el-form-item>
  
            <el-form-item label="手机号码" prop="phoneNumber">
              <el-input v-model="userInfo.phoneNumber" :disabled="!isEditing" />
            </el-form-item>
  
            <el-form-item label="用户邮箱" prop="email">
              <el-input v-model="userInfo.email" :disabled="!isEditing" />
            </el-form-item>
  
            <el-form-item label="出生日期" prop="birthDate">
              <el-date-picker
                v-model="userInfo.birthDate"
                type="date"
                placeholder="选择日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :disabled="!isEditing"
              />
            </el-form-item>
  
            <el-form-item label="账号状态">
              <el-tag :type="userInfo.status === '0' ? 'success' : 'danger'">
                {{ userInfo.status === '0' ? '正常' : '停用' }}
              </el-tag>
            </el-form-item>
  
            <el-form-item label="上次登录">
              <span>{{ userInfo.lastLoginTime }}</span>
            </el-form-item>
  
            <el-form-item label="注册时间">
              <span>{{ userInfo.createTime }}</span>
            </el-form-item>
  
            <el-form-item v-if="isEditing">
              <el-button type="primary" @click="handleUpdate">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
  
      <!-- 修改密码对话框 -->
      <el-dialog v-model="passwordDialogVisible" title="修改密码" width="500px">
        <el-form
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="100px"
        >
          <el-form-item label="原密码" prop="oldPassword">
            <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              show-password
            />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input
              v-model="passwordForm.newPassword"
              type="password"
              show-password
            />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="passwordForm.confirmPassword"
              type="password"
              show-password
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="passwordDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleUpdatePassword">确认</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive, onMounted } from 'vue'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import { useRouter } from 'vue-router'
  import type { FormInstance } from 'element-plus'
  import defaultAvatar from '@/assets/default-avatar.jpg'
  import api from '@/api/user'
  import { USER_URL } from '@/api/user'
  import { getItem, removeItem } from '@/utils/storage'
  
  const router = useRouter()
  const formRef = ref<FormInstance>()
  const passwordFormRef = ref<FormInstance>()
  const passwordDialogVisible = ref(false)
  const isEditing = ref(false)
  
  // 用户信息
  const userInfo = reactive({
    username: '',
    email: '',
    name: '',
    phoneNumber: '',
    birthDate: '',
    avatar: '',
    status: '0',
    lastLoginTime: getItem("lastLoginTime"),
    createTime: ''
  })
  
  // 表单验证规则
  const rules = {
    name: [
      { required: true, message: '请输入用户昵称', trigger: 'blur' },
      { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
    ],
    phoneNumber: [
      { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
    ],
    email: [
      { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
    ]
  }
  
  // 密码表单
  const passwordForm = reactive({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  })
  
  // 密码验证规则
  const passwordRules = {
    oldPassword: [
      { required: true, message: '请输入原密码', trigger: 'blur' }
    ],
    newPassword: [
      { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
    ],
    confirmPassword: [
      { required: true, message: '请再次输入新密码', trigger: 'blur' },
      {
        validator: (rule: any, value: string, callback: any) => {
          if (value !== passwordForm.newPassword) {
            callback(new Error('两次输入的密码不一致'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ]
  }
  
  // 切换编辑状态
  const toggleEdit = () => {
    if (isEditing.value) {
      // 取消编辑时重新获取数据
      getUserInfo()
    }
    isEditing.value = !isEditing.value
  }
  
  // 获取用户信息
  const getUserInfo = async () => {
    try {
      const userId = getItem('userId')
      if (!userId) {
        return
      }

      const response = await api.getInfo(parseInt(userId))
      const { data } = response.data
      Object.assign(userInfo, {
        ...data,
        birthDate: data.birthDate?.split('T')[0]
      })
    } catch (error) {
      console.error(error)
    }
  }
  
  // 更新用户信息
  const handleUpdate = async () => {
    if (!formRef.value) return
    
    try {
      await formRef.value.validate()
      const response = await api.updateUser({
        ...userInfo,
        userId: parseInt(getItem('userId') || '0')
      })
      
      // 只保留成功提示
      if (response.data.code === 200) {
        ElMessage.success('更新成功')
        isEditing.value = false
        getUserInfo()
      }
    } catch (error) {
      console.error(error)
    }
  }
  
  // 更新密码
  const handleUpdatePassword = async () => {
    if (!passwordFormRef.value) return
    
    try {
      await passwordFormRef.value.validate()
      const response = await fetch('/api/manage/user/password', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(passwordForm)
      })
      const data = await response.json()
      if (data.code === 200) {
        ElMessage.success('密码修改成功，请重新登录')
        passwordDialogVisible.value = false
        handleLogout()
      }
    } catch (error) {
      ElMessage.error('密码修改失败')
    }
  }
  
  // 头像上传前的验证
  const beforeAvatarUpload = (file: File) => {
    const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
    const isLt2M = file.size / 1024 / 1024 < 2
  
    if (!isJPG) {
      ElMessage.error('上传头像图片只能是 JPG/PNG 格式!')
    }
    if (!isLt2M) {
      ElMessage.error('上传头像图片大小不能超过 2MB!')
    }
    return isJPG && isLt2M
  }
  
  // 头像上传成功
  const handleAvatarSuccess = (response: any) => {
    if (response.code === 200) {
      userInfo.avatar = response.data
      ElMessage.success('头像更新成功')
    }
  }
  
  // 头像加载失败
  const handleAvatarError = () => {
    userInfo.avatar = defaultAvatar
  }
  
  // 退出登录
  const handleLogout = () => {
    ElMessageBox.confirm(
        '是否确定要退出?',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    ).then(() => {
      api.logout().then(() => {
        ElMessage({
          type: 'success',
          message: '退出成功',
        }); 
        removeItem("token")
        removeItem("name")
        removeItem("lastLoginTime")
        removeItem("userId")
        router.push({ path: '/login' })
      })
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: '取消',
      })
    })
  }
  
  onMounted(() => {
    getUserInfo()
  })
  </script>
  
  <style scoped lang="scss">
  .user-profile {
    padding: 20px;
    
    .profile-card {
      max-width: 800px;
      margin: 0 auto;
      
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      
      .profile-content {
        .avatar-container {
          text-align: center;
          margin-bottom: 30px;
          
          .avatar-uploader {
            margin-top: 10px;
          }
        }
        
        .user-form {
          max-width: 500px;
          margin: 0 auto;
        }
      }
    }
  }
  
  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
  }
  </style>