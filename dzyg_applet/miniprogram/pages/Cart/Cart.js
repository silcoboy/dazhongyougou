var that;
var api = require('../../common/api.js');
Page({
  data: {
    inputnumber:1,  //购物产品购买的数量
    Is:100,
    CartList:[],
    allChecked:false,
    totalPrice:0,
    totalNum:0,
    Settlement:false
  },
  //去结算
  settlementBtnfunc() {
    if(that.data.totalNum==0){
      wx.showToast({
        title: '您还没有选购商品',
        icon: 'error',
        duration: 2000
  });
  return;
    }
    that.setData({
      totalPrice:0,
      totalNum:0
    });
    wx.navigateTo({
      url: './Settlement/Settlement',
    })
  },

  addfunc(){
    that.data.inputnumber++;
    that.setData({ inputnumber:that.data.inputnumber });
  },
  handleItemNumEdit(e){
    const {operation,id}=e.currentTarget.dataset;
       var CartList=that.data.CartList;
      const index=CartList.findIndex(v=>v.id===id);
    if(CartList[index].num==1&&operation==-1){
      wx.showModal({
        title: '删除',
        content: '确定要删除该商品么?',
        success(res) {
          if (res.confirm) {
            CartList.splice(index,1);
            wx.showToast({
              title: '删除商品成功',
              icon: 'success',
              duration: 2000
            }); 
             that.setCart(CartList);
          }
        }
      })
    }else{
      CartList[index].num+=operation;
       that.setCart(CartList);
    }
    
    api.get('/miniapi/addCartNum',
    { 
    num:operation,
    id:id,  
    } 
    ).then(res => { 
    if (res.code == 0) { 
      // wx.showToast({
      //   title: '操作成功',
      //   icon: 'success',
      //   duration: 2000
      // });  
       
    }else{
      wx.showToast({
        title: rs.msg,
        icon: 'none',
        duration: 2000
      });
    }
    });
  },


  changefunc: function (e) { //手指触摸后移动
    that.setData({
      dangqianweizhi: e.detail.x
    })
  },
  touchendfunc: function (e) { //手指动作后结束 
    console.log('手指动作后结束' + that.data.dangqianweizhi);
    if (that.data.dangqianweizhi <= 45) {
      console.log(3)
      that.setData({
        ['numList[' + e.currentTarget.dataset.index + '].x']: 0
      })
    } else {
      that.setData({
        ['numList[' + e.currentTarget.dataset.index + '].x']: 90
      })
    }
  },
  touchstartfunc: function (e) {
    console.log('手指触摸动作开始' + that.data.numList[e.currentTarget.dataset.index].x)
    for (var i in that.data.numList) {
      if (that.data.numList[i].x == 0) {
        console.log(5)
        that.setData({
          ['numList[' + i + '].x']: 90,
        })
      }
    }
  },
  deleteProduct(e){
    that.GetCartList();
    var index = e.currentTarget.dataset.index;
    var id = e.currentTarget.dataset.id;
    var CartList=that.data.CartList;
    for (var i in that.data.numList) {
      that.setData({
        ['numList[' + i + '].x']: 90,
      })
    };
    wx.showModal({
      title: '删除',
      content: '确定要删除该商品么?',
      success(res) {
        if (res.confirm) {
          var num = CartList[index].num;
          CartList.splice(index,1);
          wx.showToast({
            title: '删除商品成功',
            icon: 'success',
            duration: 2000
          }); 
          console.log(CartList)
           that.setCart(CartList);
           
           api.get('/miniapi/addCartNum',
           { 
           num:-num,
           id:id,  
           } 
           ).then(res => { 
           if (res.code == 0) { 
             // wx.showToast({
             //   title: '操作成功',
             //   icon: 'success',
             //   duration: 2000
             // });  
              
           }else{
             wx.showToast({
               title: rs.msg,
               icon: 'none',
               duration: 2000
             });
           }
           });

        }
      }
    })
  },
  goindexfunc(){ wx.switchTab({ url: '../Home/Home' }) }, //跳转首页
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    that.GetCartList();
  },
   /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   that=this;
  },
    /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },
  //获取购物车数据
  GetCartList(){
    api.get('/miniapi/userCartList' 
    ).then(res => { 
      if (res.code == 0) {
        var list= res.data.list;
        for(var i = 0;i<list.length;i++){
          list[i].goodsPic = JSON.parse(list[i].goodsPic)
        }
        that.setData({ 
          CartList: list ,
          totalPrice:res.data.amount
        })  
        console.log("有东西吗",that.data.CartList) 
      }
    });
  },
    //设置购物车状态且计算全选 总价 总数
    setCart(CartList){
      // 计算全选
      let allChecked=true;
      // 总数 总价
      let totalPrice=0;
      let totalNum=0;
      console.log("过来",CartList)
      CartList.forEach(v => {
        if(v.checked){
          totalPrice+=v.num * v.goodsPrice;
          totalNum+=v.num;
        }else{
          allChecked=false;
        }
      });
      allChecked=CartList.length!=0?allChecked:false;
     let Settlement=totalNum==0?false:true;
     totalPrice=totalPrice.toFixed(2)
     console.log("赋值",CartList)
     that.setData({
        CartList:CartList,
        allChecked,
        totalPrice,
        totalNum,
        Settlement
      });
      console.log("缓存",CartList)
      wx.setStorageSync("CarList",CartList);
    },
     //商品的选中计算金额
  handeItemChange(e){
   let index =e.currentTarget.dataset.index;
   let CartList =that.data.CartList;
   CartList[index].checked=!CartList[index].checked;
   that.setCart(CartList);
  },
   // 全选与反选
   handleItemAllCheck(){
    let {CartList,allChecked}=that.data;
    allChecked=!allChecked;
    // 循环修改cart数组中的 商品选中状态
    CartList.forEach(v=>v.checked=allChecked);
    that.setCart(CartList);
  },
})