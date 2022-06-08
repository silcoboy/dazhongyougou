var that;
var api = require('../../common/api.js');
var app =getApp()
Page({

  data: {
    userInfo:[],
    StoreList:[],
    Address:[],
    addressId:'',
    token:'',
    nickname:'',
    headImg:''
    
  },
  onReady: function (e) {
    that = this;
  },
    /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  },
    /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    that= this;
    var token = wx.getStorageSync('token');
    this.setData({
      token:token
    });
    if(token!=''){
      that.loadMine();
    }
  },
    /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that= this;
    var token = wx.getStorageSync('token');
    console.log("执行1")
    this.setData({
      token:token
    });
    if(token!=''){
      that.loadMine();
    }
    // that.GetStoreList();
  },
  loadMine(){
    api.get('/miniapi/mine' 
    ).then(res => { 
      if (res.code == 0) {
        console.log("登录信息",res)
        that.setData({
          nickname: res.data.nickname ,
          headImg: res.data.headImg , 
        })  
      }
    });
  },
  onShow(){ 
    that.data.addressId=app.addressId
    this.GetStoreList();
  },
  getinfoFunc: function (e) {
    this.setData({
      userInfo: e.detail.userInfo
    });
    wx.setStorageSync('userInfo', e.detail.userInfo)
  },

  //点击订单
  OrderItem(e) {
    console.log(e)
    wx.navigateTo({
      url: './MyOrder/MyrOder?current=' + e.currentTarget.id,
    })
  },

  //售后
  AfterSale(res) {
    wx.navigateTo({
      url: 'Shouhou/Shouhou',
    })
  },
  Usecode() {
    wx.makePhoneCall({
      phoneNumber: '1340000'
    })
  },
  GetStoreList(){
    api.get('/miniapi/listStore' 
    ).then(res => { 
      if (res.code == 0) {
        var store=res.data.storeList[0];
        if(that.data.addressId){
       
            for(var i=0;i<res.data.storeList.length;i++){ 
              if(res.data.storeList[i].id==that.data.addressId){
                store=res.data.storeList[i];
                break;
              }
            }
        }

        that.setData({ 
          StoreList: res.data.storeList, 
          Address:store,
        })   
      }
    });
  },
  handleChooseAddress(){
    wx.navigateTo({url: "../Store/Store?back=0"});
  },
  GetStore(){
  
  },
  gotoLogin(){
    wx.reLaunch({
      url: '../Login/Login'
    })
  }
})