<template>
  <div class="BookSelects">
      <el-row style="margin-top:-10px;">
        <el-col :span="6">
            <el-input v-model="keywords" @change="findQuery"
             placeholder="请输入书名或作者查询"
             prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="18">          
        </el-col>
      </el-row>
      <el-row class="tabletop" style="text-align: center;">
        <el-col>
            <el-table :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%; text-align: center;"
               height="600" :header-cell-style="rowClass">
              <el-table-column label="书名" class="bookname" prop="name"></el-table-column>
              <el-table-column label="作者"  prop="author"></el-table-column>
              <el-table-column label="出版社"  prop="publish" ></el-table-column>
              <el-table-column label="出版日期" :formatter="dateFormat"  prop="pubdate"></el-table-column>
              <el-table-column label="介绍" :formatter="contentFormat" width="120" prop="introduction"></el-table-column>
              <el-table-column label="价格" width="60" prop="price"></el-table-column>
              <el-table-column label="分类Id" width="70" prop="class_id"></el-table-column>
              <el-table-column label="数量" width="60" prop="number"></el-table-column>
            </el-table>
            <div class="block">
                <el-pagination :total="books.length" :current-page="currentPage" :page-size="pageSize"
                 @current-change="handleCurrentChange" 
                 layout="total,prev,pager,next,jumper"></el-pagination>
            </div>
        </el-col>
      </el-row>
  </div>
</template>
<script>
import {mapState,mapGetters,mapActions} from 'vuex'
import moment from 'moment'
export default {
    name:'BookSearch',
    computed: {
        ...mapGetters(['books'])       
    },
     data() {
          return{ 
				keywords:'',
                pageSize:4,
                currentPage:1,
			}
     },
    created () {
         this.findAllBook();
     },
     methods: {
        rowClass({ row, rowIndex}) {
            return 'text-align:center;background-color:#FAFAFA;' 
        },
         //日期显示问题
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
         handleCurrentChange: function(currentPage){
             this.currentPage = currentPage;
         },
         //模糊查询
         findQuery(){
             var params = {searchWord:this.keywords};
             console.log(params);
             this.findQueryBook(params);
         },

        
         ...mapActions(['findAllBook','findQueryBook'])
     }
     

}
</script>
<style scoped>
.tabletop {
    padding: 20px 0 40px 0;
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

