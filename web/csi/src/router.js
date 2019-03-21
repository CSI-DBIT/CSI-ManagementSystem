import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import DashAdd from './views/dashAdd.vue'
import DashMembers from './views/dashMembers.vue'

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
      path: '/dashadd',
      name: 'DashAdd',
      component: DashAdd
    },
    {
      path: '/dashMembers',
      name: 'DashMembers',
      component: DashMembers
    }
  ]
})
