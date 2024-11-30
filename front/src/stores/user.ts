import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => {
    return {
        username: '',
        lastLoginTime: '',
        token: ''
    }
  },
  actions: {
    setUsername(name: string) {
      this.username = name;
    },
    setLastLoginTime(lastLoginTime: string) {
      this.lastLoginTime = lastLoginTime;
    },
    setToken(token: string) {
      this.token = token;
    }
  }
});
