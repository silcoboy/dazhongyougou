// pages/Menu/MenuDetail/MenuDetail.js
var api = require('../../../common/api.js')
var that;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    swiperList:[],
    id:'',
    picUrl:[],
    info:'',
    list:[],
    value:''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
  that=this;
  that.data.id= options.id;
  that.loadInfo()
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
  loadInfo(){
    var that=this; 
    api.get('/miniapi/menuDetail',{id:that.data.id} 
    ).then(res => { 
      if (res.code == 0) {
      console.log(res)
        that.setData({ 
          picUrl:JSON.parse(res.data.info.picUrl),
          // title:res.data.info.title,
          // course:res.data.info.content,
          // createTime:res.data.info.createTime,
          info:res.data.info,
          list:res.data.list
        })  
        console.log(that.data.picUrl)
        console.log(that.data.title)
        console.log(that.data.course)
      }
    });
  },
    //在新页面中全屏预览图片。预览的过程中用户可以进行保存图片、发送给朋友等操作
    previewImage(e){
      wx.previewImage({
        current: that.data.picUrl[e.currentTarget.dataset.index], // 当前显示图片的http链接
        urls: that.data.picUrl // 需要预览的图片http链接列表
      })
    },
    deleteComment(e){
      wx.showModal({
        title: '提示',
        content: '确定要删除此评论吗',
        success (res) {
          if (res.confirm) {
             
            wx.showLoading({
              title: '操作中',
            })
  
            api.get('/miniapi/delMenuComment',
            {
                  id: e.currentTarget.dataset.id  
            }
          ).then(res => { 
            wx.showToast({
              title: '删除成功',
            })
            that.loadInfo();
          });
  
  
          } else if (res.cancel) {
          
          }
        }
      }) 
    },
    toComment(event){
    if(event.detail==''){
    wx.showToast({
      title: '请输入评论内容',
      icon:'error'
    }) 
    return;
    }
    
    api.get('/miniapi/addMenuComment',
    { 
      content:event.detail,
      id:that.data.id} 
    ).then(res => { 
      if (res.code == 0) { 
        wx.showToast({
          title: '评论成功',
          icon: 'success',
        });  
        
    
      }else{
        wx.showToast({
          title: rs.msg,
          icon: 'error',
          duration: 2000
        });
      }
    });
    that.setData({
    value:''
    })
    that.loadInfo()
   
    }
})