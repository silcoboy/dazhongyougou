var that;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    checked: true,
    userInfo:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
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
  //是否同意协议
  onChange(event) {
    that.setData({
      checked: event.detail,
    });
    console.log(event.detail)
    
  },
  //登录
  getinfoFunc: function (e) {
    this.setData({
      userInfo: e.detail.userInfo
    });
    wx.setStorageSync('userInfo', e.detail.userInfo)
    that.gomypage()
  },
  gomypage(){
    wx.switchTab({ url: '../Home/Home' })
  },
  //登录
  getUserProfile: function (e) {
    if(!that.data.checked){
      wx.showToast({
        title: '请勾选协议',
        icon: 'error',
      });
    return;
    }
    wx.getUserProfile({
    desc: '展示用户信息', 
    success: (res) => {
      console.log(res)
      this.setData({
        userInfo: res.userInfo,
        hasUserInfo: true
      })
      var u = res.userInfo;
      wx.request({
        url: `http://localhost:8082/miniapi/loginWx`,
        method:'GET',
        data:{
          nickname:res.userInfo.nickName
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        success:(res)=>{
          console.log(res)
          if (res.data.code == 0) { 
          
            wx.setStorage({
              key: 'token',
              data: res.data.data,
              success() { 
    
                that.gomypage();
              }
            })
          }else{
            wx.showToast({
              title: 登陆失败,
              icon: 'none',
              duration: 2000
            });
          }
        },
        
      })
    }
  })
  } , 
  gettourist(){
    wx.switchTab({ url: '../Home/Home' })
  }
})