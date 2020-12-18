<template>
  <div class="section">
  <div class="ms-login">
    <el-form 
      :model="RegisterForm" 
      label-width="52px"
      status-icon
      ref="RegisterForm" 
      :rules="rule"
      class="register-form">
      <h3>注册新用户</h3>
      <el-form-item prop="name" label="姓名">
        <el-input 
          type="text" oninput="if(value.length>20)value=value.slice(0,20)"
          v-model="RegisterForm.name" 
          placeholder="请输入姓名">
        </el-input>
      </el-form-item>
      <el-form-item prop="accountName" label="账户">
        <el-input oninput="if(value.length>30)value=value.slice(0,30)"
          v-model="RegisterForm.accountName" 
          placeholder="请输入账户名" >
        </el-input>
      </el-form-item>
       <!-- <el-form-item prop="Student_name" label="昵称">
        <el-input 
          type="text" 
          v-model="RegisterForm.Student_name" 
          placeholder="请输入名字">
        </el-input>
      </el-form-item> -->
      <el-form-item prop="birth" label="出生">
          <el-date-picker
            v-model="RegisterForm.birth"
            type="date"
            placeholder="请选择出生日期">
          </el-date-picker>
      </el-form-item>
      
      <el-form-item prop="Sex" label="性别">
        <el-radio-group v-model="RegisterForm.Sex" size="medium">
          <el-radio border label="男"></el-radio>
          <el-radio border label="女"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item prop="phone" label="电话">
        <el-input 
          type="text" oninput="if(value.length>11)value=value.slice(0,11)"
          v-model="RegisterForm.phone" 
          placeholder="请输入电话" >
        </el-input>
      </el-form-item>
      <el-form-item prop="address" label="地址">
        <el-input 
          type="text" 
          v-model="RegisterForm.address" 
          placeholder="请输入地址">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button 
          type="primary" 
          class="submitBtn"
          round
          
          @click="postData('RegisterForm')">
          注册
        </el-button>
        <el-button 
          type="danger"
          class="resetBtn" 
          round
          @click.native.prevent="reset">
          重置
        </el-button>
        <hr>
        <p>返回&nbsp;<span class="to" @click="tologin">登录</span>&nbsp;页面</p>
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

//@click="submit('RegisterForm')">
export default {

  // ....

  data () {
    return {
      RegisterForm: {
        name: '',
        accountName: '',
        birth: '',
        address: '',
        
        Sex: '',
        phone: ''       
      },
      rule: {
        name: [
          {
            required: true,
            max: 20,
             min: 1,
            message: '请输入名字',
            trigger: 'blur'
          }
        ],
        accountName: [
          {
            max:30,
            min:1,
            required: true,
            message: '请输入账户名！',
            trigger: 'blur'
          }
        ],
        birth: [
          { 
            required: true, 
            message: '请输入出生日期',
            trigger: 'blur' 
          }
        ],
        address: [
          { 
            required: true, 
            message: '请输入地址',
            trigger: 'blur' 
          }
        ],
        // Class_name: [
        //   { 
        //     required: true, 
        //     message: '请输入班级',
        //     trigger: 'blur' 
        //   }
        // ],
        Sex: [
          { 
            required: true, 
            message: '请选择性别',
            trigger: 'blur' 
          }
        ],
        phone: [
          { 
            max:11,
            min:11,
            required: true, 
            message: '请输入11位电话号码',
            trigger: 'blur' 
          },
           { validator: isNum, trigger: 'blur' }
        ],
        
      }
    }
  },

  methods: {
    reset () {
      this.$refs.RegisterForm.resetFields();
    },
    tologin () {
      this.$router.push('/');
    },
    postData(formName){

        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http({
        method:'post',
        url:'http://localhost:8081/saveReaderInfo',
        data:{
          name:this.RegisterForm.name,
          accountName:this.RegisterForm.accountName,
          sex:this.RegisterForm.Sex,
          birth:this.RegisterForm.birth,
          address:this.RegisterForm.address,
          phone:this.RegisterForm.phone

        }
      }).then(function(response){
        console.log(response);
      }).catch(function(error){
        console.log(error);
      });
      alert("初始化密码为：123456");
      this.$router.push('/');
                  this.$message({
                    type:'success',
                    message:'注册成功，返回登录页面'
                    });
          } else {
            console.log('请重新输入电话号码');
            this.$message({
                    type:'error',
                    message:'请重新输入电话号码'
                    });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    


      
      

    
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post('http://localhost:8081/saveReaderInfo').then((response) => {
             if (response.data == -1) {
               this.$message({
                 type:'error',
                 message:'学号不正确'
                 });
                console.log('学号不正确');
                } else {
                  this.$router.push('/');
                  this.$message({
                    type:'success',
                    message:'注册成功，返回登录页面'
                    });
                  }
            }).catch((error) => {
              console.log(error);
            });
            } else {
              console.log('error submit!!');
              return false;
              }
            });
      }
    }

  
}
</script>

<style scoped>
.section {
  position: absolute;
  width: 100%;
  height: 100%;
  background:url('../../../static/images/13.jpg');
  background-size:cover;
}

.register-form {
  margin: 0 auto;
  width: 410px;
  background: #fff;
  box-shadow: 0 0 10px #B4BCCC;
  padding: 6px 50px 0 30px;
  border-radius: 25px; 
}
.submitBtn {
  margin-left: -20px;
  width: 50%;
}
.resetBtn {
  width: 50%;
}
.to {
  color: aqua;
  cursor: pointer;
}
.ms-login {
  margin-top: 30px;
}
h3 {
  text-align: center;
}
p {
  font-size: 15px;
  margin-top: -6px;
  margin-left: -20px;
}
hr {
  margin-left: -30px;
}
</style>

