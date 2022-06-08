// pages/Home/Home.js
var that;
var app = getApp();
var api = require('../../common/api.js'); 
Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollTop: 0,//滚动距离
    backTop: false,
    scrolltop:'500',
    value: '',
    swiperList:[
      {
        image_src:'../../img/home/huodong1.jpg'
      },
      {
        image_src:'../../img/home/huodong2.jpg'
      },
      {
        image_src:'../../img/home/huodong3.jpg'
      }
    ],
    iconList:[],
    index:'',
    id:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that= this;
    that.getvbList()
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
  goTop: function (e) {  // 一键回到顶部
    if (wx.pageScrollTo) {
      wx.pageScrollTo({
        scrollTop: 0
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  // 实时获取滚动条的值，根据滚动条的值与设定的值进行比较，决定是否显示和隐藏返回顶部部件
  onPageScroll: function (e) {
    var that = this
    if (e.scrollTop > that.data.scrolltop) { that.setData({ backTop: true }) }
    else { that.setData({ backTop: false }) }
  },
  onChange(e) {
    this.setData({
      value: e.detail,
    });
  },
  onSearch() {
    Toast('搜索' + this.data.value);
  },
  onClick() {
    Toast('搜索' + this.data.value);
  },
  getvbList(){
    wx.request({
      url: `http://localhost:8082/miniapi/indexInit`,
      success:(res)=>{
        console.log(res)
        var skillList = res.data.data.skillList;
        var specialList = res.data.data.specialList;
        var recommendList = res.data.data.recommendList;
        for(var i =0;i<skillList.length;i++){
          skillList[i].picUrl=JSON.parse(skillList[i].picUrl)
        }
        for(var i =0;i<specialList.length;i++){
          specialList[i].picUrl=JSON.parse(specialList[i].picUrl)
        }
        for(var i =0;i<recommendList.length;i++){
          recommendList[i].picUrl=JSON.parse(recommendList[i].picUrl)
        }
          that.setData({
            iconList:res.data.data.iconList,//分类列表
            recommendList:recommendList,//今日推荐
            skillList:skillList,//,秒杀特卖
            specialList:specialList,//特价菜场
          })
          console.log(res)
      },
      
    })
  },
  Gosearch(){
    wx.navigateTo({
      url: '../Home/SearchPage/SearchPage',
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
  api.get('/miniapi/addCart',
  {
    goodsId: e.currentTarget.dataset.id
  }
).then(res => {  
  wx.showToast({
    title: '加购成功',
    icon: 'success',
})
}).catch(res =>{
})
},
goproductDetailfunc(e) {
  wx.navigateTo({
        url: '../Home/productDetail/productDetail?id=' + e.currentTarget.dataset.id,
  })
},
//查看更多秒杀特卖
GotoSerchResult(){
wx.navigateTo({
  url: './SearchPage/SearchResult/SearchResult?key='+'seckill',
})
},
GotoResult(){
  wx.navigateTo({
    url: './SearchPage/SearchResult/SearchResult?key='+'special',
  })
},
Addcarts(e){
  var token = wx.getStorageSync('token')
  if(token==''){
  wx.reLaunch({
    url: '../Login/Login',
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

}).catch(res =>{
  wx.navigateTo({
    url: '../Login/Login',
  })
  })
},
GotoSort(e){
  app.active=e.currentTarget.dataset.index;
  app.type =e.currentTarget.dataset.id;
  wx.switchTab({
     url: '../Sort/Sort',
    })
}
})