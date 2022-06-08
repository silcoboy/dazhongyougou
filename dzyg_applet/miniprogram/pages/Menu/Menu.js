// pages/Menu/Menu.js
var that;
var api = require('../../common/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    recommendList:[],
    value:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
  that.loadInfo();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  that.loadInfo();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  addMenu(){
    wx.navigateTo({
      url: './AddMenu/AddMenu',
    })
  },
  loadInfo(name){
    if(name==''){
    wx.showToast({
      title: '请输入搜索关键词',
      icon:'error'
    })
    return;
    }
    wx.showLoading({
      title: '正在加载中',
      
    })
    api.get('/miniapi/menuList' ,{  
      name:name
    } 
    ).then(res => { 
      console.log("111",res.data)
      if (res.code == 0) {
        var list = res.data.userMenuList; 
        for(var i=0;i<list.length;i++){
        list[i].picUrl=JSON.parse(list[i].picUrl)
        }
        that.setData({ 
          recommendList: list, 
        })
        wx.hideLoading(); 
      }
    });
  } ,
  goproductDetailfunc(e){
    wx.navigateTo({
      url: './MenuDetail/MenuDetail?id='+ e.currentTarget.dataset.id,
    })
  },
  searchMenu(e){
  that.setData({
  value:''
  })
  that.loadInfo(e.detail)
  }
})