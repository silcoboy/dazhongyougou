
var that;
var api = require('../../../common/api.js');
var app =getApp();
Page({

  data: {
    navList: [
      { name: '全部', Style: 'active',id:0,},
      { name: '待付款', Style: '' ,id:1,},
      { name: '待配送', Style: '',id:2, },
      { name: '待提货', Style: '',id:3, },
      { name: '已提货', Style: '',id:4, },
    ],
    current: '',   //指定在哪个面板
    swiperheight: '',//swiper的高度
    left:'50rpx',        //导航底部的位置
    stopPageScroll:'',
    status:'',
    tabid:0,
    OrderList:[]
  },
  GetOrderList(){
    console.log(that.data.status)
    api.get('/miniapi/myOrder',
    {status:that.data.status} 
    ).then(res => { 
      if (res.code == 0) {
        if(res.data.list==''||res.data.list==undefined||res.data.list==null){
          that.setData({ 
            OrderList: ''
          }) 
        return;
        }
        var list = res.data.list;
        console.log("数据来源", list)
        for(var i = 0;i<list.length;i++){
        for(var j = 0;j<list[i].goodsList.length;j++){
          list[i].goodsList[j].goodsPic=JSON.parse(list[i].goodsList[j].goodsPic)
        }
        }
        that.setData({ 
          OrderList: list
        })   
        console.log("数据来源1", list)
      }
    });
  },
  onLoad(options) {
    that = this;
    this.setData({
      current: options.current,
      status: options.current
    });
    that.GetOrderList()
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    // wx.reLaunch({
    //   url: '../../My/My'
    // })
  },
  orderListfun(){ wx.navigateTo({ url: 'order-detail-page/order-detail-page' }) },

  onReady: function (res) {
    this.swiperHeight();
    this.setData({ appLoadingStatue: true, stopPageScroll: 'stopPageScroll'})
    // app.request("GET","/api/auth/login",{},
    // function(res){
    //   console.log(res)
    //   that.setData({
    //     appLoadingStatue: false,  //请求未完成的loding
    //     stopPageScroll: '',
    //     network500: false
    //   })
    // },this.error)
  },

  connectfunc(){
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
        status: e.currentTarget.dataset.type
      });
    };

    var query = wx.createSelectorQuery();
    //选择id
    query.selectAll('.every').boundingClientRect(function (rect) {
      that.setData({
        left: e.currentTarget.offsetLeft + (rect[e.currentTarget.id].width / 3) + "px"
      })
    }).exec();
    that.GetOrderList();
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
        left: rect[e.detail.current].left + (rect[e.detail.current].width / 3) + "px"
      })
    }).exec();
  },

  stopPageScroll(){return},
  
  error(err){
    this.setData({
      appLoadingStatue: false,
      network500: true
    })
  },
 //点击取消
 cancel(e){
  var id=e.currentTarget.dataset.id;
wx.showModal({
  title: '提示',
  content: '您确认要取消操作吗？',
  success (res) {
    if (res.confirm) {
       
       that.payOrd(1,id);


    } else if (res.cancel) { 
    }
  }
}) 
},
payOrd(status,ordSn){ 
  var that=this;
      api.get('/miniapi/payOrd',
  {  
    ordSn:ordSn,  
  status:status
  } 
  ).then(res => { 
  if (res.code == 0) {  
    that.GetOrderList();
  } 
  });
},
//点击支付
pay(e){
  var that=this;
  var id=e.currentTarget.dataset.id;
wx.showModal({
  title: '提示',
  content: '您确认要支付操作吗？',
  success (res) {
    if (res.confirm) {
       
       that.payOrd(2,id);


    } else if (res.cancel) { 
    }
  }
}) 
}, 
 //跳转详情页
 godetail(e) { 
  wx.navigateTo({
        url: './OrderDerailPage/OrderDerailPage?id=' + e.currentTarget.dataset.id,
  })
}, 
})