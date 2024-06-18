import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/all-news',
    name: 'All-News',
    meta: { authRequired: false },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/News.vue')
  },
  {
    path: '/news/single-news-view/:id',
    name: 'NewsDetails',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/./NewsDetails')
  },
  {
    path: '/news/by-category/:id',
    name: 'NewsByCategory',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/NewsByCategory.vue')
  },
  {
    path: '/news/popular',
    name: 'Popular',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/Popular.vue')
  },
  {
    path: '/news/by-tag/:tagname',
    name: 'NewsByTag',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/NewsByTag.vue')
  },
  {
    path: '/news/by-word/:word',
    name: 'NewsByWord',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/NewsByWord.vue')
  },
  {
    path: '/users/login',
    name: 'Login',
    meta: { authRequired: false },
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/news',
    name: 'CreateNews',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/CreateNews.vue')
  },
  {
    path: '/categories',
    name: 'CreateCategory',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/CreateCategory.vue')
  },
  {
    path: '/users/register',
    name: 'CreateUser',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/CreateUser.vue')
  },
  {
    path: '/user/update/:id',
    name: 'UpdateUser',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/UpdateUser.vue')
  },
  {
    path: '/categories/:id',
    name: 'UpdateCategory',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/UpdateCategory.vue')
  },
  {
    path: '/news/:id',
    name: 'UpdateNews',
    meta: { authRequired: true },
    component: () => import(/* webpackChunkName: "about" */ '../views/UpdateNews.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired === true) {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'Login'});
      return;
    }

    const payload = JSON.parse(atob(jwt.split('.')[1]));
    const expDate = new Date(payload.exp * 1000);
    if (expDate < new Date()) {
      next({name: 'Login'});
      return;
    }
  }

  next();
});

export default router
