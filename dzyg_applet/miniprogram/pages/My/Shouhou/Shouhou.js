const app = getApp()
var that;
var api = require('../../../common/api.js');
Page({

  data: {
    navList: [
      { name: '全部', Style: 'active',id:0 },
      { name: '处理中', Style: '',id:1 },
      { name: '已退款', Style: '',id:2 },
    ],
    // { name: '已拒绝', Style: '' },
    current: '',   //指定在哪个面板
    swiperheight: '',//swiper的高度
    left: '50rpx',        //导航底部的位置
    stopPageScroll: '',
    status:null,
    tabid:'',
    list:[]
  },

  onLoad(options) {
    that = this;
    that.getlist();
  },

  orderListfun() { wx.navigateTo({ url: 'order-detail-page/order-detail-page' }) },

  onReady: function (res) {
    this.swiperHeight();
    
    var query = wx.createSelectorQuery();
    //选择id
    var that = this;
    query.selectAll('.every').boundingClientRect(function (rect) {
      that.setData({
        left: rect[0].left + (rect[0].width / 3.14) + "px",
        navborderWidth: rect[0].width / 3 + "px",
      })
    }).exec();

    this.setData({ appLoadingStatue: true, stopPageScroll: 'stopPageScroll' })
    app.request("GET", "/api/auth/login", {},
      function (res) {
        console.log(res)
        that.setData({
          appLoadingStatue: false,  //请求未完成的loding
          stopPageScroll: '',
          network500: false
        })
      }, this.error)
  },

  connectfunc() {
    this.selectComponent('#network-500').connectfunc(this.onReady)
  },

  swiperHeight() {
    var PhoneHeight = app.globalData.PhoneHeight
    var swiperheight = PhoneHeight
    this.setData({
      swiperheight
    })
  },

  //点击导航跳转
  navtap: function (e) {
    for (let i in this.data.navList) {
      this.setData({
        ['navList[' + i + '].Style']: '',
        ['navList[' + e.currentTarget.id + '].Style']: 'active',
        current: e.currentTarget.id,
      });
    };
    var query = wx.createSelectorQuery();
    //选择id
    var that = this;
    query.selectAll('.every').boundingClientRect(function (rect) {
      that.setData({
        left: rect[e.currentTarget.id].left + (rect[e.currentTarget.id].width / 3.14) + "px",
        navborderWidth: rect[e.currentTarget.id].width / 3 + "px",
      })
    }).exec();
    that.setData({
      tabid: e.currentTarget.dataset.id,
      status: e.currentTarget.dataset.type
      })
    that.getlist();
  },
  //滑动跳转
  swiperChange: function (e) {   //拖动swiper监听
    for (let i in this.data.navList) {
      this.setData({
        ['navList[' + i + '].Style']: '',
        ['navList[' + e.detail.current + '].Style']: 'active'
      });
    };

    var query = wx.createSelectorQuery();
    //选择id
    var that = this;
    query.selectAll('.every').boundingClientRect(function (rect) {
      that.setData({
        left: rect[e.detail.current].left + (rect[e.detail.current].width / 3.14) + "px",
        navborderWidth: rect[e.detail.current].width / 3 + "px",
      })
    }).exec();
  },

  stopPageScroll() { return },

  error(err) {
    this.setData({
      appLoadingStatue: false,
      network500: true
    })
  },
  getlist(){
    var that=this; 
    api.get('/miniapi/shouhouOrder',{status:that.data.status} 
    ).then(res => { 
      if (res.code == 0) {
        var d = res.data; 
        var list= res.data.list;
        for(var i = 0;i<list.length;i++){
          list[i].goods.picUrl=JSON.parse(list[i].goods.picUrl)
        }
        that.setData({ 
          list: list
        })   
        console.log(d.list)
      }
    });
  },
//申请退款
refund(e) {
  wx.navigateTo({
        url: './ApplyPage/ApplyPage?id=' + e.currentTarget.dataset.id,
  })
},
  onShow(){ 
    that.getlist();
  },
})