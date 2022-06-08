// pages/Menu/AddMenu/AddMenu.js
var that;
var api = require('../../../common/api.js'); 
Page({

  /**
   * 页面的初始数据
   */
  data: {
  title:'',
  course:'',
  images:[],
  pic:[],
  maxCount:6,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
  that=this;
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    if(wx.getStorageSync("MenuList")==''||wx.getStorageSync("MenuList")==undefined||wx.getStorageSync("MenuList")==null){
      return;
    }else{
      let MenuList=wx.getStorageSync("MenuList")||[];
      that.data.title = MenuList.title;
      that.data.course = MenuList.course;
      that.data.pic =  MenuList.pic;
      that.data.images=MenuList.pic;
      that.getList()
    }
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  getList(){
  that.setData({
    title:that.data.title,
    course:that.data.course,
    images:that.data.images,
    pic:that.data.pic
  })
  },
  getTitle(e){
  that.setData({
  title:e.detail.value
  })
  },
  getCourse(e){
    that.setData({
      course:e.detail.value
      })
  },
  chooseImage(){
  wx.chooseImage({
    count: 6,
    sizeType:['original','compressed'],
    sourceType:['album','camera'],
    success(res){
      console.log(res)
    var imagesData = that.data.images;
    if(res.tempFilePaths.length == 1){
      imagesData.push(res.tempFilePaths[0])
      that.setData({
        images:imagesData
      })
    }else{
      for(var i=0;i <res.tempFilePaths.length;i++){
        imagesData.push(res.tempFilePaths[i])
      }
      that.setData({
        images:imagesData
      })
    }
    that.uploadFile(imagesData)
    }
  })
  },
   //删除图片
   deleteImg(e){
    console.log(e.currentTarget.dataset.index)
    that.data.images.splice(e.currentTarget.dataset.index,1)
    that.setData({
      images:that.data.images
    })
    },
  //在新页面中全屏预览图片。预览的过程中用户可以进行保存图片、发送给朋友等操作
  previewImage(e){
    console.log(that.data.images)
    wx.previewImage({
      current: that.data.images[e.currentTarget.dataset.index], // 当前显示图片的http链接
      urls: that.data.images // 需要预览的图片http链接列表
    })
  },
  onClickButton(){

  if(that.data.title==''){
    wx.showToast({
      title: '标题不能为空',
      icon: 'error'
    });
    return;
  }else if(that.data.course==''){
    wx.showToast({
      title: '教程不能为空',
      icon: 'error'
    });
    return;
  }else if(that.data.images==''){
    wx.showToast({
      title: '图片不能为空',
      icon: 'error'
    });
    return;
  }
  wx.showLoading({
    title: '正在发布中',
  })
  console.log("测试",that.data.pic)
  api.get('/miniapi/addMenu',
  {  
    title: that.data.title,   
    picUrl:that.data.pic,
    content: that.data.course,
  }
).then(res => {
  wx.hideLoading();
  if (res.code == 0) {
    wx.removeStorage({
      key: 'MenuList',
    }) 
    wx.switchTab({
      url: '../Menu',
    }) 
    
  }else{
    wx.showToast({
      title: res.msg,
      icon: 'none',
      duration: 2000
    });
    return;
  }
});
  },
   // 上传图片
 uploadFile(imagesData) {
   that.data.pic=[];
  for(var i = 0;i<imagesData.length;i++){
    wx.uploadFile({
      url: api.domain + '/upload/fileUploadFile',
      filePath:imagesData[i],
      name: 'file',
      count: 1, 
      success(res) {
        var url = JSON.parse(res.data).extra.url;
        console.log("图片地址",url)
        that.data.pic.push(url)
        console.log("11",that.data.pic)
        // that.setData({
        //  pic: url
        // }) 
        
      }
    })
  }
  console.log("12",that.data.pic)
} ,
onClickIcon(){
  var MenuList={};
  MenuList.title = that.data.title;
  MenuList.course = that.data.course;
  MenuList.pic = that.data.pic;
  wx.setStorageSync("MenuList",MenuList);
  wx.showToast({
    title: '存入成功',
    icon: 'success'
  });
}
})