// pages/Home/SearchPage/SearchResult/SearchResult.js
var that;
var api = require('../../../../common/api.js'); 
Page({

  /**
   * 页面的初始数据
   */
  data: {
    key:'',
    SearchList:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
  that.setData({
  key:options.key
  })
  this.GetSearch()
  if(options.key=='seckill'){
    wx.setNavigationBarTitle({
      title:'秒杀特卖',
  })
    this.GetSeckill()
  }else if(options.key=='special'){
    wx.setNavigationBarTitle({
      title:'特价菜场',
  })
    this.GetSpecial()
  }else{
    wx.setNavigationBarTitle({
      title:'"'+ options.key + '"的搜索结果',
  })
  }
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
  GetSearch(){
    if(that.data.key=='seckill'||that.data.key=='special'){
    return;
    }
    wx.request({
      url: `http://localhost:8082/miniapi/getAllListInfo`,
      method:'GET',
      data:{
      name:that.data.key
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success:(res)=>{
        console.log("搜索",res.data)
        wx.hideLoading();
        var SearchList= res.data.data.goodsList;
        for(var i=0;i<SearchList.length;i++){
          SearchList[i].picUrl=JSON.parse(SearchList[i].picUrl)
        }
        that.setData({
        SearchList:SearchList
       })
      },
      
    })
  },
  AddCart(e){
    var token = wx.getStorageSync('token')
    if(token==''){
    wx.reLaunch({
      url: '../../../Login/Login',
    })
    return;
    }
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
  },
  //跳转到详情页面
  goproductDetailfunc(e) {
    wx.navigateTo({
          url: '../../productDetail/productDetail?id=' + e.currentTarget.dataset.id,
    })
},
//获取秒杀特卖列表
GetSeckill(){
  wx.request({
    url: `http://localhost:8082/miniapi/indexInit`,
    success:(res)=>{
      console.log(res)
      var SearchList=res.data.data.skillList;
      for(var i = 0;i<SearchList.length;i++){
        SearchList[i].picUrl=JSON.parse(SearchList[i].picUrl)
      }
        that.setData({
          SearchList:SearchList,//,秒杀特卖
        })
        console.log(res)
    },
  })
},
//获取特价菜场列表
GetSpecial(){
  wx.request({
    url: `http://localhost:8082/miniapi/indexInit`,
    success:(res)=>{
      console.log(res)
      var specialList = res.data.data.specialList;
      for(var i = 0;i<specialList.length;i++){
        specialList[i].picUrl=JSON.parse(specialList[i].picUrl)
      }
        that.setData({
          SearchList:specialList,//特价菜场
        })
        console.log(res)
    },
  })
}
})