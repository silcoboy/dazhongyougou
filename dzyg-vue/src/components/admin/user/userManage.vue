<template>
  <el-container>

    <el-header> 
      <el-input v-model="queryInfo.nickName"   placeholder="搜索用户" prefix-icon="el-icon-search"
                style="margin-left: 5px"></el-input>
				
				<el-button type="primary" style="margin-left: 5px" icon="el-icon-search"
				 @click="getUserInfo">搜索</el-button>
    </el-header>

    <el-main>


      <el-table
        ref="multipleTable"
        highlight-current-row
        v-loading="loading"
        :border="true"
        :data="userInfo"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">

        <el-table-column align="center"
                         type="selection"
                         width="55">
        </el-table-column>

        <el-table-column align="center"
                         prop="nickname"
                         label="昵称">
        </el-table-column>
		 
		
		<el-table-column align="center"
		                 prop="mobile"
		                 label="手机号">
		</el-table-column>
		  
		 
        <el-table-column align="center"   width="180"
                         label="时间">
          <template slot-scope="scope">
            {{ scope.row.createTime }}
          </template>
        </el-table-column>
 
		
		
		<el-table-column align="center"
		                 label="操作" width="180">
		  <template slot-scope="scope">
			   <el-button size="mini"   type="danger" @click="delInfo(scope.row)">删除</el-button>  
			</template>
		 
		</el-table-column>

      </el-table>

      <!--分页-->
      <el-pagination style="margin-top: 25px"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="queryInfo.pageNo"
                     :page-sizes="[10, 20, 30, 50]"
                     :page-size="queryInfo.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>

    </el-main>
 

  </el-container>

</template>

<script>
  export default {
    name: 'UserManage',
    data () { 
      return {
        //查询用户的参数
        queryInfo: { 
          'nickName': '', 
          'pageNo': 1,
          'pageSize': 10
        },
        //用户信息
        userInfo: [],
        //下拉框所选择的数据
        selected: '',
        //被选择的表格中的行数据
        selectedInTable: [],
        //所有用户的条数
        total: 0,
        //添加用户的对话框是否显示
        addTableVisible: false,
        //添加用户的表单信息
        //表格信息加载
        loading: true
      }
    },
    created () {
      this.getUserInfo()
    },
    methods: {
      //获取用户信息
      getUserInfo () {
        this.$http.get(this.API.getUserInfo, { params: this.queryInfo }).then((resp) => {
          if (resp.data.code === 200) {
            this.userInfo = resp.data.data.users;
            this.total = resp.data.data.total;
            this.loading = false;
          } else {
            this.$notify({
              title: 'Tips',
              message: '获取信息失败',
              type: 'error',
              duration: 2000
            })
          }
        })
      },
      //表格某一行被选中
      handleSelectionChange (val) {
        this.selectedInTable = val
      } ,
      //分页插件的大小改变
      handleSizeChange (val) {
        this.queryInfo.pageSize = val
        this.getUserInfo()
      },
      //分页插件的页数
      handleCurrentChange (val) {
        this.queryInfo.pageNo = val
        this.getUserInfo()
      },delInfo(row) {
		   this.$confirm('你正在操作删除注销，是否继续?', 'Tips', {
			   confirmButtonText: '确定',
			     cancelButtonText: '取消',
			     type: 'warning'
			   }).then(() => {
				   this.$http.get(this.API.deleteUser, { params: { 'id': row.id,status:0 } }).then((resp) => {
				     if (resp.data.code === 200) {
				       //删除成功后,回调更新数据
               var pageNo1 = Math.ceil((this.total-1)/10)
               this.queryInfo.pageNo=pageNo1;
				       this.getUserInfo()
				       this.$notify({
				         title: '删除成功',
				         type: 'success',
				         duration: 2000
				       })
				     } else {
				       this.$notify({
				         title: 'Tips',
				         message: '操作失败',
				         type: 'error',
				         duration: 2000
				       })
				     }
				   })
				   
		   }).catch(() => {
			 this.$notify({
			   title: 'Tips',
			   message: '您已取消!',
			   type: 'success',
			   duration: 2000
			 })
		   })
			   
			   
		 
	 }
    }
  }
</script>

<style scoped lang="scss">
  /deep/.el-table td, .el-table th{
       padding:5px 0 !important;
    
    }
  .el-container {
    width: 100%;
    height: 100%;
  }

  .el-input {
    width: 200px;
  }

  .el-container {
    animation: leftMoveIn .7s ease-in;
  }

  @keyframes leftMoveIn {
    0% {
      transform: translateX(-100%);
      opacity: 0;
    }
    100% {
      transform: translateX(0%);
      opacity: 1;
    }
  }

  .role {
    color: #606266;
  }

  /deep/ .el-table thead {
    color: rgb(85, 85, 85) !important;
  }

  /*表格的头部样式*/
  /deep/ .has-gutter tr th {
    background: rgb(242, 243, 244);
    color: rgb(85, 85, 85);
    font-weight: bold;
    line-height: 32px;
  }

  .el-table {
    box-shadow: 0 0 1px 1px gainsboro;
  }
</style>
