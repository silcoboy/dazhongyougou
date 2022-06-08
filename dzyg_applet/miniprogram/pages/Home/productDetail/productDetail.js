// pages/commodity-detail/commodity-detail.js
const app = getApp()
var that;
var api = require('../../../common/api.js'); 
Page({

  /**
   * 页面的初始数据
   */
  data: {
    img: 9,
    scrollTop: 0,//滚动距离
    backTop: false,
    scroll: 500 / app.globalData.bili,
    scrolltop:500,
    id:'',
    detailsList:[],
    CartList:[]
  },

  onLoad(optons) {
    that = this
    that.data.intervarID = setInterval(function () {
      that.start('2019-04-20 23:00:00')
    }, 1000)
    that.setData({ 
      id: optons.id ,  
    }) 
    console.log(optons.id)
    that.getproductDetail()
  },


  start(end) {
    var leftTime1 = new Date(end);
    var leftTime2 = new Date().getTime();

    let newdata = leftTime1.getTime();
    let olddata2 = end.replace(/-/g, '/');
    let mydata2 = new Date(olddata2);
    let newdata2 = mydata2.getTime();

    var leftTime = newdata2 - leftTime2; //计算剩余的毫秒数

    var days = parseInt(leftTime / 1000 / 60 / 60 / 24, 10); 
    var hours = parseInt(leftTime / 1000 / 60 / 60 % 24, 10); 
    var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);
    var seconds = parseInt(leftTime / 1000 % 60, 10);  
    if (days < 10) {
      days = "0" + days
    }
    if (hours < 10) {
      hours = "0" + hours
    }
    if (minutes < 10) {
      minutes = "0" + minutes
    }
    if (seconds < 10) {
      seconds = "0" + seconds
    }
    this.setData({
      hours, minutes, seconds,
      time: hours + ':' + minutes + ':' + seconds
    })
  },

  onPageScroll: function (e) {
    if (e.scrollTop > that.data.scrolltop) { that.setData({ backTop: true }) }
    else { that.setData({ backTop: false }) }


    if (e.scrollTop < 100) { that.setData({ baritem: 0 }) }
    else if (e.scrollTop > 400&& e.scrollTop < 500) { that.setData({ baritem: 1 }) }
    else if (e.scrollTop > 500&& e.scrollTop < 600) { that.setData({ baritem: 2 }) }
    else { that.setData({ baritem: 3 }) }
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
  getproductDetail(){
    wx.request({
      url: `http://localhost:8082/miniapi/goodsDetail?id=`+that.data.id,
     success:(res)=>{
       var info =res.data.data.info;
        info.imgs=JSON.parse(info.imgs)
        info.picUrl=JSON.parse(info.picUrl)
       that.setData({
        detailsList:info
       })
       console.log("详情",info)
     }
    }) 
  },
  onClickHome(){
    wx.switchTab({ url: '../../Home/Home' }) 
  },
  onClickCart(){
   wx.switchTab({ url: '../../Cart/Cart' }) 
  },
  AddCart(){
    var token = wx.getStorageSync('token')
    if(token==''){
    wx.reLaunch({
      url: '../../Login/Login',
    })
    return;
    }
    api.get('/miniapi/addCart',
    {
      goodsId: that.data.id 
    }
  ).then(res => {  
    console.log(res)
    wx.showToast({
      title: '加购成功',
      icon: 'success',
  })  
  });
  },
      //在新页面中全屏预览图片。预览的过程中用户可以进行保存图片、发送给朋友等操作
previewImage(e){
wx.previewImage({
      current: that.data.detailsList.picUrl[e.currentTarget.dataset.index], // 当前显示图片的http链接
      urls: that.data.detailsList.picUrl // 需要预览的图片http链接列表
                })
  },
  //在新页面中全屏预览图片。预览的过程中用户可以进行保存图片、发送给朋友等操作
  previewImages(e){
    wx.previewImage({
      current: that.data.detailsList.imgs[e.currentTarget.dataset.index], // 当前显示图片的http链接
      urls: that.data.detailsList.imgs // 需要预览的图片http链接列表
    })
  },
  Bynow(){
    var token = wx.getStorageSync('token')
    if(token==''){
    wx.reLaunch({
      url: '../../Login/Login',
    })
    return;
    }
    api.get('/miniapi/addCart',
    {
      goodsId: that.data.id 
    }
  ).then(res => {  
    console.log(res)
    api.get('/miniapi/userCartList' 
    ).then(res => { 
      if (res.code == 0) {
        var list= res.data.list;
        for(var i = 0;i<list.length;i++){
          list[i].goodsPic = JSON.parse(list[i].goodsPic)
        }
        var index = list.length-1;
        var CartList=[];
        CartList.push(list[index])
        CartList[0].checked=true;
        console.log("缓存测试",CartList)
        wx.setStorageSync("CarList",CartList);
        wx.navigateTo({
          url: '../../Cart/Settlement/Settlement',
        })
      }
    });

  });

  }
})