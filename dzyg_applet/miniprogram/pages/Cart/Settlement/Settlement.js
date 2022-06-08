var that;
var api = require('../../../common/api.js');
Page({
  data: {
    maskShow:false,             //遮罩层show/hide
    showToastboxstyle:"-860rpx;",
    stopPageScroll:"",
    CartList:[],
    totalPrice:0,
    totalNum:0, 
    StoreList:[],
    Address:[],
    addressId:''
  },

  //去支付
  gopaymentfunc(e){
    this.setData({
      showToastboxstyle:"bottom:0rpx;",
      maskShow:true,
      stopPageScroll:"stopPageScroll"
    })
  },
  //点击取消  遮罩层
  hideshowToastboxfunc(){
    this.setData({
      showToastboxstyle: "bottom:-860rpx;",
      maskShow: false,
      stopPageScroll:""
    })
  },
  onLoad(options){
    console.log("id",options)
    this.setData({ 
      addressId: options.addressId
    })  
  
  },
  onShow(){ 
    that=this;
    this.GetStoreList();
    let CartList=wx.getStorageSync("CarList")||[];
    console.log("测试",CartList)
    // 过滤购物车数组 过滤后checked都为true 数据是从缓存中获取的并不是从购物车中传过来的
    CartList=CartList.filter(v=>v.checked);
    
    let totalPrice=0;
    let totalNum=0;
    CartList.forEach(v => {
      totalPrice+=v.num * v.goodsPrice;
      totalNum+=v.num;
    });
    for(var i = 0;i<CartList.length;i++){
    CartList[i].amount=(CartList[i].goodsPrice*CartList[i].num).toFixed(2)
    }
    console.log("数据1",CartList)
    totalPrice=totalPrice.toFixed(2)
    this.setData({
      CartList,
      totalPrice,
      totalNum
    })
    console.log("测试",CartList)
    
    
  },
  GetStoreList(){
    api.get('/miniapi/listStore' 
    ).then(res => { 
      if (res.code == 0) {
        var store=res.data.storeList[0];
        if(that.data.addressId){
       
            for(var i=0;i<res.data.storeList.length;i++){ 
              if(res.data.storeList[i].id==that.data.addressId){
                store=res.data.storeList[i];
                break;
              }
            }
        }

        that.setData({ 
          StoreList: res.data.storeList, 
          Address:store,
        })   
      }
    });
  },
  handleChooseAddress(){
    wx.redirectTo({url: "../../Store/Store?back=1"});
  },
  //支付订单
  handleOrderPay(){
    wx.showModal({
      title: '提示',
      content: '您确认要支付操作吗？',
      success (res) {
        if (res.confirm) {
           
           that.payOrd(2);


        } else if (res.cancel) {
          that.payOrd(1);
        }
      }
    }) 
  },
  payOrd(status){
    let goodsId=""; 
    if(this.data.CartList.length==1){
      goodsId=this.data.CartList[0].id; 
    }else{
      this.data.CartList.forEach(i => {
        goodsId+=i.id+","; 
      });
    };
    api.get('/miniapi/submitCart',
{ 
storeId:that.data.Address.id,
ids:goodsId,  
status:status
} 
).then(res => { 
if (res.code == 0) {  
  wx.removeStorage({
    key: 'CartList',
  })
  //  wx.navigateTo({
  //    url: '../../My/MyOrder/MyrOder',
  //  })
   wx.redirectTo({
    url: '../../My/MyOrder/MyrOder',
   })
} 
});
  }, 
stopPageScroll(){return}
})