import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Editor from './views/Editor.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/editor',
      name: 'editor',
      component: Editor
    }
  ]
})
