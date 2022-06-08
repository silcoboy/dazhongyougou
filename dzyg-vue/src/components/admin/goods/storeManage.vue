<template>
	<el-container>

		<el-header>
			<el-input v-model="queryInfo.name"   placeholder="搜索名称" prefix-icon="el-icon-search">
			</el-input>
<el-button type="primary" style="margin-left: 5px" icon="el-icon-search"
				 @click="getDataInfo">搜索</el-button>
			<el-button type="primary" style="margin-left: 5px" icon="el-icon-plus" @click="showPublishDialog">新增
			</el-button>
		</el-header>

		<el-main>

			<el-table ref="multipleTable" highlight-current-row v-loading="loading" :border="true" :data="dataInfo"
				tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
         <el-table-column
               type="selection"
               width="55">
             </el-table-column>
			 

				<el-table-column align="center" :show-overflow-tooltip="true" width="100px" prop="storeName" label="自提点">
				</el-table-column>


				<el-table-column align="center" prop="storeNo" label="编号" width="75px">
				</el-table-column>
            <el-table-column align="center" width="120px" prop="storePhone" 
				label="联系电话">
				</el-table-column>
				<el-table-column align="center" width="100px" prop="linkName"
					label="团长">
					</el-table-column>
				
				<el-table-column align="center" :show-overflow-tooltip="true" prop="storeAddress" label="提货地址">
				</el-table-column>
				
				<el-table-column align="center" width="95px" prop="latitude" label="latitude">
				</el-table-column>

				<el-table-column align="center" width="100px" prop="longitude" label="longitude">
				</el-table-column>

				<el-table-column align="center" width="85px" label="是否上架">

					<template slot-scope="scope">
						{{ scope.row.status === 1 ? '是' : '否' }}
					</template>
				</el-table-column>
				 

				<el-table-column align="center" width="160" label="操作">
					<template slot-scope="scope">
						<el-button size="mini" type="primary" @click="showUpdateDialog(scope.row)">编辑</el-button>
						<el-button size="mini" type="danger" @click="delInfo(scope.row)">删除</el-button>
					</template>

				</el-table-column>

			</el-table>

			<!--分页-->
			<el-pagination style="margin-top: 25px" @size-change="handleSizeChange"
				@current-change="handleCurrentChange" :current-page="queryInfo.pageNo" :page-sizes="[10, 20, 30, 50]"
				:page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
			</el-pagination>

		</el-main>

		<el-dialog :title="infoTitle" :visible.sync="publishTableVisible" width="60%" @close="resetPublishForm" center>

			<el-form :model="publishForm" :rules="publishFormRules" ref="publishForm">

				<el-row>
					<el-col :span="12">
						<el-form-item label="自提点" label-width="120px" prop="storeName">
							<el-input v-model="publishForm.storeName"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						 <el-form-item label="编号" label-width="120px" prop="storeNo">
						 	<el-input   v-model="publishForm.storeNo"></el-input>
						 </el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="团长" label-width="120px" prop="linkName">
							<el-input  v-model="publishForm.linkName"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">

						<el-form-item label="联系电话" label-width="120px" prop="storePhone">
							<el-input  v-model="publishForm.storePhone"></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="是否上架" label-width="120px" prop="status">
							<el-select v-model="publishForm.status" placeholder="请选择">
								<el-option label="是" value="1"></el-option>
								<el-option label="否" value="0"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="提货地址" label-width="120px" prop="storeAddress">
							<el-input  v-model="publishForm.storeAddress"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
 
				<el-row>
				<el-col :span="12">
					<el-form-item label="latitude" label-width="120px" prop="latitude">
						<el-input  v-model="publishForm.latitude"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">

					<el-form-item label="longitude" label-width="120px" prop="longitude">
						<el-input  v-model="publishForm.longitude"></el-input>
					</el-form-item>
				</el-col>
				</el-row>
        <el-row>
        门店图片
        <el-upload class="avatar-uploader" 
        action="http://localhost:8082/upload/fileUploadFile"
          list-type="picture-card" :on-success="handleAvatarSuccess"
          :on-remove="updateFileList"
          :file-list="realFileList"
                :limit-count="5"
           :multiple="true"
           >
         
        	 <i class="el-icon-plus"></i>
        </el-upload>
         
          </el-row>


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
		data() {
			return {
				infoTitle: '添加商品',
				//查询公告的参数
				queryInfo: {
					'name': '',
					'pageNo': 1,
					'pageSize': 10
				},
				//下拉框所选择的数据
				selected: '',
				// 所有信息
				dataInfo: [],
        realFileList: [],
				// 表格数据预加载
				loading: true,
				// 公告总数
				total: 0,
        tips:'',
				// 被选中的表格行
				selectedInTable: [],
				categoryList:[],
				// 发布对话框
				publishTableVisible: false,
				// 发布的表单
				publishForm: {
 
					id: '',
					storeNo: '',
					 storeName: '',
					storeAddress: '',
						linkName: '',
					storePhone: '',
					status: 1,
					latitude: '',
					longitude: '',
          store_pic:[]
          
				},
				publishFormRules: {
					storeName: [{
						required: true,
						message: '请输入名称',
						trigger: 'blur'
					}],
					latitude: [{
						required: true,
						message: '请输入latitude',
						trigger: 'blur'
					}]
				},
				// 当前更新的信息
				currentUpdateStatus: ''
			}
		},
		created() {
			this.getDataInfo()
		},
		methods: {
      handleAvatarSuccess(res, file) {
      	var realFileList=this.realFileList; 
      	         const fileObj = { 
      	           url:res.extra.url 
      	         };
      	         this.realFileList.push(fileObj);
                 console.log("添加",this.realFileList)
      	 
      	//this.fileList.push(res.extra.url);
      	//this.updateForm.picUrl = res.extra.url;
      }, 
      updateFileList(file, fileList) {
      		 
         this.realFileList = this.realFileList.filter(v => v.url !== file.url);
         console.log("删除",this.realFileList)
       
      	},
			//获取信息
			getDataInfo() {
				this.$http.get(this.API.getAllStore, {
					params: this.queryInfo
				}).then((resp) => {
					if (resp.data.code === 200) {
						this.dataInfo = resp.data.data.list;
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
			handleSelectionChange(val) {
				this.selectedInTable = val
			},
			//分页插件的大小改变
			handleSizeChange(val) {
				this.queryInfo.pageSize = val
				this.getDataInfo()
			},
			//分页插件的页数
			handleCurrentChange(val) {
				this.queryInfo.pageNo = val
				this.getDataInfo()
			},
			// 显示发布新闻对话框
			showPublishDialog() {
				this.publishForm.id = null;
				this.publishForm.storeNo = '';
				this.publishForm.storeName = '';
				this.publishForm.status = "1";
				this.publishForm.storeAddress = '';
				this.publishForm.storePhone = "";
				this.publishForm.linkName = "";
				this.publishForm.latitude="";
        this.publishForm.store_pic=[];
				this.publishTableVisible = true
				this.infoTitle = '新增自提点';
				// this.getCategoryList();
			},
			// 发布的表单信息重置
			resetPublishForm() {
				//清空表格数据
        this.realFileList=[]
				this.$refs['publishForm'].resetFields()
			},
			// 发布
			saveInfo() {
       this.publishForm.store_pic = JSON.stringify(this.realFileList);
        if(this.publishForm.id==null||this.publishForm.id==''||this.publishForm.id==undefined){
          this.tips='新增成功'
        }else{
          this.tips='编辑成功'
        }
				this.$refs['publishForm'].validate((valid) => {
					if (valid) {
						this.$http.post(this.API.saveStore, this.publishForm).then((resp) => {
							if (resp.data.code === 200) {
								this.getDataInfo()
                 this.realFileList=[];
								this.$notify({
									title: this.tips,
									type: 'success',
									duration: 2000
								})
							} else {
								this.$notify({
									title: '失败',
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
			// 显示更新的对话框
			showUpdateDialog(row) {
        
				this.infoTitle = '修改自提点'
				this.publishForm.id = row.id;
				this.publishForm.storeNo = row.storeNo;
				this.publishForm.storeName = row.storeName;
				this.publishForm.status = row.status;
			    this.publishForm.storeAddress = row.storeAddress;
				this.publishForm.storePhone = row.storePhone;
				this.publishForm.linkName = row.linkName;
				this.publishForm.latitude = row.latitude;
			   this.publishForm.longitude = row.longitude;
         if(row.storePic!=''||row.storePic!=undefined||row.storePic!=null){
           this.realFileList=JSON.parse(row.storePic);
         }else{
            this.realFileList=[];
         }
         
       
				this.publishTableVisible = true
			},
			delInfo(row) {

				this.$confirm('你正在操作删除，是否继续?', 'Tips', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.get(this.API.deleteStore, {
						params: {
							'id': row.id
						}
					}).then((resp) => {
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
						title: '您已取消',
						type: 'success',
						duration: 2000
					})
				})
			},

		}
	}
</script>

<style scoped lang="scss">
  /deep/.el-tooltip__popper {
      max-width: 190px;
    
    }
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
