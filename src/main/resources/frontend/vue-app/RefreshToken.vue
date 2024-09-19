<template>
  <div class="refresh-token">
    <h2>Refresh Token</h2>
    <button @click="refreshToken">Refresh</button>
    <p>{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      message: ''
    };
  },
  methods: {
    refreshToken() {
      const token = localStorage.getItem('token');
      if (token) {
        axios.post('/api/v1/auth/refresh', {}, {
          headers: { Authorization: `Bearer ${token}` }
        })
            .then(response => {
              this.message = 'Token refreshed!';
              localStorage.setItem('token', response.data.data);
            })
            .catch(error => {
              this.message = 'Failed to refresh token: ' + error.response.data.message;
            });
      } else {
        this.message = 'No token found!';
      }
    }
  }
};
</script>

<style scoped>
.refresh-token {
  max-width: 300px;
  margin: 0 auto;
}
</style>
