// pages/Store/Store.js
var that;
var api = require('../../common/api.js');
var app =getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    Address:[],
    pay:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
  that.data.pay=options.back
  console.log("判断",that.data.pay)
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
    that.GetStoreList()
    // let pages = getCurrentPages();
    //     let currentPage=pages[pages.length-1];
    //     let options=currentPage.options;
    //     const {pay}=options;
     console.log(that.data.pay)
        if(that.data.pay==true){
          this.setData({
            payTo:true
          })
        }
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
  GetStoreList(){
    api.get('/miniapi/listStore' 
    ).then(res => { 
      if (res.code == 0) {
        console.log(res.data.storeList)
       var  storeList = res.data.storeList;
       for(var i = 0;i<storeList.length;i++){
        storeList[i].storePic = JSON.parse(storeList[i].storePic)
       }
       console.log(storeList)
        that.setData({ 
          Address: storeList
        }) 
    
      }
    });
  },
  GotoMy(e){
  app.addressId=e.currentTarget.dataset.id;
    wx.switchTab({
      url: '../My/My',
    })
  },
  Gotosettlement(e){
  wx.redirectTo({
    url: '../Cart/Settlement/Settlement?addressId='+e.currentTarget.dataset.id,
  })
  }
})