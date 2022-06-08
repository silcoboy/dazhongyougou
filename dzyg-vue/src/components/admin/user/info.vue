<template>
	
	<el-card class="box-card" style="width: 50%;margin-left: 20px;margin-top: 20px;">
	  <div slot="header" class="clearfix">
	    <span>个人基本信息</span>
	    <el-button style="float: right; padding: 3px 0" 
		 @click="saveInfo()"
		type="text">确认修改</el-button>
	  </div>
	  <div  class="text item">
		  
		    <el-form :model="infoForm"   ref="infoForm">
				  <el-form-item label="姓名" label-width="120px" prop="realName">
					  <el-input v-model="infoForm.realName"></el-input> 
				   </el-form-item>
				   
				   <el-form-item label="账号" label-width="120px" prop="username">
				   			 		  <el-input v-model="infoForm.username" disabled ></el-input> 
				    </el-form-item>
				   
				   <el-form-item label="手机号" label-width="120px" prop="mobile">
				       <el-input v-model="infoForm.mobile"></el-input>
				     </el-form-item> 
					 
					 <el-form-item label="性别" label-width="120px" prop="sex">
					        <el-select v-model="infoForm.sex" placeholder="请选择">
					          <el-option label="男" value="男"></el-option>
					          <el-option label="女" value="女"></el-option> 
					        </el-select>
					   </el-form-item>
					   
					   
					   <el-form-item v-if="infoForm.roleId==3" label="班级" label-width="120px" prop="banji">
					       <el-input v-model="infoForm.banji"></el-input>
					     </el-form-item> 
						 
						 <el-form-item v-if="infoForm.roleId==3" label="专业" label-width="120px" prop="major">
						     <el-input v-model="infoForm.major"></el-input>
						   </el-form-item> 
				   
			 </el-form>
			 
			 
			 
	    
	  </div>
	</el-card>
	
</template>

<script>
	export default {
	  name: 'info',
	  data () {
	    return {
			  infoForm: {
			    'realName': '',
			     'no':'',
			    'pageNo': 1,
			    'pageSize': 10
			  }, 
			}
		},
    created () {
      this.getInfo()
    },
    methods: { //获取用户信息
      getInfo () {
		   this.$http.get(this.API.getSelfInfo).then((resp) => {
		     if (resp.data.code === 200) { 
		       this.infoForm = resp.data.data;
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
      //保存信息
      saveInfo () {
       this.$http.post(this.API.addUser, this.infoForm).then((resp) => {
         if (resp.data.code === 200) { 
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
         this.addTableVisible = false
       })
      }
	}
}
</script>

<style>
 
</style>
