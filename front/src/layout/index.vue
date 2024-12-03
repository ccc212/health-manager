<template>
  <div class="app-wrapper">
    <Navbar class="navbar-container" />
    <div class="main-body">
      <aside class="sidebar-container">
        <Aside />
      </aside>
      <div class="main-container">
        <div class="main-content">
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Navbar from './components/Navbar.vue'
import Aside from './components/Aside.vue'
</script>

<style lang="scss" scoped>
.app-wrapper {
  height: 100vh;
  width: 100%;
}

.navbar-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
}

.main-body {
  display: flex;
  padding-top: 60px;
  height: 100vh;
}

.sidebar-container {
  width: 200px;
  height: calc(100vh - 60px);
  position: fixed;
  left: 0;
  top: 60px;
}

.main-container {
  flex: 1;
  margin-left: 200px;
  min-height: calc(100vh - 60px);
}

.main-content {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}
</style>