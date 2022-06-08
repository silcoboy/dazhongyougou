// pages/Sort/Sort.js
var that;
var api = require('../../common/api.js'); 
var app = getApp(); 
Page({

  /**
   * 页面的初始数据
   */
  data: {
  wh:'',
  sortname:[],
  active:0,
  type:1,
  scrollTop:0,
  goodsList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
  if(app.active==undefined||app.type){
    app.active=0;
    app.type=1;
  }
  console.log("你",app.active)
  console.log("好",app.type)
  that.setData({
    active:app.active,
    type:app.type
  })
  // 获取手机窗口高度
  wx.getSystemInfo({
      success (res) {
        that.setData({
          wh:'height:'+res.windowHeight+'px'
        })
        
      }
  })
  that.getsortname()
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
    that.setData({
      active:app.active,
      type:app.type
      })
      that.getsortname()
    console.log("1",app.active)
    console.log("2",app.type)
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    // app.type=1;
    // app.active=0;
    // that.setData({
    // active:app.active,
    // type:app.type
    // })
    // that.getsortname()
    
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
  // 获取分类菜品名称
  getsortname(){
   
  wx.request({
    url: `http://localhost:8082/miniapi/getListInfo?type=`+that.data.type,
   success:(res)=>{
     console.log(res.data)
     var goodsList=res.data.data.goodsList;
     console.log("数据",res.data.data.goodsList)
     for(var i = 0;i<goodsList.length;i++){
      goodsList[i].picUrl= JSON.parse(goodsList[i].picUrl)
     }
     that.setData({
     sortname:res.data.data.typeList,
     goodsList:goodsList
     })
     console.log("菜品名称",res.data.data.goodsList)
   }
  })
  },
  activeChanged(event){
  that.setData({
  active:event.currentTarget.dataset.index,
  type:event.currentTarget.dataset.id,
  })
  this.getsortname()
  that.scrollTop=that.scrollTop==0 ? 1:0
  },
  //跳转到详情页面
  goproductDetailfunc(e) {
    wx.navigateTo({
          url: '../Home/productDetail/productDetail?id=' + e.currentTarget.dataset.id,
    })
},
//添加购物车
AddCart(e){
  var token = wx.getStorageSync('token')
  if(token==''){
  wx.reLaunch({
    url: '../Login/Login',
  })
  return;
  }
  console.log("数据",e)
  api.get('/miniapi/addCart',
  {
    goodsId: e.currentTarget.dataset.id
  }
).then(res => {  
  console.log(res)
  wx.showToast({
    title: '加购成功',
    icon: 'success',
})
});
}
})