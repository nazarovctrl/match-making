<template>
  <div class="register">
    <h2>Register</h2>
    <form @submit.prevent="register">
      <input type="text" v-model="username" placeholder="Username" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <input type="email" v-model="email" placeholder="Email" required />
      <button type="submit">Register</button>
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
      email: '',
      message: ''
    };
  },
  methods: {
    register() {
      axios.post('/api/v1/auth/register', {
        username: this.username,
        password: this.password,
        email: this.email
      })
          .then(response => {
            this.message = 'User registered successfully!';
          })
          .catch(error => {
            this.message = 'Registration failed: ' + error.response.data.message;
          });
    }
  }
};
</script>

<style scoped>
.register {
  max-width: 300px;
  margin: 0 auto;
}
input {
  display: block;
  margin: 10px 0;
}
</style>
