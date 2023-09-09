import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/my',
    component: () => import('@/views/dashboard/my'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  { path: '/', component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'el-icon-office-building', affix: true} },
    ] }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/system',
    component: Layout,
    name: 'system',
    meta: { title: '系统管理', icon: 'el-icon-set-up' },
    children: [
      {
        path: '/sysUserList',
        name: 'sysUserList',
        component: () => import('@/views/system/sysUserList'),
        meta: { title: '用户管理', icon: 'el-icon-cpu' }
      },
      {
        path: '/sysRoleList',
        name: 'sysRoleList',
        component: () => import('@/views/system/sysRoleList'),
        meta: { title: '角色管理', icon: 'el-icon-unlock' }
      },
      {
        path: '/sysMenuList',
        name: 'sysMenuList',
        component: () => import('@/views/system/sysMenuList'),
        meta: {title: '菜单管理',icon: 'el-icon-coordinate'}
      },
      {
        path: '/sysNotice',
        name: 'sysNotice',
        component: () => import('@/views/system/sysNotice'),
        meta: {title: '公告管理',icon: 'el-icon-chat-line-square'}
      },
      {
        path: '/bkUser',
        name: 'bkUser',
        component: () => import('@/views/system/bkUser'),
        meta: { title: '读者管理', icon: 'el-icon-goblet' }
      },
    ]
  },
  {
    path: '/books',
    component: Layout,
    alwaysShow: true,
    name: 'books',
    meta: { title: '图书管理', icon: 'el-icon-reading' },
    children: [
      {
        path: '/bookAdd',
        name: 'bookAdd',
        component: () => import('@/views/books/bookAdd.vue'),
        meta: { title: '图书列表', icon: 'el-icon-collection' }
      },
      {
        path: '/bookFl',
        name: 'bookFl',
        component: () => import('@/views/books/bookFl.vue'),
        meta: {title: '图书分类',icon: 'el-icon-folder-opened\n'}
      }
    ]
  },
  {
    path: '/borrow',
    component: Layout,
    alwaysShow: true,
    meta: { title: '借阅管理', icon: 'el-icon-ice-cream-round' },
    children: [
      {
        path: '/borrowBook',
        name: 'BorrowBook',
        component: () => import('@/views/borrow/BorrowBook'),
        meta: { title: '借书管理', icon: 'el-icon-grape' }
      },
      {
        path: '/returnBook',
        name: 'returnBook',
        component: () => import('@/views/borrow/ReturnBook'),
        meta: { title: '还书管理', icon: 'el-icon-goblet' }
      },
      {
        path: '/borrowLook',
        name: 'borrowLook',
        component: () => import('@/views/borrow/borrowLook'),
        meta: { title: '借阅查看', icon: 'el-icon-s-cooperation' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
