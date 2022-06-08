let API = {
  //应用表
  api: {
	//登陆接口
	login: '/api/login',
    // 查询应用列表
    register: '/api/register', 
   //检验token合法(返回当前对象)
    checkToken: '/api/checkToken', 
    //获取主页面的左侧menu数据
    getMenuInfo: '/api/getMenu',
	//获取角色信息
	getRoleInfo: '/api/getRole',
	
	//获取用户信息(可附带查询条件,可分页,n功能合一接口)
	getUserInfo: '/api/user/getUser',
	//管理员新增用户
	addUser: '/api/user/saveUser', 
	deleteUser:'/api/user/delete', 
	updateUserStatus:'/api/user/updateStatus', 
	getSelfInfo: '/api/user/getSelfInfo',  
	getUserListByRoleId: '/api/user/getUserList',  
	
	
	//商品分类管理
	getAllCategory: '/api/category/getAllCategory', 
	deleteCategory: '/api/category/deleteCategory',
  saveCategory: '/api/category/saveCategory',
    listCategory: '/api/category/getList',
	
	//商品管理
	getAllGoods: '/api/goodsInfo/getAllGoods', 
	deleteGoods: '/api/goodsInfo/deleteGoods',
	saveGoods: '/api/goodsInfo/saveGoods',
	
	//用户菜谱
	getUserMenu: '/api/userMenu/getUserMenu', 
	deleteMenu: '/api/userMenu/delete',
	 
	//自提点管理
	getAllStore: '/api/storeInfo/getAllStore', 
	deleteStore: '/api/storeInfo/deleteStore',
	saveStore: '/api/storeInfo/saveStore',
	
	//用户订单
	getAllOrder: '/api/userOrder/getAllOrder', 
	deleteUserOrder: '/api/userOrder/deleteUserOrder',
	updateOrdStatus: '/api/userOrder/updateOrdStatus',     
	updateRefundStatus: '/api/userOrder/updateRefundStatus', 
	
  },
}

export default {
  API: API,
}
