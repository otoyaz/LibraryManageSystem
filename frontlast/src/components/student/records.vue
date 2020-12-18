<template>
<div>
  <div class="breadcrumb">
    <el-breadcrumb separator-class="el-icon-d-arrow-right">
      <el-breadcrumb-item :to="{ path: '/library/slider' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的图书馆</el-breadcrumb-item>
      <el-breadcrumb-item>我的借阅</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
      <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
        <el-tab-pane name="first">
          <span slot="label"><i class="el-icon-remove-outline"></i>&nbsp;书籍归还</span>
            <div class="BookRecord">
                <el-row class="tabletop" style="text-align:center;">
                  <el-col>
                      <el-table :data="records" style="width:100%;text-align:center;"
                      height="440" :header-cell-style="{'text-align':'center','background-color':'#f0f9eb'}">
                        <el-table-column prop="bookName" label="书名" class="bookname"></el-table-column>
                        <el-table-column prop="readerName" label="用户名称"></el-table-column>
                        <el-table-column prop="lend_date" label="借书日期" :formatter="dateFormat">
                        </el-table-column>
                        <el-table-column prop="back_date" label="应还书日期" :formatter="dateFormat"></el-table-column>
                        <el-table-column label="操作" width="160">
                          <template slot-scope="scope">
						                <el-button v-model="types" round type="success" plain icon="el-icon-time"
						                @click="handleReturn(scope.$index, scope.row)" size="small">还书</el-button>
						              </template>
                        </el-table-column>
                      </el-table>
                  </el-col>
                </el-row>
                </div>
              </el-tab-pane>
              <el-tab-pane name="second" type="border-card">
                <span slot="label"><i class="el-icon-circle-plus"></i>&nbsp;书籍借阅</span>
                <el-row>
                  <el-col :span="6" class="search">
                      <el-input v-model="keywords"
                       placeholder="请输入书名进行查询"
                       prefix-icon="el-icon-search"
                       @change="findQuery"></el-input>
                  </el-col>
                  <el-col :span="18"></el-col>
                </el-row>
                <el-row class="tabletop" style="text-align:center;">
                  <el-col>
                      <el-table :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width:100%;text-align:center;"
                      height="600"  :header-cell-style="rowClass">
                      <el-table-column label="编号" prop="bookId" width="60"></el-table-column>
                      <el-table-column label="书名" class="bookname" prop="name"></el-table-column>
                      <el-table-column label="作者"  prop="author"></el-table-column>
                      <el-table-column label="出版社"  prop="publish" ></el-table-column>
                      <el-table-column label="出版日期" :formatter="dateFormat2"  prop="pubdate"></el-table-column>
                      <el-table-column label="介绍" :formatter="contentFormat2" width="120" prop="introduction"></el-table-column>
                      <el-table-column label="价格" width="60" prop="price"></el-table-column>
                      <el-table-column label="分类Id" width="70" prop="class_id"></el-table-column>
                      <el-table-column label="数量" width="60" prop="number"></el-table-column>           
                        <el-table-column label="操作">
                            <template slot-scope="scope">
						                  <el-button type="warning" icon="el-icon-tickets" plain round @click="handleEdit(scope.$index, scope.row)">借书</el-button>	
						               </template>
                        </el-table-column>
                      </el-table>
                      <div class="block">
                        <el-pagination :total="books.length" :current-page="currentPage" :page-size="pageSize"
                        @current-change="handleCurrentChange" layout="total,prev,pager,next,jumper"></el-pagination>
                      </div>
                  </el-col>
                </el-row>
                <el-dialog :title="title" :visible.sync="dialogFormVisible" center width="40%">
                  <el-form :model="form">
                  <el-form-item label="用户账号">
                     <el-input v-model="form.username" readonly style="width:400px;" class="row"></el-input>
                   </el-form-item>
                   <el-form-item label="书的编号">
                     <el-input v-model="form.bookId" readonly style="width:400px;" class="row"></el-input>
                   </el-form-item>
                      <el-form-item label="用户id">
                     <el-input v-model="form.readerId" readonly style="width:400px;" class="row"></el-input>
                   </el-form-item>
					        </el-form>
					        <div slot="footer" class="dialog-footer">
					          <el-button @click="dialogFormVisible = false">取 消</el-button>
					          <el-button type="primary" @click="handleSubmit()">确 定</el-button>
					        </div>
					       </el-dialog>
      </el-tab-pane>
      </el-tabs>
  </div>
</template>
<script>
import {mapState,mapGetters,mapActions} from 'vuex'
import moment from 'moment'
import qs from "Qs"

export default {
  name:'BookRecords',
  computed: {
    ...mapGetters(['records','books'])
  },
  data() {
    return {
      
      form: {
        username: sessionStorage.getItem('username'),
        readerId: sessionStorage.getItem("userId"),
        bookId: ""
      },
      activeName:'first',
      keywords:'',
      keywordsreturn:'',
      dialogFormVisiblereturn:false,
      dialogFormVisible:false,
      formreturn:{},
      title:'',
      types:'',
      pageSize:4,
      currentPage:1,
    };
  },
  created() {
    // var params = {username:sessionStorage.getItem('username')};
    // console.log(params);
    // this.findStudentRecord(params);
    //加载第一个选项卡的数据
    var params = {readerId: sessionStorage.getItem("userId")};
    console.log(params);
    this.findStudentRecord(params);
    this.findAllBook();
  },
  methods: {
        rowClass({ row, rowIndex}) {
            return 'text-align:center;background-color:oldlace;color:grey' 
        },
        dateFormat(row,column) {
         var date = row[column.property];
         if (date == undefined) {
          return "";
        }
         return moment (date).format("YYYY-MM-DD");
        },
        dateFormat2(row,column) {
         var date = row[column.property];
         if (date == undefined) {
          return "";
        }
         return moment (date).format("YYYY-MM");
        },
        contentFormat2(row,column) {
            var content = row[column.property];
             if (content == undefined) {
                 return "";
             }
             return content.slice(1,8) + "...";
         },
        
        handleCurrentChange: function(currentPage){
         this.currentPage = currentPage;
        },
        // 模糊查询
        findQuery(){
        var params = {searchWord:this.keywords};
        console.log(params);
		    this.findQueryBook(params);
		    },
        // 借书
        handleEdit(index,row){
          console.log(row);
          this.title="增加借书信息";
          this.form.bookId=row.bookId;
          this.dialogFormVisible=true;
        },
        handleSubmit(){
          this.dialogFormVisible=false;
          var vm=this;
          if(this.title=="增加借书信息"){
            this.saveborrowbook1(this.form).then(function(data){
              console.log("借书",data);
              vm.$message({
                type:'success',
                message:'借书成功!'
              });
            }).catch(function(error){
              console.log("借书失败",error);
               vm.$message({
                type:'success',
                message:'借书成功!'
              });
            });
            // let id=this.form.Book_num;
            // console.log("id",id);
            // this.updateBookNum(id).then(function(data){
            //   console.log("减",data);              
            // }).catch(function(error){
            //   console.log("借书数量修改失败",error);
            // });
          }
        },
        // 还书
        handleReturn(index,row){
          this.$confirm('确定还书?','提示',{
              confirmButtonText:'确定',
              cancelButtonText:'取消',
              type:'warning'
            }).then(()=> {
              let readerId=sessionStorage.getItem('userId');
              let bookId=row.bookId; 
              let obj1 = {readerId: readerId, bookId: bookId};
              this.$http({
              method:"put",
              url:"http://localhost:8081/returnBook",
              data:qs.stringify(obj1),            //参数
              headers:{
                 Authorization:sessionStorage.getItem("token")     //请求头携带的token
              }
              }).then((data)=>{
                this.$message({ 
                  type:'success',
                  message:'还书成功!'
                });
	        		this.findStudentRecord({readerId: sessionStorage.getItem("userId")});
                // this.updateBookNumadd(id).then(function(data){
                //   console.log("加",data);              
                // }).catch(function(error){
                //   console.log("还书数量修改失败",error);
                //   });
                //   var params = {username:sessionStorage.getItem('username')};
                //   console.log(params);
	        		  //   this.findStudentRecord(params);               
                }).catch((error)=> {
                  this.$message({
                    type:'error',
                    message:'还书失败!'
                  });
                });
                })
            },
        handleClick(tab, event) {
	        	if(this.activeName=="first"){
              var params = {readerId: sessionStorage.getItem("userId")};
              console.log(params);
	        		this.findStudentRecord(params);
	        	}else if(this.activeName=="second"){
	        		this.findAllBook();
	        	}else{

	        	}},
        ...mapActions(['findAllBook','findQueryBook','findQueryBook1','findStudentRecord',
        'findQueryStudent','saveborrowbook1','updateBookNum','returnBook','updateBookNumadd']),
  }
  
   
 
}
</script>  
<style scoped>
.tabletop {
  padding: 10px 0 20px 0; 
}

.row {
    margin-left: 10px;
}
.search {
  margin: 4px 0;
}
.breadcrumb {
  margin-bottom: 20px;
  margin-top: -14px;
}
</style>

