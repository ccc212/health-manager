<template>
  <div id="app">
    <el-container>
      <el-header v-if="!$route.meta.hideNavbar"><Navbar/></el-header>
      <el-container>
        <el-aside v-if="!$route.meta.hideNavbar"><Aside/></el-aside>
        <el-container>
          <el-main><router-view/></el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import Navbar from "@/components/Navbar.vue";
import Aside from "@/components/Aside.vue";

export default defineComponent({
  components: {
    Aside,
    Navbar
  }
});
</script>

<style lang="scss">
.el-main {
  padding: 0px !important;
}
</style>
