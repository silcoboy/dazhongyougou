// 接口路径
var domain = 'http://localhost:8082'; 

const http = ({
    url = "",
    param = {},
    ...others
}) => {
    // wx.showLoading({
    //     title: '加载中'
    // });
    for(let p in param){
        if(!param[p]){
            delete param[p]
        }
    }
    return new Promise((resolve, reject) => {
        var token = wx.getStorageSync('token');
        wx.request({
            url: domain + url,
            data: param,
            ...others,
            header: {
                'content-type': 'application/json',                
                'token': token
            },
            complete: (res) => {              
                if (res.data.code === 0) {
                  resolve(res.data)
                } else if (res.data.code == '-1003') {
                  wx.showToast({
                    title: res.data.msg,
                    icon: 'none',
                    duration: 2000
                  });
                  reject(res);
                  wx.navigateTo({
                    url: '../auth/auth',
                  });
                } else {
                  wx.showToast({
                      title: res.data.msg,
                    icon: 'none',
                      duration: 2000
                  })
                  reject(res)
                }
            }
        })
    })
}



// get方法
const get = (url, param = {}) => {
    return http({
        url,
        param
    })
}

const post = (url, param = {}) => {
    return http({
        url,
        param,
        method: 'post'
    })
}

module.exports = {
    domain,
    get,
    post,
}