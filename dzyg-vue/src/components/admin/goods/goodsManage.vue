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

				<el-table-column align="center" prop="categoryName" label="分类">
				</el-table-column>

				<el-table-column align="center" width="180px" :show-overflow-tooltip="true" prop="name" label="商品名称">
				</el-table-column>
	<el-table-column align="center" prop="resume" label="简介" :show-overflow-tooltip="true">
				</el-table-column>

				<el-table-column align="center" width="60px" prop="oldPrice" label="原价">
				</el-table-column>

				<el-table-column align="center"  prop="price" label="出售价格">
				</el-table-column>
				<el-table-column align="center" width="98px" prop="totalNum" label="总库存数量">
				</el-table-column>
				<el-table-column align="center" prop="curNum" label="当前剩余">
				</el-table-column>

				<el-table-column align="center" prop="totalBuyNum" label="累计销售">
				</el-table-column>

				<el-table-column align="center" width="78px" label="是否上架">

					<template slot-scope="scope">
						{{ scope.row.status === 1 ? '是' : '否' }}
					</template>
				</el-table-column>
				<el-table-column align="center" width="78px" label="是否特价">

					<template slot-scope="scope">
						{{ scope.row.isSpecial === 1 ? '是' : '否' }}
					</template>
				</el-table-column>

				<el-table-column align="center" width="78px" label="是否秒杀">

					<template slot-scope="scope">
						{{ scope.row.isSkill === 1 ? '是' : '否' }}
					</template>
				</el-table-column>
				
				<el-table-column align="center" width="78px" label="是否推荐">
				
					<template slot-scope="scope">
						{{ scope.row.isRecommend === 1 ? '是' : '否' }}
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

			<el-form   :model="publishForm" :rules="publishFormRules" ref="publishForm">

				<el-row>
					<el-col :span="12">
						<el-form-item label="名称" label-width="120px" prop="name">
							<el-input v-model="publishForm.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="商品分类" width="80px" prop="categoryId">
							<el-select v-model="publishForm.categoryId"  >

								<el-option v-for="(item,index) in categoryList" :key="index" :value="item.id"
									:label="item.name">
									<span>ID：{{ item.id }}</span>
									<span
										style="padding-left:20px;color: #8492a6; font-size: 13px">名称：{{ item.name }}</span>
								</el-option>

							</el-select>
						</el-form-item>
					</el-col>
				</el-row>

      <el-row>
      	<el-col :span="11">
      <el-form-item label="简介" label-width="120px" prop="resume">
      	<el-input  v-model="publishForm.resume" style="height: 30px;width: 200px;"></el-input>
      </el-form-item>
           </el-col> 
       <el-col :span="12">
       				<el-form-item label="产地" label-width="105px" prop="address">
       					<el-input   v-model="publishForm.address" style="width: 220px;"></el-input>
       				</el-form-item>
       </el-col> 
      </el-row>




				<el-row>
					<el-col :span="7">
						<el-form-item label="原价" label-width="120px"  prop="oldPrice">
							<el-input type=number v-model="publishForm.oldPrice" style="width: 140px;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">

						<el-form-item label="出售价格" label-width="120px" prop="price">
							<el-input type=number v-model="publishForm.price" style="width: 140px;"></el-input>
						</el-form-item>
					</el-col>
          <el-col :span="4">
          	<el-form-item label="数量" label-width="120px" prop="curNum">
          		<el-input type=number v-model="publishForm.curNum" style="width: 140px;"></el-input>
          	</el-form-item>
          </el-col>
				</el-row>

				<el-row>
					<!-- <el-col :span="12">
						<el-form-item label="数量" label-width="120px" prop="curNum">
							<el-input type=number v-model="publishForm.curNum"></el-input>
						</el-form-item>
					</el-col> -->
					<el-col :span="5">
						<el-form-item label="是否上架" label-width="120px" prop="status">
							<el-select v-model="publishForm.status" placeholder="请选择" style="width: 85px;">
								<el-option label="是" value="1"></el-option>
								<el-option label="否" value="0"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
          <el-col :span="5">
          	<el-form-item label="是否特价" label-width="120px" prop="isSpecial">
          		<el-select v-model="publishForm.isSpecial" placeholder="请选择" style="width: 85px;">
          		<el-option label="是" value="1"></el-option>
          		<el-option label="否" value="0"></el-option>
          		</el-select>
          	</el-form-item>
          </el-col>
          <el-col :span="5">
          
          	<el-form-item label="是否秒杀" label-width="120px" prop="isSkill">
          		<el-select v-model="publishForm.isSkill" placeholder="请选择" style="width: 85px;">
          			<el-option label="是" value="1"></el-option>
          			<el-option label="否" value="0"></el-option>
          		</el-select>
          	</el-form-item>
          </el-col>
          <el-col :span="5">
          	<el-form-item label="是否推荐" label-width="120px" prop="isRecommend">
          		<el-select v-model="publishForm.isRecommend" placeholder="请选择" style="width: 85px;">
          		<el-option label="是" value="1"></el-option>
          		<el-option label="否" value="0"></el-option>
          		</el-select>
          	</el-form-item>
          </el-col> 
				</el-row>
        
	        <el-row> 
				 商品图片
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
			
			<el-row>
				 商品详情
				<el-upload class="avatar-uploader" 
				action="http://localhost:8082/upload/fileUploadFile"
				  list-type="picture-card" :on-success="handleDetailImgSuccess"
				  :on-remove="updateImgDetailList"
				  :file-list="detailPicList"
				        :limit-count="5"
				   :multiple="true">
				 
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
				// 表格数据预加载
				loading: true,
				// 公告总数
				total: 0,
        tips:'',
				// 被选中的表格行
				selectedInTable: [],
				categoryList:[],
				detailPicList: [], //fileList仅仅作为展示使用
				realFileList: [], // 所有关于文件的上传和删除直接操作 realFileList。
				// 发布对话框
				publishTableVisible: false,
				// 发布的表单
				publishForm: {

					name: '',
					id: '',
					address:'',
					picUrl: '',
					categoryId: '',
					oldPrice: '',
					price: '',
					curNum: '',
					resume: '',
					status: 1,
					imgs:'',
					isSpecial: 0,
					isRecommend:0,
					isSkill: 0,
				},
				publishFormRules: {
					name: [{
						required: true,
						message: '请输入名称',
						trigger: 'blur'
					}],
					picUrl: [{
						required: true,
						message: '请输入图片',
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
			
			    updateImgDetailList(file, fileList) {
					 
			       this.detailPicList = this.detailPicList.filter(v => v.url !== file.url);
				  
				},
			handleDetailImgSuccess(res, file) {
				var detailPicList=this.detailPicList; 
				         const fileObj = { 
				           url:res.extra.url 
				         };
				 
				         this.detailPicList.push(fileObj);
				 
				//this.fileList.push(res.extra.url);
				//this.updateForm.picUrl = res.extra.url;
			},  updateFileList(file, fileList) {
					 
			   this.realFileList = this.realFileList.filter(v => v.url !== file.url);
			 
				},
			handleAvatarSuccess(res, file) {
				var realFileList=this.realFileList; 
				         const fileObj = { 
				           url:res.extra.url 
				         };
				        console.log(res.extra.url)
				         this.realFileList.push(fileObj);
                 console.log(this.realFileList)
				 
				//this.fileList.push(res.extra.url);
				//this.updateForm.picUrl = res.extra.url;
			},  getCategoryList () {
				this.$http.get(this.API.listCategory).then((resp) => {
				  this.categoryList = resp.data;  
				})
      },
			//获取信息
			getDataInfo() {
				this.$http.get(this.API.getAllGoods, {
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
				this.publishForm.name = '';
				this.publishForm.picUrl = '';
				this.publishForm.imgs = '';
				this.publishForm.price = '';
				this.publishForm.oldPrice = '';
				this.publishForm.resume = '';
				this.publishForm.address = '';
				this.publishForm.status = "1";
				this.publishForm.curNum = '';
				this.publishForm.isSkill = "0";
				this.publishForm.isSpecial = "0";
				this.publishForm.categoryId="";
				this.publishForm.isRecommend="0";
				this.publishTableVisible = true
				this.infoTitle = '新增商品';
				this.getCategoryList();
			},
			// 发布的表单信息重置
			resetPublishForm() {
				//清空表格数据
        this.realFileList=[];
        this.detailPicList=[];
				this.$refs['publishForm'].resetFields()
			},
			// 新增
			saveInfo() {
        var picUrl=[];
				var realFileList=this.realFileList; 
        console.log("商品",realFileList)
				for(var i=0;i<realFileList.length;i++){
					picUrl.push(realFileList[i].url);
				} 
				this.publishForm.picUrl=JSON.stringify(picUrl); 
				
				var imgs=[];
				var detailPicList=this.detailPicList; 
        console.log("详情",realFileList)
				for(var i=0;i<detailPicList.length;i++){
          imgs.push(detailPicList[i].url)
				} 
				this.publishForm.imgs=JSON.stringify(imgs); 
				
				if(this.publishForm.id==null||this.publishForm.id==''||this.publishForm.id==undefined){
				  this.tips='新增成功'
				}else{
				  this.tips='编辑成功'
				}
				this.$refs['publishForm'].validate((valid) => {
					if (valid) {
						this.$http.post(this.API.saveGoods, this.publishForm).then((resp) => {
							if (resp.data.code === 200) {
                this.realFileList=[];
                this.detailPicList=[];
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
			showUpdateDialog(row) {
				this.infoTitle = '修改商品'
				this.publishForm.isSkill = row.isSkill+"";
				this.publishForm.isRecommend=row.isRecommend+"";
				this.publishForm.id = row.id;
				this.publishForm.address = row.address;
				this.publishForm.name = row.name;
				this.publishForm.isSpecial = row.isSpecial+"";
				this.publishForm.status = row.status+"";
			    this.publishForm.resume = row.resume;
				this.publishForm.price = row.price;
				this.publishForm.oldPrice = row.oldPrice;
				this.publishForm.curNum = row.curNum;
				this.publishForm.categoryId = row.categoryId;
        if(row.picUrl!=''||row.picUrl!=undefined||row.picUrl!=null){
         this.realFileList = JSON.parse(row.picUrl); 
        }else{
          this.realFileList = [];
        }
        if(row.imgs!=''||row.imgs!=undefined||row.imgs!=null){
         this.detailPicList = JSON.parse(row.imgs); 
        }else{
          this.realFileList = [];
        }
                /* var fileList = row.picUrl.split(",") ;
				var realFileList=[];
					for(var i=0;i<fileList.length;i++){
						var  fileObj = {
						  name:i,
						  url:fileList[i] 
						 };
						 realFileList.push(fileObj)
						} */
				this.publishTableVisible = true
			},
			delInfo(row) {

				this.$confirm('你正在操作删除，是否继续?', 'Tips', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.get(this.API.deleteGoods, {
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
