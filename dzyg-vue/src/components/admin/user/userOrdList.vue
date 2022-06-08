<template>
	<el-container>

		<el-header>
			<el-input v-model="queryInfo.name"   placeholder="搜索收货人" prefix-icon="el-icon-search">
			</el-input>
			<el-input v-model="queryInfo.orderSn"   placeholder="搜索订单号" prefix-icon="el-icon-search">
			</el-input>
 <el-button type="primary" style="margin-left: 5px" icon="el-icon-search"
  @click="getDataInfo">搜索</el-button>
		</el-header>

		<el-main>

			<el-table ref="multipleTable" highlight-current-row v-loading="loading" :border="true" :data="dataInfo"
				tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column align="center"
                         type="selection"
                         width="55">
        </el-table-column>
			 

				<el-table-column align="center" prop="userName" label="用户名称" width="85px">
				</el-table-column>


				<el-table-column align="center" prop="orderSn" label="订单编号" width="135px">
				</el-table-column>
            <el-table-column align="center" :show-overflow-tooltip="true"  prop="goodsName" 
				label="商品名称">
				</el-table-column>
				<el-table-column align="center" width="65px" prop="num"
					label="数量">
					</el-table-column>
				<el-table-column align="center" width="95px" prop="amount"
					label="实付金额">
					</el-table-column>
				<el-table-column align="center" prop="takeStoreName" label="自取点" width="95px">
				</el-table-column>
				
				 

				<el-table-column align="center" width="170" label="订单状态">

					<template slot-scope="scope">
						 <span v-if="scope.row.orderStatus==1">待支付</span>
						  <span v-if="scope.row.orderStatus==2">待配货</span>
						 <span v-if="scope.row.orderStatus==3">待提货</span>
						 	 <span v-if="scope.row.orderStatus==4">已提货
							 <span v-if="scope.row.refundStatus==1">-退款中</span>
							  <span v-if="scope.row.refundStatus==2">-退款成功</span>
							  	  <span v-if="scope.row.refundStatus==3">-退款拒绝</span>
							 </span>
					</template>
				</el-table-column>
				 

				<el-table-column align="center" width="160" label="操作">
					<template slot-scope="scope">
						<el-button size="mini" type="primary" @click="showDetailDialog(scope.row)">详情</el-button>
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

			<el-form :model="publishForm"  ref="publishForm">

				<el-row>
					<el-col :span="12">
						<el-form-item label="用户" label-width="120px" prop="storeName">
							<span>{{publishForm.userName}}</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						 <el-form-item label="商品" label-width="120px" prop="storeNo">
						 <span>{{publishForm.goodsName}}</span>
						 </el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="订单号" label-width="120px" prop="linkName">
							<span>{{publishForm.orderSn}}</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">

						<el-form-item label="数量金额" label-width="120px" prop="storePhone">
							<span>{{publishForm.num}}个 {{publishForm.amount}}元 </span>
						</el-form-item>
					</el-col>
				</el-row>
				
				<el-row>
					<el-col :span="12">
						<el-form-item label="收货人" label-width="120px" prop="linkName">
							<span>{{publishForm.consignee}}</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">
				
						<el-form-item label="手机号" label-width="120px" prop="storePhone">
							<span>{{publishForm.mobile}}</span>
						</el-form-item>
					</el-col>
				</el-row>
				
				<el-row>
					<el-col :span="12">
						<el-form-item label="自提点" label-width="120px" prop="linkName">
							<span>{{publishForm.takeStoreName}}</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">
				
						<el-form-item label="提货时间" label-width="120px" prop="storePhone">
							<span>{{publishForm.takeTime}}</span>
						</el-form-item>
					</el-col>
				</el-row>
				<!-- 
				<el-row>
					<el-col :span="12">
						<el-form-item label="评价内容" label-width="120px" prop="linkName">
							<span>{{publishForm.appraiseContent}}</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">
				
						<el-form-item label="评价时间" label-width="120px" prop="storePhone">
							<span>{{publishForm.appraiseTime}}</span>
						</el-form-item>
					</el-col>
				</el-row> -->

<el-row v-if="publishForm.refundStatus!=null">
					<el-col :span="12">
						<el-form-item label="退款状态" label-width="120px" prop="linkName">
							<span v-if="publishForm.refundStatus==1">申请中</span>
							<span v-if="publishForm.refundStatus==2">已退款</span>
							<span v-if="publishForm.refundStatus==3">已拒绝</span>
						</el-form-item>
					</el-col>
					<el-col :span="12">
				
						<el-form-item label="申请原因" label-width="120px" prop="storePhone">
							<span>{{publishForm.refundRemark}}</span>
						</el-form-item>
					</el-col>
				</el-row>
				 
 


			</el-form>

			<div slot="footer" class="dialog-footer"> 
			 
				<el-button v-if="publishForm.orderStatus==2" type="primary" @click="fahuo">确认发货</el-button>
					<el-button v-if="publishForm.refundStatus==1" type="primary" @click="refund">同意退款</el-button> 
			
			</div>
		</el-dialog>



	</el-container>

</template>

<script>
	export default {
		name: 'planManage',
		data() {
			return {
				infoTitle: '查看订单详情',
				//查询公告的参数
				queryInfo: {
					'name': '',
					'orderSn':'',
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
				// 被选中的表格行
				selectedInTable: [],
				categoryList:[],
				// 发布对话框
				publishTableVisible: false,
				// 发布的表单
				publishForm: {
 
					id: '',
					orderSn: '',
					 userName: '',
					orderStatus: '',
					consignee: '',
					mobile: '',
					message:'',
					amount: '',
					takeTime: '',
					takeStoreName: '',
					
					num:0,
					refundRemark: '', 
				},
				// 当前更新的信息
				currentUpdateStatus: ''
			}
		},
		created() {
			this.getDataInfo()
		},
		methods: {
			//获取信息
			getDataInfo() {
				this.$http.get(this.API.getAllOrder, {
					params: this.queryInfo
				}).then((resp) => {
					if (resp.data.code === 200) {
						this.dataInfo = resp.data.data.list;
						this.total = resp.data.data.total;
						this.loading = false;
					} else {
						this.$notify({
							title: '获取信息失败',
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
			} ,
			// 发布的表单信息重置
			resetPublishForm() {
				//清空表格数据
				this.$refs['publishForm'].resetFields()
			} ,
			// 显示详情的对话框
			showDetailDialog(row) {
				this.infoTitle = '订单详情'
				this.publishForm.id = row.id;
				this.publishForm.refundRemark = row.refundRemark;
				this.publishForm.refundStatus = row.refundStatus;
				this.publishForm.takeStoreName = row.takeStoreName;
			    this.publishForm.takeTime = row.takeTime;
				this.publishForm.appraiseContent = row.appraiseContent;
				this.publishForm.payTime = row.payTime;
				this.publishForm.amount = row.amount;
			   this.publishForm.mobile = row.mobile;
			    this.publishForm.consignee = row.consignee;
			   this.publishForm.orderStatus = row.orderStatus;
			   this.publishForm.orderSn = row.orderSn;
			   this.publishForm.userName = row.userName;
               this.publishForm.goodsName = row.goodsName; 
			   this.publishForm.num=row.num;
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
			fahuo() {
                var id=this.publishForm.id;
				this.$http.get(this.API.updateOrdStatus, {
					params: {
						'id': id,
						'status':3,
					}
				}).then((resp) => {
					if (resp.data.code === 200) {
						 this.publishForm.orderStatus=2;
						this.publishTableVisible = false;
						this.getDataInfo()
						this.$notify({
							title: '发货成功',
							type: 'success',
							duration: 2000
						})
					} else {
						this.$notify({
							title: '发货失败',
							type: 'error',
							duration: 2000
						})
					}
				})
			},
			refund() {
                var id=this.publishForm.id;
				this.$http.get(this.API.updateRefundStatus, {
					params: {
						'id': id,
						'status':2,
					}
				}).then((resp) => {
					if (resp.data.code === 200) {
						 this.publishForm.orderStatus=2;
						this.publishTableVisible = false;
						this.getDataInfo()
						this.$notify({
							title: '同意退款成功',
							type: 'success',
							duration: 2000
						})
					} else {
						this.$notify({
							title: '同意退款失败',
							type: 'error',
							duration: 2000
						})
					}
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
