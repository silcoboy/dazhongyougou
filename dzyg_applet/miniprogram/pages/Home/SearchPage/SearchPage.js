// pages/Home/SearchPage/SearchPage.js
var that;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hislist: [],
    list: [],
    key: '',
    blank: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  that=this;
  that.GetKey();
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
      that.GetKey();
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
  //获取本地缓存中的搜索关键词
  GetKey(){
  wx.getStorage({
  key:'history',
  success: function(res) {
    let hislist = JSON.parse(res.data);
    //限制长度
    if (hislist.length > 5) {
          hislist.length = 5
    }
    that.setData({
          hislist: hislist
    })
},
  })
  },
  //添加到搜索历史
  history(key) {
    let that = this;
    wx.getStorage({
          key: 'history',
          success(res) {
                let oldarr = JSON.parse(res.data); //字符串转数组
                let newa = [key]; //对象转为数组
                let newarr = JSON.stringify(newa.concat(oldarr)); //连接数组\转字符串
                wx.setStorage({
                      key: 'history',
                      data: newarr,
                })
          },
          fail(res) {
                //第一次打开时获取为null
                let newa = [key]; //对象转为数组
                var newarr = JSON.stringify(newa); //数组转字符串
                wx.setStorage({
                      key: 'history',
                      data: newarr,
                })
          }
    });
    that.GetKey();
},
//输入框输入的值
onChange(e) {
  this.setData({
    key: e.detail,
  });
},
  //选择历史搜索关键词
  choosekey(e) {
    this.data.key = e.currentTarget.dataset.key;
    this.search('his');
},
//搜索结果
search(n) {
  var key = that.data.key;
  console.log("11",that.data.key)
  if (key == '') {
        wx.showToast({
              title: '请输入关键词',
              icon: 'error',
        })
        return false;
  }
  wx.showLoading({
        title: '加载中',
  }) 
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
          console.log(res)
         if(res.data.code == 0){
          wx.hideLoading();
         }else{
          wx.hideLoading();
          wx.showToast({
            title: '后台数据获取错误',
            icon: 'none',
      })
         }
         console.log(res.data.data.goodsList)
         if(res.data.data.goodsList==''){
          wx.showToast({
            title: '未查询到商品',
            icon: 'error',
      })
          return;
         }
         if (n !== 'his') {
            that.history(key);
      } 
         wx.navigateTo({
          url: '../SearchPage/SearchResult/SearchResult?key='+that.data.key,
        })
        },
        
      })
},

})