const app = getApp()
 
var api = require('../../../../common/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
   id:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
     this.data.id=options.id;
     this.loadInfo();
  },loadInfo(){
    var that=this; 
    api.get('/miniapi/orderDetail',{id:that.data.id} 
    ).then(res => { 
      if (res.code == 0) {
        var d = res.data;
        var list =res.data.list;
        var amount=0;
        for(var i =0;i<list.length;i++){
          list[i].goodsPic=JSON.parse(list[i].goodsPic)
          amount=amount+list[i].amount;
        }
        amount=amount.toFixed(2)
        that.setData({ 
          list: list,
          amount: amount,
          createTime: d.createTime,
          takeTime:d.list[0].takeTime,
          num:d.num,
          statusName:d.statusName,
          status:d.status,
          ordSn: d.ordSn,
          store: d.store,
        })   
      }
      console.log(res.data)
      console.log(res.data.list)
      console.log(d.store)
    });
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
  //点击取货
  takeOrd(e){
      var that=this;
      var id=e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '您确认已成功取货操作吗？',
      success (res) {
        if (res.confirm) { 
           that.payOrd(id);  
        } else if (res.cancel) { 
        }
      }
    }) 
 
    
  },payOrd(ordSn){
    
    var that=this;
        api.get('/miniapi/takeOrd',
    {  
      ordSn:ordSn 
    } 
    ).then(res => { 
    if (res.code == 0) {  
      that.loadInfo();
    } 
    });
  },  
})