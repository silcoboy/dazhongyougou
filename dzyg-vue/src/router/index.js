import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'

import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
// 进度条配置项
NProgress.configure({
  showSpinner: false
});

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../components/admin/sys/Login')
  } ,
  {
    path: '/index',
    component: () => import('../components/admin/sys/Main'),
    redirect: '/welcome',
    children: [
      {
        path: '/welcome',
        component: () => import('../components/admin/welcome')
      },  
		// 分类管理 
		{
		  path: '/categoryManage',
		  component: () => import('../components/admin/goods/categoryManage')
		} ,
		// 商品管理
		{
		  path: '/goodsManage',
		  component: () => import('../components/admin/goods/goodsManage')
		} ,
		
		//用户管理(管理员)
		{
		  path: '/userManage',
		  component: () => import('../components/admin/user/userManage')
		} , 
				
		
		//用户菜谱
		{
		  path: '/userMenuList',
		  component: () => import('../components/admin/user/userMenuList')
		} ,
		
		 //自提点
		 {
		   path: '/storeList',
		   component: () => import('../components/admin/goods/storeManage')
		 } ,
		 
			 
		
		//用户订单查询
		{
		  path: '/userOrdList',
		  component: () => import('../components/admin/user/userOrdList')
		} , 
		
		 
		
      
	 
	 
    ]
  } 
]

const router = new VueRouter({
  routes
})
router.beforeEach((to, from, next) => {
  NProgress.start(); 
  next()
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
});

export default router
