<template>
  <div class="BookSelects">
      <el-row style="margin-top:-10px;">
          <hr><hr/>
        <el-col :span="6">
            <el-input v-model="keywords" @change="findQuery"
             placeholder="请输入书名或作者查询"
             prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="18">
            <el-button class="addbook" type="primary" icon="el-icon-edit-outline" plain @click="handleSingleSave">图书录入</el-button>
        </el-col>
      </el-row>
      <el-row class="tabletop" style="text-align: center;">
        <el-col>
            <el-table :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%; text-align: center;"
               height="600" stripe border :header-cell-style="{'text-align':'center','background-color':'#FAFAFA'}">
              <el-table-column label="书名" class="bookname" prop="name"></el-table-column>
              <el-table-column label="作者"  prop="author"></el-table-column>
              <el-table-column label="出版社"  prop="publish" ></el-table-column>
              <el-table-column label="出版日期" :formatter="dateFormat"  prop="pubdate"></el-table-column>
              <el-table-column label="介绍" :formatter="contentFormat" width="120" prop="introduction"></el-table-column>
              <el-table-column label="价格" width="60" prop="price"></el-table-column>
              <el-table-column label="分类" width="70" prop="class_name"></el-table-column>
              <el-table-column label="数量" width="60" prop="number"></el-table-column>
              <el-table-column label="操作" width="124">
                  <!--作用域插槽-->
                  <template slot-scope="scope">
                      <el-button type="primary" plain icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
                      <el-button type="danger" plain icon="el-icon-delete" circle  @click="handleDelete(scope.$index, scope.row)"></el-button>
                  </template>
              </el-table-column>
            </el-table>
            <div class="block">
                <el-pagination :total="books.length" :current-page="currentPage" :page-size="pageSize"
                 @current-change="handleCurrentChange" 
                 layout="total,prev,pager,next,jumper"></el-pagination>
            </div>
        </el-col>
      </el-row>
      <el-dialog :title="title" :visible.sync="dialogFormVisible" center width="40%">
          <el-form :model="form" ref="form" :rules="rule">
            <el-form-item label="书名" class="row" prop="name">
                <el-input v-model="form.name" style="width:400px;" placeholder="请输入书名"></el-input>
            </el-form-item>
             <el-form-item label="作者" class="row" prop="author">
                <el-input v-model="form.author" style="width:400px;" placeholder="请输入作者大名"></el-input>
            </el-form-item>
            <el-form-item label="出版社" class="row" >
                <el-input v-model="form.publish" style="width:400px;" placeholder="请输入出版社"></el-input>
            </el-form-item>
            <el-form-item label="Isbn" prop="isbn" class="row" >
                <el-input v-model="form.isbn" style="width:400px;" placeholder="请输入编号"></el-input>
            </el-form-item>
            <el-form-item label="介绍" class="row">
                <el-input v-model="form.introduction" style="width:400px;" placeholder="请输入简介"></el-input>
            </el-form-item>
            <el-form-item label="语言" class="row">
                <el-input v-model="form.language" style="width:400px;" placeholder="请输入版本语言"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price" class="row">
                <el-input type="text" v-model="form.price" style="width:400px;" placeholder="请输入价格"></el-input>
            </el-form-item>
             <el-form-item label="类型" class="row" prop="class_id">
                <el-select v-model="form.class_id" style="width:400px;" placeholder="请选择">
                    <el-option v-for="item in types"
                     :key="item.class_id"
                     :label="item.class_name"
                     :value="item.class_id"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="日期" style="margin-left:30px">
               <el-date-picker v-model="form.pubdate" type="date" placeholder="选择日期" style="display:block;margin:0 auto;width:400px;"></el-date-picker>
            </el-form-item>
            <el-form-item label="总数" prop="number" class="row" >
                <el-input type="text" v-model="form.number" style="width:400px;" placeholder="请输入总数"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible=false">取消</el-button>
              <el-button type="primary" @click="handleSubmit('form')">确定</el-button>
          </div>
      </el-dialog>
  </div>
</template>
<script>
const isNum = (rule, value, callback) => {
      const age= /^[0-9]*$/
      if (!age.test(value)) {

        
        this.flag=true,
        callback( new Error('编码为数字组成'))

      }else{
        
        callback()
      }
    }
import {mapState,mapGetters,mapActions} from 'vuex'
import moment from 'moment'
export default {
    name:'BookSearch',
    computed: {
        ...mapGetters(['books','types'])       
    },
     data() {
          return{
				keywords:'',
				dialogFormVisible:false,
				form:{},
                title:'',
                pageSize:4,
                currentPage:1,
                picture:'',
                rule:{
                    isbn:[{
                         required: true, 
                         message:'请输入数字编码',
                         trigger: 'blur' 
                         },
                         { validator: isNum, trigger: 'blur' }
                          ],
                          number:[{
                        
                         required: true, 
                         message: '请输入数量',
                         trigger: 'blur' 
                    },
                         { validator: isNum, trigger: 'blur' }],
                    price:[{
                        
                         required: true, 
                         message: '输入价格',
                         trigger: 'blur' 

                    },
                         { validator: isNum, trigger: 'blur' }],
                    name:[{
                        min:1,
                          required: true, 
                         message: '输入书名',
                         trigger: 'blur'
                    }],
                    author:[{
                        min:1,
                          required: true, 
                         message: '输入作者名',
                         trigger: 'blur'
                    }],
                    class_id:[{
                         min:1,
                          required: true, 
                         message: '选择类型',
                         trigger: 'blur'
                    }]
                    
                }
            }
            
     },
    created () {
         this.findAllBook();
         this.findAllTypes();
     },
     methods: {
         //日期显示问题
         dateFormat(row,column) {
             var date = row[column.property];
             if (date == undefined) {
                 return "";
             }
             return moment (date).format("YYYY-MM");
         },
         handleCurrentChange: function(currentPage){
             this.currentPage = currentPage;
         },
         dateFormat(row,column) {
             var date = row[column.property];
             if (date == undefined) {
                 return "";
             }
             return moment (date).format("YYYY-MM-DD");
         },
         contentFormat(row,column) {
            var content = row[column.property];
             if (content == undefined) {
                 return "";
             }
             return content.slice(1,8) + "...";
         },
         // 上传之前的钩子函数
         beforeAvatarUpload(file) {
             console.log(file.type)
             const isJPG = file.type === 'image/jpeg'||'image/png';
             const isLt2M = file.size / 500 / 500 < 1;
             console.log(file.size)
             if (!isJPG) {
                 this.$message({
                     type:'false',
                     message:'只支持jpg/png格式'
                 }); 
             }
             if (!isLt2M) {
                 this.$message({
                     type:'false',
                     message:'图片大小不能超过1024KB!'
                 }); 
             }
             return isJPG ;
         },
         // 上传成功
         uploadsuccess(fulAvatar){
             console.log('上传成功');
             this.findAllBook();
         },
         //模糊查询
         findQuery(){
             var params = {searchWord:this.keywords};
             console.log(params);
             this.findQueryBook(params);
         },
         //删除书籍
         handleDelete(index,row){
             this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
		       confirmButtonText: '确定',
		       cancelButtonText: '取消',
		       type: 'warning'
		 }).then(()=>{
             let ids= row.bookId;
             this.batchDeleteBook(ids).then((data)=>{
                 this.$message({
                     type:'success',
                     message:'删除成功!'
                 });
             }).catch((error)=>{
                 this.$message({
                     type:'false',
                     message:'删除失败!'
                 }); 
             });
         }).catch(()=>{
              this.$message({
                     type:'info',
                     message:'已取消操作'
                 });
         });
         },
         // 修改书籍
         handleEdit(index,row){
              console.log(row);
             this.form=row;
              this.form.class_id = "";
            //  for(var i in this.types) {
            //     if(this.types[i].class_id == row.class_id) {
            //         this.form.class_id = this.types[i].class_name;
            //         break;
            //     }
            //  }
             console.log(this.form);
             this.title="修改书籍信息";
             this.dialogFormVisible=true;
         },
         // 书籍录入
         handleSingleSave(){
             this.dialogFormVisible=true;
             this.title='录入书籍信息';
             this.form={};
         },
         // 用promise知道保存图书成功还是失败
         handleSubmit(formName)
                        {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            

                          this.dialogFormVisible=false;
             var vm=this;
             if(this.title=="录入书籍信息"){
                 console.log(this.form);
                 this.saveBook(this.form).then(function(data){
                     vm.$message({
                         type: 'success',
                         message: '添加成功!'
                     });
                 }).catch(function(error){
                     vm.$message({
                         type: 'error',
                         message: '添加失败!'
                     });
                 });
             }else{
                 console.log(this.form);
                 this.updateBook(this.form).then(function (data){
                     vm.$message({
                         type: 'success',
                         message: '修改成功!'
                     });
                   }).catch(function (error) {
                       vm.$message({
                           type: 'error',
                           message: '修改失败!'
                       });
                     });
             }


          } else {
            console.log('error submit!!');
            this.$message({
                           type: 'error',
                           message: '格式错误!'
                       });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

            //  this.dialogFormVisible=false;
            //  var vm=this;
            //  if(this.title=="录入书籍信息"){
            //      console.log(this.form);
            //      this.saveBook(this.form).then(function(data){
            //          vm.$message({
            //              type: 'success',
            //              message: '添加成功!'
            //          });
            //      }).catch(function(error){
            //          vm.$message({
            //              type: 'error',
            //              message: '添加失败!'
            //          });
            //      });
            //  }else{
            //      console.log(this.form);
            //      this.updateBook(this.form).then(function (data){
            //          vm.$message({
            //              type: 'success',
            //              message: '修改成功!'
            //          });
            //        }).catch(function (error) {
            //            vm.$message({
            //                type: 'error',
            //                message: '修改失败!'
            //            });
            //          });
            //  }
         
         ...mapActions(['findAllBook','findQueryBook','batchDeleteBook','updateBook','saveBook','findAllTypes'])
     }
     

} 
</script>
<style type="text/css" scoped>
.tabletop {
    padding: 10px 0;
}
.row {
    margin-left: 28px;
}
.pubcompany {
    margin-left: 15px;
}
.addbook {
    float: right;
}

</style>

