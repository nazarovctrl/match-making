const routes = [
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/components/Register.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/components/Login.vue')
    }
];
