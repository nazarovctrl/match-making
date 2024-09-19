<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input type="text" v-model="username" placeholder="Username" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <button type="submit">Login</button>
    </form>
    <p>{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
      message: ''
    };
  },
  methods: {
    login() {
      axios.post('/api/v1/auth/login', {
        username: this.username,
        password: this.password
      })
          .then(response => {
            this.message = 'Login successful!';
            // Сохранить токен в localStorage, если нужно
            localStorage.setItem('token', response.data.data.accessToken);
          })
          .catch(error => {
            this.message = 'Login failed: ' + error.response.data.message;
          });
    }
  }
};
</script>

<style scoped>
.login {
  max-width: 300px;
  margin: 0 auto;
}
input {
  display: block;
  margin: 10px 0;
}
</style>
