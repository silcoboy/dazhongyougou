const app = getApp()
var that;
var api = require('../../../../common/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:null,
    comment:'不想要了',
    info:[],
    radio: '1'
  }, 
  realInput(e) {
    this.data.comment = e.detail.value;
  }, 
  confirmBuy: function () {
    api.get('/miniapi/refundApply',
    { 
      content:that.data.comment,
      id:that.data.id} 
    ).then(res => { 
      if (res.code == 0) { 
        wx.showToast({
          title: '申请成功',
          icon: 'none',
          duration: 2000
        });  
       wx.navigateBack({
        delta: 1
       })
      }else{
        wx.showToast({
          title: rs.msg,
          icon: 'none',
          duration: 2000
        });
      }
    });
     

    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;
    this.data.id = options.id;  
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
  onChange(event) {
    this.setData({
      radio: event.detail,
    });
  },

  onClick(event) {
    console.log("name",event.currentTarget.dataset.reason)
    this.setData({
      comment: event.currentTarget.dataset.reason,
    });
  },
  getCourse(e){
    that.setData({
      comment:e.detail.value
      })
  },
})