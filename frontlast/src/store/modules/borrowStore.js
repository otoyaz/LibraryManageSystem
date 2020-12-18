import axios from 'axios'
import Vue from 'Vue'
import qs from "Qs"
Vue.prototype.$http = axios

export default{
  state:{
      records:[]
  },
  getters:{
      records:state=>state.records
  },
  mutations:{
      alterRecords(state,data){
          state.records=data
      }
  },
  actions:{
        // 所有记录
        findAllRecords(context) {
            axios.get('http://localhost:8081/getLendList').then(({data})=>{
                context.commit('alterRecords',data)
                }).catch((error)=>{
                console.log(error)
            })
        },
        // 学生借阅记录
        findStudentRecord(context,params) {
            axios.get('http://localhost:8081/getMyLendList',{
                headers: 
                {
                Authorization:sessionStorage.getItem("token")}
                ,params}).then(({data})=>{
                console.log(data)
                context.commit('alterRecords',data)
            }).catch((error)=>{
                console.log(error)
            })
        },
        // 模糊查询(书名)
        findQueryBook1(context,params) {
            // axios.get('http://localhost:3000/bookstudent/queryBook',{params}).then(({data})=>{
            //     context.commit('alterRecords',data)
            // }).catch((error)=>{
            //     console.log(error)
            // })
        },
        // 模糊查询(学生)
        findQueryStudent(context,params){
            axios.get('http://localhost:8081/getReaderInfo',{headers: {
				Authorization:sessionStorage.getItem("token")
			},params}).then(({data})=>{
                let list = new Array();
                list.push(data);
                context.commit('alterRecords',list)
            }).catch((error)=>{
                console.log(error)
            })
        },
        // 添加借阅信息
        saveborrowbook1(context,form,params){
            console.log(params);
            return new Promise(function(resolve,reject){
                axios.post('http://localhost:8081/lendBook',qs.stringify(form)).then((data)=>{
                resolve(data);
                }).catch(function(error){
                    reject(error)
                    console.log("error",error)
                })
            })
        },
        // 还书，管理员修改表中信息
        updateborrowbook(context,form){
            return new Promise(function(resolve,reject){
                // axios.post('http://localhost:3000/bookstudent/update',form).then((data)=>{
                //     resolve(data);
                // }).catch(function(error){
                //     reject(error)
                //     console.log("error",error)
                // })
            })
        },
        // 学生归还图书
        returnBook(context,id){
            return new Promise((resolve,reject)=>{
                // axios.post('http://localhost:3000/bookstudent/returnbook',{id:id}).then((data)=>{
                //     context.dispatch('findStudentRecord');
                //     resolve(data);
                // }).catch((error)=>{
                //     reject(error);
                // });
            })
        },
        //查询借阅排行榜
        findTop(context) {
            axios.get('http://localhost:8081/getRank',{headers: {
            Authorization:sessionStorage.getItem("token")
        }})
                .then(
                    response => {
                        context = response.data
                    }
                ).catch((error) => {
                    console.log(error)
                })
        },
        // 管理员删除记录
        deleteStudentRecord(context,id){
            return new Promise((resolve,reject)=> {
                // axios.post('http://localhost:3000/bookstudent/deleterecord',{id:id}).then((data)=> {
                //     context.dispatch('findAllRecords');
                //     resolve(data);
                // }).catch((error)=> {
                //     reject(error);
                // });
            });
        }
}
}
