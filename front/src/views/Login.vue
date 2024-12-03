<template>
  <div class="container">
    <!-- 登录表单 -->
    <div class="box login" :style="{ transform: loginTransform }">
      <div class="form-content">
        <h2 class="login-title">用户登录</h2>
        <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="用户名">
              <template #prefix><el-icon><User /></el-icon></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="密码">
              <template #prefix><el-icon><Lock /></el-icon></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">登录</el-button>
          </el-form-item>
        </el-form>
        <p class="btn-something">
          还没有账号？ <span class="signupbtn" @click="showSignupForm">立即注册</span>
        </p>
      </div>
    </div>

    <!-- 注册表单 -->
    <div class="box signup" :style="{ transform: signupTransform }">
      <div class="form-content">
        <h2 class="login-title">用户注册</h2>
        <el-form :model="regForm" :rules="loginRules" ref="regFormRef">
          <el-form-item prop="username">
            <el-input v-model="regForm.username" placeholder="用户名">
              <template #prefix><el-icon><User /></el-icon></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="regForm.password" type="password" placeholder="密码">
              <template #prefix><el-icon><Lock /></el-icon></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRegister" :loading="loading" class="login-button">注册</el-button>
          </el-form-item>
        </el-form>
        <p class="btn-something">
          已有账号？ <span class="loginbtn" @click="showLoginForm">立即登录</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import api from '@/api/user'
import { setItem } from '@/utils/storage'
// import '@/styles/login.scss'

const router = useRouter()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ]
}

const showLogin = ref(true)
const regForm = reactive({
  username: '',
  password: ''
})

const loginTransform = computed(() => showLogin.value ? 'rotateY(0deg)' : 'rotateY(180deg)')
const signupTransform = computed(() => showLogin.value ? 'rotateY(-180deg)' : 'rotateY(0deg)')

const showLoginForm = () => {
  showLogin.value = true
}

const showSignupForm = () => {
  showLogin.value = false
}

const handleLogin = () => {
  loginFormRef.value.validate(async (valid: boolean) => {
    if (!valid) {
      ElMessage.error('请正确填写登录信息')
      return false
    }

    loading.value = true
    try {
      const response = await api.login(loginForm.username, loginForm.password)
      if (response.data.code === 200) {
        ElMessage.success('登录成功')
        const { data } = response.data
        setItem("userId", data.id)
        setItem("token", data.token)
        setItem("name", data.name)
        setItem("lastLoginTime", data.lastLoginTime)
        await router.push('/')
      }
    } catch (error) {
      console.error('Login error:', error)
    } finally {
      loading.value = false
    }
  })
}

const handleRegister = () => {
  regFormRef.value.validate(async (valid: boolean) => {
    if (!valid) {
      ElMessage.error('请正确填写注册信息')
      return false
    }

    loading.value = true
    try {
      const response = await api.register(regForm.username, regForm.password)
      if (response.data.code === 200) {
        ElMessage.success('注册成功')
        showLoginForm() // 注册成功后切换到登录表单
      }
    } catch (error) {
      console.error('Register error:', error)
      ElMessage.error('注册失败')
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
  perspective: 1000px;
}

.box {
  position: absolute;
  width: 350px;
  padding: 35px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: transform 0.6s;
  transform-style: preserve-3d;
  backface-visibility: hidden;
}

.login, .signup {
  position: absolute;
}

.signup {
  transform: rotateY(180deg);
}

.btn-something {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
}

.loginbtn, .signupbtn {
  color: #409EFF;
  cursor: pointer;
}

.login-title {
  text-align: center;
  margin-bottom: 25px;
  font-size: 24px;
  color: #303133;
}

.login-button {
  width: 100%;
}
</style>