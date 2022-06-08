<template>
  <el-container>
    <el-main>
      <el-card class="box-card" shadow="always">
        <div slot="header" class="card-header">
          <p>大众优购小程序-登录</p>
        </div>

        <div>
          <el-form :model="loginForm" :rules="loginFormRules" ref="loginForm" :status-icon="true" label-width="100px">
            <el-form-item prop="username">
              <el-input prefix-icon="el-icon-user" v-model="loginForm.username" placeholder="账号"></el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" v-model="loginForm.password" placeholder="密码"
                        show-password></el-input>
            </el-form-item> 

            <el-form-item>
              <el-button type="primary" @click="submitForm('loginForm')" icon="el-icon el-icon-s-promotion">登录
              </el-button>
             <!-- <el-button type="warning" @click="toRegisterPage" icon="el-icon el-icon-circle-plus">学生注册</el-button> -->
            </el-form-item>

          </el-form>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>

  export default {
    name: 'Login',
    data () {
    
      return {
        //登录表单数据信息
        loginForm: {
          username: '',
          password: '', 
        },
        //登录表单的校验规则
        loginFormRules: {
          username: [
            {
              required: true,
              message: '请输入账号',
              trigger: 'blur'
            },
          ],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 5,
              message: '密码不能小于5个字符',
              trigger: 'blur'
            }
          ], 
        } 
      }
    },
    mounted () { 
      //检验用户是否存在token,存在直接跳转主页
      this.checkToken()
    },
    methods: {
      //表单信息提交
      submitForm () {
        this.$refs['loginForm'].validate((valid) => {
          if (valid) {//验证通过
            const data = new FormData()
            data.append('username', this.loginForm.username)
            data.append('password', this.loginForm.password)
            //发送登录请求
            this.$http.post(this.API.login, data).then((resp) => {
              if (resp.data.code === 200) {
                localStorage.setItem('authorization', resp.data.data.token); 
                this.$notify({
                  title: 'Tips',
                  message: '登陆成功^_^',
                  type: 'success',
                  duration: 2000
                })
                this.$router.push('/index')
              } else {//请求出错 
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
                  type: 'error',
                  duration: 2000
                })
              }
            })
          } 
        })
      },
      //注册页面跳转
      toRegisterPage () {
        this.$router.push('/register')
      } ,
      //检验token
      async checkToken () {
        if (window.localStorage.getItem('authorization') !== null) {
          const resp = await this.$http.get(this.API.checkToken)
          if (resp.data.code === 200) {//如果token合法自动跳转主页
            await this.$router.push('/index')
          }
        }
      }
    }
  }
</script>

<style scoped lang="scss">
  .el-container {
    min-width: 417px;
    height: 100%;
    background: url("../../../assets/imgs/bj.png");
    background-size: 100% 100%;
  }

  a {
    text-decoration: none;
    color: blueviolet;
  }

  /*  card样式  */
  .box-card {
    width: 450px;
  }

  .el-card {
    position: absolute;
    top: 45%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    border-radius: 15px;
  }

  .card-header {
    text-align: center;

    p {
      font-size: 20px;
    }
  }

  /*  表单的左侧margin清楚 */
  /deep/ .el-form-item__content {
    margin: 0 !important;
  }

  /*  按钮样式 */
  .el-button {
    width: 100%;
  }

  /*  按钮前的小图标样式更改*/
  /deep/ .el-icon {
    margin-right: 3px;
  }

  /*  验证码的输入框*/
  .code {
    width: 72%;
  }
</style>
