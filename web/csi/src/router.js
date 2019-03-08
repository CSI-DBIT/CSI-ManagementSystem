import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Dash from './views/dash.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: {
        name: 'Login'
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/dash',
      name: 'Dash',
      component: Dash
    }
  ]
})
