<template>
  <nav class="navbar" :class="{ hidden: isHidden }">
    <ul>
      <li><router-link to="/empty">首页</router-link></li>
      <li><router-link to="/exercise">健身</router-link></li>
      <li><router-link to="/psychology">心理</router-link></li>
      <li><router-link to="/medical">医疗</router-link></li>
      <li v-if="$route.meta.excludeHome">
        <span @click="goBack">返回</span>
      </li>
      <li class="login">
        <div  @click="toggleLogout" class="user-container">
          <span style="margin-right: 10px">上次登录： {{ lastLoginTime }}  </span>
          <span style="margin-right: 10px">欢迎回来： {{ name }}</span>
          <span v-if="isLogoutVisible" @click="logout">退出登录</span>
        </div>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import { computed, defineComponent, ref, onMounted, onBeforeUnmount } from 'vue';
import { ElMessage, ElMessageBox } from "element-plus";
import Login from "@/views/Login.vue";
import { useRouter } from "vue-router";
import api from '@/api/user';
import { getItem, removeItem } from '@/utils/storage';

export default defineComponent({
  name: 'Navbar',
  components: { Login },
  setup() {
    const name = getItem("name");
    const lastLoginTime = getItem("lastLoginTime");
    const isLogoutVisible = ref(false);
    const router = useRouter();
    const isHidden = ref(false);

    const handleScroll = () => {
      const scrollTop = document.documentElement.scrollTop;
      if (scrollTop > 0) {
        isHidden.value = true;
      } else {
        isHidden.value = false;
      }
    };

    onMounted(() => {
      window.addEventListener('scroll', handleScroll);
    });

    onBeforeUnmount(() => {
      window.removeEventListener('scroll', handleScroll);
    });

    const goBack = () => {
      router.back();
    };

    const toggleLogout = () => {
      isLogoutVisible.value = !isLogoutVisible.value;
    };

    const logout = () => {
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
          isLogoutVisible.value = false;
          router.push({ path: '/login' })
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消',
        })
      })
    };

    return {
      name,
      lastLoginTime,
      isLogoutVisible,
      toggleLogout,
      logout,
      goBack,
      isHidden
    };
  }
});
</script>

<style lang="scss">
.navbar {
  //background-color: transparent;
  background-color: red;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 60px;
  padding: 1rem;
  z-index: 1000;
  transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;

  &.hidden {
    transform: translateY(-100%);
    opacity: 0;
  }

  ul {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
    justify-content: left;

    li {
      margin: 0 1rem;

      &.login {
        margin-left: auto;
      }

      .user-container {
        display: flex;
        align-items: center;
        cursor: pointer;
        color: white;
        font-weight: bold;
      }
    }
  }
}

.router-link-active {
  text-decoration: none;
  color: #fff;
}

a {
  text-decoration: none;
  color: #fff;
}
</style>
