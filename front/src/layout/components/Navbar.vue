<template>
  <nav class="navbar">
    <ul>
      <li><router-link to="/empty">首页</router-link></li>
      <li><router-link to="/exercise">健身</router-link></li>
      <li><router-link to="/psychology">心理</router-link></li>
      <li><router-link to="/medical">医疗</router-link></li>
      <li v-if="$route.meta.excludeHome">
        <span @click="goBack">返回</span>
      </li>
      <li class="login">
        <div @click="goToProfile" class="user-container">
          <span style="margin-right: 10px">欢迎回来： {{ name }}</span>
        </div>
      </li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import { getItem } from '@/utils/storage';

const name = getItem("name");
const router = useRouter();

const goBack = () => {
  router.back();
};

const goToProfile = () => {
  router.push({ path: '/profile/info' })
};
</script>

<style lang="scss" scoped>
.navbar {
  background-color: #1f2d3d;
  width: 100%;
  height: 60px;
  padding: 1rem;

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
