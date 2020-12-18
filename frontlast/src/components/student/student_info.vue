<template>
	<div id="usercenter">
        <h3>修改信息</h3>
		<div class="userContent">
			<el-form ref="form" :model="form" label-width="60px" :rules="rule" status-icon>
				<el-form-item label="账号" prop="readerId">
					<el-input v-model="form.readerId" disabled=""></el-input>
				</el-form-item>
				<el-form-item label="名称" prop="name">
					<el-input v-model="form.name" oninput="if(value.length>20)value=value.slice(0,20)"></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="sex"> 
					<el-select class="select-sex" v-model="form.sex" placeholder="请选择性别">
						<el-option label="男" value="男"></el-option>
						<el-option label="女" value="女"></el-option>
					</el-select>
				</el-form-item>
                <el-form-item label="手机" prop="phone">
					<el-input v-model="form.phone" oninput="if(value.length>11)value=value.slice(0,11)"></el-input>
				</el-form-item>
                <el-form-item label="地址" prop="address">
					<el-input v-model="form.address"></el-input>
				</el-form-item>
                <el-form-item label="生日" prop="birth">
					 <el-date-picker
                        v-model="form.birth"
                        type="date"
                        placeholder="选择日期">
                    </el-date-picker>
				</el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('form')">提交</el-button>
                    <el-button @click.native.prevent="reset">重置</el-button>
                </el-form-item>
			</el-form>
		</div>
	</div>
</template>

<script>
const isNum = (rule, value, callback) => {
      const age= /^[0-9]*$/
      if (!age.test(value)) {

        
        this.flag=true,
        callback( new Error('电话只能为数字'))

      }else{
        
        callback()
      }
    }
import {mapState,mapGetters,mapActions} from 'vuex'
	export default {
		data() {
            
            return {
                // ff:{
                //      readerId:sessionStorage.getItem("userId"),
                //     name: '',
                //     sex: '' ,
                //     phone: '' ,
                //     address: '',
                //     birth: '',
                //     accountName: ""  
                // },
				form: {
                    readerId:'',
                    name: '',
                    sex: '' ,
                    phone: '' ,
                    address: '',
                    birth: '',
                    accountName: ""  
                },
                rule: {
                    readerId: [
                    {
                         required: true, 
                         message: '请输入用户账号',
                         trigger: 'blur' 
                    }],
                    name: [
                    { 
                        required: true, 
                        message: '请输入名字',
                        trigger: 'blur' 
                    }],
                    sex: [
                    { 
                        required: true, 
                        message: '请输入性别',
                        trigger: 'blur' 
                    }],
                    phone: [
                    { 
                        required: true, 
                        message: '请选择手机号码',
                        trigger: 'blur' 
                    }
                    ,
           { validator: isNum, trigger: 'blur' }],
                    address: [
                    { 
                        required: true, 
                        message: '请输入地址',
                        trigger: 'blur' 
                    }],
                    birth: [
                    { 
                        required: true, 
                        message: '请输入出生日期',
                        trigger: 'blur' 
                    }],
                    accountName: [
                    { 
                        required: true, 
                        message: '请输入账户名字',
                        trigger: 'blur' 
                    }]
                    }
                    }
        },
        
        mounted() {
            this.getUserData();			
        },   
        methods: {
            reset() {         
                this.$refs.form.resetFields()
            },
            getUserData() {
                var params = {readerId:sessionStorage.getItem("userId")};	
                this.$http.get('http://localhost:8081/getReaderInfo',
                {
                    headers: {
                    Authorization:sessionStorage.getItem("token")
                },params}).then((response)=> {
                    // console.log(response);
                    let result = response.data;
                    console.log(result);
                    this.form.readerId = result[0].readerId;
                    this.form.name = result[0].name;
                    this.form.sex = result[0].sex;
                    this.form.phone = result[0].phone;
                    this.form.address = result[0].address;
                    this.form.birth = result[0].birth;
                    this.form.accountName = result[0].accountName;
					// this.form.Email = result.Email;
				}).then(function(error) {
					console.log(error);
				})
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.put('http://localhost:8081/updateReaderInfo',this.form,{
                    headers: {
                    Authorization:sessionStorage.getItem("token")
                }}).then((data) => {
                            this.$message({
                            type:'success',
                            message:'修改成功'
                            });
                        }).catch(function(error){
                            this.$message({
                            type:'error',
                            message:'修改失败!'
                            });
                        });
                        } else {
                        console.log('error submit!!');
                        }
                    });
    }
        }}	
</script>

<style>
.userContent {
	width: 400px;
	margin: 0 auto;
}
.select-sex {
	width: 340px;
}
#usercenter {
    margin-top: -40px;
}
h3 {
    text-align: center;
}
</style>