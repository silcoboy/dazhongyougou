<template>
  <el-container>

    <el-header>
      <el-input v-model="queryInfo.title" @blur="getNoticeInfo" placeholder="搜索资讯"
                prefix-icon="el-icon-search"></el-input>

      <el-button type="primary" style="margin-left: 5px" icon="el-icon-plus" @click="showPublishNoticeDialog">发布
      </el-button>
    </el-header>

    <el-main>
      <!--操作的下拉框-->
      <el-select @change="selectChange" clearable v-if="selectedInTable.length !== 0" v-model="selected"
                 :placeholder="'已选择' + selectedInTable.length + '项'" style="margin-bottom: 25px;">

        <el-option v-for="(item,index) in optionInfo" :key="index" :value="item.desc">
          <span style="float: left">{{ item.label }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.desc }}</span>
        </el-option>

      </el-select>

      <el-table
        ref="multipleTable"
        highlight-current-row
        v-loading="loading"
        :border="true"
        :data="noticeInfo"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">

        <el-table-column align="center"
                         type="selection"
                         width="55">
        </el-table-column>
		
      <el-table-column align="center"
                         prop="title"
                         label="标题">
        </el-table-column>

        <el-table-column label="资讯内容" width="400px;">
          <template slot-scope="scope">
            <p v-html="scope.row.content"></p>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         prop="createTime"
                         label="发布时间">
        </el-table-column>

        <el-table-column align="center"
                         label="更新时间">

          <template slot-scope="scope">
            {{ scope.row.updateTime === null ? '暂无更新' : scope.row.updateTime }}
          </template>
        </el-table-column> 

        <el-table-column align="center"
                         label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="warning" @click="showUpdateNoticeDialog(scope.row)">编辑</el-button>
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

    <el-dialog title="发布资讯" :visible.sync="publishTableVisible" width="60%"
		
               @close="resetPublishForm"
               center>

      <el-form :model="publishForm" :rules="publishFormRules" ref="publishForm">

       <el-form-item label="标题" label-width="120px" prop="title">
            <el-input v-model="publishForm.title"></el-input>
          </el-form-item>
		  
		  <el-upload  
		    class="avatar-uploader"
		    action="http://localhost:8082/upload/fileUploadFile"
		    :show-file-list="false"
		    :on-success="handleAvatarSuccess" >
		    <img v-if="publishForm.imgUrl" :src="publishForm.imgUrl" class="avatar">
		    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		  </el-upload>

        <el-form-item label="资讯内容" label-width="120px" prop="content">
          <div id="publishEditor" ></div>
        </el-form-item>
 

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="publishTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="publishNotice">发 布</el-button>
      </div>
    </el-dialog>

    <el-dialog title="更新资讯" :visible.sync="updateTableVisible" width="60%"
               @close="$refs['updateForm'].resetFields()"
               center>

      <el-form :model="updateForm" :rules="updateFormRules" ref="updateForm">
		  
		  <el-form-item label="标题" label-width="120px" prop="title">
		       <el-input v-model="updateForm.title"></el-input>
		     </el-form-item>
			 
			 <el-upload
			   class="avatar-uploader"
			   action="http://localhost:8082/upload/fileUploadFile"
			   :show-file-list="false"
			   :on-success="handleAvatarSuccess" >
			   <img v-if="updateForm.imgUrl" :src="updateForm.imgUrl" class="avatar">
			   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
			 </el-upload>

        <el-form-item label="资讯内容" label-width="120px" prop="content">
          <div id="updateEditor"></div>
        </el-form-item>

 

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="updateTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateNotice">更 新</el-button>
      </div>
    </el-dialog>

  </el-container>

</template>

<script>
  // 导入富文本编辑器
  import E from 'wangeditor'

  export default {
    name: 'NoticeManage',
    data () {
      return {
        //查询公告的参数
        queryInfo: {
          'title': '',
		   'source':3,
          'pageNo': 1,
          'pageSize': 10
        },
        //下拉选择框的数据
        optionInfo: [
          {
            'label': '删除',
            'desc': 'delete'
          }
        ],
        //下拉框所选择的数据
        selected: '',
        // 所有公告信息
        noticeInfo: [],
        // 表格数据预加载
        loading: true,
        // 公告总数
        total: 0,
        // 被选中的表格行
        selectedInTable: [],
        // 发布公告对话框
        publishTableVisible: false,
        // 发布的表单
        publishForm: {
          content: '',
		  imgUrl:'',
          title: ''
        },
        publishFormRules: {
			title: [
			  {
			    required: true,
			    message: '请输入公告标题',
			    trigger: 'blur'
			  }
			], 
          content: [
            {
              required: true,
              message: '请输入公告内容',
              trigger: 'blur'
            }
          ]
        },
        // 发布公告的富文本编辑器
        publishEditor: new E('#publishEditor'),
        // 更新公告的富文本编辑器
        updateEditor: new E('#updateEditor'),
        // 更新公告的对话框是否展示
        updateTableVisible: false,
        // 更新的表单
        updateForm: {
          id: '',
          content: '',
		  imgUrl: '',
          title: ''
        },
        updateFormRules: {
			title: [
			  {
			    required: true,
			    message: '请输入公告标题',
			    trigger: 'blur'
			  }
			],
          content: [
            {
              required: true,
              message: '请输入公告内容',
              trigger: 'blur'
            }
          ]
        },
        // 当前更新的公告的信息
        currentUpdateNoticeStatus: ''
      }
    },
    created () {
      this.getNoticeInfo()
    },
    methods: { 
		handleAvatarSuccess(res, file) { 
		        this.publishForm.imgUrl =res.extra.url;
				 this.updateForm.imgUrl =res.extra.url;
		} , 
      getNoticeInfo () {
        this.$http.get(this.API.getAllNotice, { params: this.queryInfo }).then((resp) => {
          if (resp.data.code === 200) {
            this.noticeInfo = resp.data.data.notices;
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
      },
      //分页插件的大小改变
      handleSizeChange (val) {
        this.queryInfo.pageSize = val
        this.getNoticeInfo()
      },
      //分页插件的页数
      handleCurrentChange (val) {
        this.queryInfo.pageNo = val
        this.getNoticeInfo()
      },
      //功能下拉框被选择
      selectChange (val) {
        //清空上一次的操作
        this.selected = ''
        //表格中所选中的公告的id
        let ids = []
        this.selectedInTable.map(item => {
          if (item.status !== 1) {// **防止删除当前使用公告
            ids.push(item.id)
          }
        })
        if (val === 'delete') {//删除
          if (ids.length === 0) {
            this.$message.error('不允许删除当前使用公告')
          } else {
            this.$http.get(this.API.deleteNotice, { params: { 'ids': ids.join(',') } }).then((resp) => {
              if (resp.data.code === 200) {
                //删除成功后,回调更新数据
                this.getNoticeInfo()
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
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
          }
        }
      },
      // 显示发布新闻对话框
      showPublishNoticeDialog () {
        this.publishTableVisible = true
        window.setTimeout(() => {
          this.createPublishWangEditor()
        }, 200)
      },
      // 发布公告的表单信息重置
      resetPublishForm () {
        //清空表格数据
        this.$refs['publishForm'].resetFields()
      },
      // 创建新增新闻的富文本编辑器
      createPublishWangEditor () {
        // 设置编辑区域高度为 300px
        this.publishEditor.config.height = 180
        this.publishEditor.config.onchange = (html) => {
          this.publishForm.content = html
        }
        this.publishEditor.txt.html(this.publishForm.content)
        this.publishEditor.create()
      },
      // 创建更新新闻的富文本编辑器
      createUpdateWangEditor () {
        // 设置编辑区域高度为 300px
        this.updateEditor.config.height = 180
        this.updateEditor.config.onchange = (html) => {
          this.updateForm.content = html
        }
        this.updateEditor.create()
        this.updateEditor.txt.html(this.updateForm.content)
      },
      // 发布公告
      publishNotice () {
        this.$refs['publishForm'].validate((valid) => {
          if (valid) {
            this.$http.post(this.API.publishNotice, this.publishForm).then((resp) => {
              if (resp.data.code === 200) {
                this.getNoticeInfo()
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
                  type: 'success',
                  duration: 2000
                })
              } else {
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
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
      // 显示更新公告的对话框
      showUpdateNoticeDialog (row) {
        this.updateForm.content = row.content
		 this.updateForm.title = row.title
        this.updateForm.id = row.id 
		this.updateForm.imgUrl=row.imgUrl
        this.updateTableVisible = true
        // 创建富文本编辑器
        window.setTimeout(() => {
          this.createUpdateWangEditor()
        }, 200)
      },
      // 更新公告
      updateNotice () {
        this.$refs['updateForm'].validate((valid) => {
          if (valid) {
            this.$http.post(this.API.updateNotice, this.updateForm).then((resp) => {
              if (resp.data.code === 200) {
                this.getNoticeInfo()
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
                  type: 'success',
                  duration: 2000
                })
              } else {
                this.$notify({
                  title: 'Tips',
                  message: resp.data.msg,
                  type: 'error',
                  duration: 2000
                })
              }
              this.updateTableVisible = false
            })
          } else {
            this.$message.error('请检查您所填写的信息是否有误')
            return false
          }
        })
      },

    }
  }
</script>

<style scoped lang="scss">
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
