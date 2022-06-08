<template>
  <el-container>

    <el-header>
      <el-input v-model="queryInfo.name"  placeholder="搜索名称"
                prefix-icon="el-icon-search"></el-input>
				<el-button type="primary" style="margin-left: 5px" icon="el-icon-search"
				 @click="getDataInfo">搜索</el-button>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-plus" 
			@click="showPublishDialog">新增
      </el-button>
    </el-header>

    <el-main> 

      <el-table
        ref="multipleTable"
        highlight-current-row
        v-loading="loading"
        :border="true"
        :data="dataInfo"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
              type="selection"
              width="55">
            </el-table-column>
        
      <el-table-column align="center"
                        class="Vegetablesname"
                         prop="name"  
                         label="名称">
        </el-table-column>
		 
	 <el-table-column align="center"
	                    prop="sort"  
	                    label="排序">
	   </el-table-column>
		   
	<el-table-column align="center" width="170"
		                 label="是否推荐">
		
		  <template slot-scope="scope">
		    {{ scope.row.isShow === 1 ? '是' : '否' }}
		  </template>
		</el-table-column> 
 

        <el-table-column align="center"  width="160"
                         label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="showUpdateDialog(scope.row)">编辑</el-button>
		   <el-button size="mini" type="danger" @click="delInfo(scope.row)">删除</el-button>
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

    <el-dialog :title="infoTitle" :visible.sync="publishTableVisible" width="60%"
		
               @close="resetPublishForm"
               center>

      <el-form :inline="true" :model="publishForm" :rules="publishFormRules" ref="publishForm" >

            
			  <el-form-item label="名称" label-width="120px" prop="name">
				<el-input v-model="publishForm.name"></el-input>
			  </el-form-item>
			  
		  
		  <el-form-item label="排序" label-width="120px" prop="sort">
      <el-input v-model="publishForm.sort"></el-input>
		  </el-form-item>
		  
		  <el-form-item label="是否推荐" label-width="110px" prop="isShow">
		   <el-select v-model="publishForm.isShow" placeholder="请选择">
			 <el-option label="是" value="1"></el-option>
			 <el-option label="否" value="0"></el-option> 
		   </el-select>
	  </el-form-item>
		  
		 <el-upload
		    class="avatar-uploader"
		    action="http://localhost:8082/upload/fileUploadFile"
		    :show-file-list="false"
        :on-remove="updateFileList"
		    :on-success="handleAvatarSuccess" >
		    <img v-if="publishForm.icon" :src="publishForm.icon" class="avatar" @click="deleteIcon">
		    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		  </el-upload>
		  
<!-- 			 <el-row>
			 <el-upload class="avatar-uploader" 
			 action="http://localhost:8082/upload/fileUploadFile"
			   list-type="picture-card" :on-success="handleAvatarSuccess"
			   :on-remove="updateFileList"
			   :file-list="publishForm.icon"
			    :limit-count="1"
			    :multiple="true"
			    >
			  
			 	 <i class="el-icon-plus"></i>
			 </el-upload>
			  
			   </el-row> -->
       
       
       
       
       
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveInfo">确认</el-button>
        <el-button @click="publishTableVisible = false">取 消</el-button>
      </div>
    </el-dialog>

     

  </el-container>

</template>

<script> 

  export default {
    name: 'planManage',
    data () {
      return {
		infoTitle:'添加分类',
        queryInfo: {
          'name': '', 
          'pageNo': 1,
          'pageSize': 10
        }, 
        //下拉框所选择的数据
        selected: '',
        // 所有信息
        dataInfo: [],
        // 表格数据预加载
        loading: true,
        // 总数
        total: 0,
        // 被选中的表格行
        selectedInTable: [],
        // 对话框
        publishTableVisible: false, 
        // 表单
        publishForm: {
          
      name: '',
		  id:'',
		  icon: '',
		  sort:0,
		  isShow:0, 
      tips:''
        }, 
        publishFormRules: {
			name: [
			  {
			    required: true,
			    message: '请输入名称',
			    trigger: 'blur'
			  }
			],
			icon: [
			  {
			    required: true,
			    message: '请输入图片',
			    trigger: 'blur'
			  }
			] 
        },
        // 当前更新的信息
        currentUpdateStatus: ''
      }
    },
    created () {
      this.getDataInfo()
    },
    methods: { 
	handleAvatarSuccess(res, file) {
	        this.publishForm.icon =res.extra.url;
			 this.updateForm.icon =res.extra.url;
	} , 
		//获取信息
      getDataInfo () {
        this.$http.get(this.API.getAllCategory, { params: this.queryInfo }).then((resp) => {
          if (resp.data.code === 200) {
            this.dataInfo = resp.data.data.list;
            this.total = resp.data.data.total;
            this.loading = false;
          } else {
            this.$notify({
              title: '获取商品分类失败',
              type: 'error',
              duration: 2000
            })
          }
        })
      },
      updateFileList(file, fileList) {
      		 
         this.publishForm.icon = this.publishForm.icon.filter(v => v.url !== file.url);
       
      	},
        deleteIcon(){
          this.publishForm.icon=''
        },
      //表格某一行被选中
      handleSelectionChange (val) {
        this.selectedInTable = val
      },
      //分页插件的大小改变
      handleSizeChange (val) {
        this.queryInfo.pageSize = val
        this.getDataInfo()
      },
      //分页插件的页数
      handleCurrentChange (val) {
        this.queryInfo.pageNo = val
        this.getDataInfo()
      } ,
      //
      showPublishDialog () {
        console.log(this.publishForm.icon)
		 this.publishForm.id = null;
		  this.publishForm.name = '';
		 this.publishForm.icon = ''; 
		this.publishForm.sort =0;  
			this.publishForm.isShow = "0";  
        this.publishTableVisible = true  
		    this.infoTitle='新增分类'
      } ,
      // 发布的表单信息重置
      resetPublishForm () {
        //清空表格数据
        this.$refs['publishForm'].resetFields()
      } ,
      // 新增
      saveInfo () {
        if(this.publishForm.id==null||this.publishForm.id==''||this.publishForm.id==undefined){
          this.tips='新增成功'
        }else{
          this.tips='编辑成功'
        }
        console.log(this.publishForm)
        this.$refs['publishForm'].validate((valid) => {
          if (valid) {
            this.$http.post(this.API.saveCategory, this.publishForm).then((resp) => {
              if (resp.data.code === 200) {
                this.getDataInfo()
                this.$notify({
                  title: this.tips,
                  type: 'success',
                  duration: 2000
                })
              } else {
                this.$notify({
                  title: '新增失败',
                  type: 'error',
                  duration: 2000
                })
              }
              this.publishTableVisible = false
            })
          } else {
            this.$message.error('请检查您所填写的信息是否有误')
            return false
          }
        })
      },
      // 显示更新的对话框
      showUpdateDialog (row) {
		  this.infoTitle='修改分类'
        this.publishForm.isShow = row.isShow;
		 this.publishForm.id = row.id;
		  this.publishForm.name = row.name;
	     this.publishForm.icon = row.icon;
       console.log(row.sort)
		  this.publishForm.sort = row.sort;  
		  
        this.publishTableVisible = true 
      },delInfo(row) {
		  this.$confirm('您正在删除'+row.name+'分类,是否继续', '提示', {
		     confirmButtonText: '确定',
         cancelButtonText: '取消',
		      type: 'warning',
          customClass: 'confirm-delete'
		    }).then(() => {
				  this.$http.get(this.API.deleteCategory, { params: { 'id': row.id } }).then((resp) => {
					if (resp.data.code === 200) {
					  //删除成功后,回调更新数据
            var pageNo1 = Math.ceil((this.total-1)/10)
            this.queryInfo.pageNo=pageNo1;
					  this.getDataInfo()
					  this.$notify({
						title: '删除成功',
						type: 'success',
						duration: 2000
					  })
					} else {
					  this.$notify({
						title: '删除失败',
						type: 'error',
						duration: 2000
					  })
					}
				  })
		  }).catch(() => {
		  this.$notify({
		    title: '已取消删除',
		    type: 'success',
		    duration: 2000
		  })
		  })
	 },

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
  }

  .el-table {
    box-shadow: 0 0 1px 1px gainsboro;
  }
  
  .avatar-uploader .el-upload {
   
      border: 1px dashed #000066;
   
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    } 
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
  	 background-color: #EEE8D5;
  	margin-left: 150px;
  	 margin-bottom: 20px;
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
  	margin-left: 150px;
  	 margin-bottom: 20px;
      width: 178px;
      height: 178px;
      display: block;
    }
  
</style>
