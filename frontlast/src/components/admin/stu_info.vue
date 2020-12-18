<template>
    <div class="StudentInfo">
        <el-row>
            <el-col :span="6">
                 <el-input
                    placeholder="请输入读者Id进行查询"
                    prefix-icon="el-icon-search"
                    v-model="keywords" @change="findQuery">
                </el-input>
            </el-col>
            <el-col :span="18">         
            </el-col>
        </el-row>
        <el-row class="tabletop" style="text-align: center;">
            <el-col>
                <el-table
                :data="students.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                style="width: 100%;text-align: center;"
                height="510"
                border stripe 
                :header-cell-style="{'text-align':'center','background-color':'#FAFAFA'}">
                 <el-table-column 
                  prop="readerId" 
                  label="账户Id">
                </el-table-column>
                <el-table-column 
                  prop="accountName"
                  label="账户">
                </el-table-column>
                <el-table-column 
                  prop="name"
                  label="姓名">
                </el-table-column>
                <el-table-column
                  prop="address"
                  label="地址">
                </el-table-column>
                <el-table-column
                  prop="sex"
                  label="性别" width="80">
                </el-table-column>
                <el-table-column 
                  prop="phone" width="200"
                  label="手机">
                </el-table-column>
                <el-table-column label="操作" width="184">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      plain icon="el-icon-edit"
                      @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                    <el-button
                      size="mini"
                      type="danger"
                      plain icon="el-icon-delete"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="block">
                <el-pagination :total="students.length" :current-page="currentPage" :page-size="pageSize"
                 @current-change="handleCurrentChange" 
                 layout="total,prev,pager,next,jumper"></el-pagination>
            </div>
            </el-col>
        </el-row>
         <el-dialog :title="title" :visible.sync="dialogFormVisible" center width="40%">
          <el-form :model="form" ref="form" :rules="rule">
            <el-form-item label="姓名" prop="name" class="row">
              <el-input v-model="form.name" style="width:400px;" oninput="if(value.length>20)value=value.slice(0,20)"></el-input>
            </el-form-item>
            <el-form-item label="账户" class="row" prop="accountName" >
              <el-input v-model="form.accountName" style="width:400px;" oninput="if(value.length>30)value=value.slice(0,30)"></el-input>
            </el-form-item>
            <el-form-item label="性别" class="row" prop="Sex">
              <!-- <el-input v-model="form.sex" style="width:400px;"></el-input> -->
                <el-radio-group v-model="form.Sex" size="medium">
                 <el-radio border label="男"></el-radio>
                 <el-radio border label="女"></el-radio>
                </el-radio-group>
            </el-form-item>

            



            <el-form-item label="出生" class="row" prop="birth">
              
                <el-date-picker
               
                v-model="form.birth"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
              
            </el-form-item>
            
            <el-form-item label="地址" class="row" prop="address">
              <el-input v-model="form.address" style="width:400px;"></el-input>
            </el-form-item>
            <el-form-item label="手机" class="row" prop="phone" >
              <el-input v-model="form.phone" style="width:400px;" oninput="if(value.length>11)value=value.slice(0,11)"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleSubmit('form')">确 定</el-button>
          </div>
          
        </el-dialog>
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
          name:'studentInfo',
          computed: {
            ...mapGetters(['students'])
          },
          data() {
            return {
              keywords:'',
              pageSize:8,
              currentPage:1,
              dialogFormVisible:false,
              dialogFormVisiblesave:false,
              form:{
                name:'',
                accountName:'',
                Sex:'',
                birth:'',
                address:'',
                phone:''
              },
              formsave:{},
              title:'',
              titlesave:'',
            
          
          rule:{
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
        ],Sex: [
          { 
            required: true, 
            message: '请选择性别',
            trigger: 'blur' 
          }
        ],
        phone: [
          { 
            max: 11,
            min: 11,
            required: true, 
            message: '请输入11位电话号码',
            trigger: 'blur' 
          },
           { validator: isNum, trigger: 'blur' }
        ]
          }
        
      
  
          }
          },
         
          methods: { 
             created() {
            this.findAllStudents();
          }, 
          // 分页
          handleCurrentChange: function(currentPage){
             this.currentPage = currentPage;
          },       
          // 模糊查询
          findQuery() {
            var params={readerId:this.keywords};
            if(this.keywords == "") {
              this.findAllStudents();
            } else {
              this.findQueryStudent(params);
            }
          },
          // 修改按钮
          handleEdit(index,row) {
            this.form=row;
            this.title="修改学生信息";
            this.dialogFormVisible=true;
          },
          // 删除按钮
          handleDelete(index,row) {
            this.$confirm('此操作将永久删除该条记录，是否继续?','提示',{
              confirmButtonText:'确定',
              cancelButtonText:'取消',
              type:'warning'
            }).then(()=>{
              let ids=row.readerId;
              this.batchDeleteStudent(ids).then((data)=>{
                this.$message({ 
                  type:'success',
                  message:'删除成功!'
                });
              }).catch((error)=> { 
                this.$message({
                  type:'false',
                  message:'删除失败!'
                });
              });
            }).catch(()=>{
              this.$message({
                type:'false',
                message:'已取消操作'
              });
            });
          },
          // 确定按钮
          handleSubmit(formName) 
            // console.log(this.form);
            // return;
          {
        this.$refs[formName].validate((valid) => {
          if (valid) {
           
           this.dialogFormVisible=false;
            var vm=this;
            if(this.title=="修改学生信息"){
              this.updateStudent(this.form).then(function(data){
                vm.$message({
                  type:'success',
                  message:'修改成功'
                });
              }).catch(function(error){
                vm.$message({
                  type:'error',
                  message:'修改失败!'
                });
              });
            }


          } else {
           this.$message({
                  type:'error',
                  message:'格式不对！'
                });
            
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
            
       
            
          //   this.dialogFormVisible=false;
          //   var vm=this;
          //   if(this.title=="修改学生信息"){
          //     this.updateStudent(this.form).then(function(data){
          //       vm.$message({
          //         type:'success',
          //         message:'修改成功'
          //       });
          //     }).catch(function(error){
          //       vm.$message({
          //         type:'error',
          //         message:'修改失败!'
          //       });
          //     });
          //   }
          // },
          ...mapActions(['findAllStudents','saveStudent','updateStudent','batchDeleteStudent','findQueryStudent'])

          }
          
          
        } 
</script>
<style>
.tabletop {
  padding: 20px 0;
 }
.row {
  margin-left: 18px;
}
</style>
        