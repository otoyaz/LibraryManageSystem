import axios from 'axios'
import Vue from 'vue'
import qs from "Qs"
Vue.prototype.$http = axios

export default{
	state:{
		students:[],
	},
	getters:{
		students:state=>state.students,
	},
	mutations:{
		alterStudents(state,data){
			state.students=data;
		},
	},
	actions:{
		//所有的
		findAllStudents(context){
			axios.get('http://localhost:8081/getAllreaderInfo',
                {
                    headers: {
                    Authorization:sessionStorage.getItem("token")
                }}).then((response)=> {
                    // console.log(response);
					context.commit('alterStudents',response.data);

				}).catch(function(error) {
					console.log(error);
				})
        },
        // 保存学生信息
		saveStudent(context,form){
			return new Promise(function(resolve,reject){
				// axios.post('http://localhost:3000/student/save',form).then((result)=>{
				// 	context.dispatch('findAllStudents');
				// 	resolve(result);
				// }).catch(function(error){
				// 	reject(error);
				// 	console.log("error",error);
				// });
			})
        },
        // 更新学生信息
		updateStudent(context,form){
			return new Promise(function(resolve,reject){
				axios.put('http://localhost:8081/updateReaderInfo',form,
				{
                    headers: {
                    Authorization:sessionStorage.getItem("token")
                }}).then((result)=>{
					context.dispatch('findAllStudents');
					resolve(result);
					console.log("123",result);
				}).catch(function(error){
					reject(error);
				})
			})
        },
        // 删除学生信息
		batchDeleteStudent(context,ids){
			let params = {readerId: ids};
			return new Promise((resolve,reject)=>{
				// axios.delete('http://localhost:8081/deleteReaderInfo',{headers: {
				// 	Authorization:sessionStorage.getItem("token")
				// 	},params}).then((data)=>{
				// 	context.dispatch('findAllStudents');
				// 	resolve(data);
				// }).catch((error)=>{
				// 	reject(error);
				// });
				console.log(params);
				axios.delete('http://localhost:8081/deleteReaderInfo',
                {
					params: params,
                    headers: {
                    Authorization:sessionStorage.getItem("token")
                }}).then((response)=> {
                    context.dispatch('findAllStudents');
					resolve(response);

				}).catch(function(error) {
					reject(error);
				})

			})
        },
            //查询借阅排行榜
            findTop(context) {
              axios.get('http://localhost:8081/getRank',{
				headers: {
				Authorization:sessionStorage.getItem("token")
			}}).then(({data})=>{
                  context.commit('alterStudents',data)
                  }).catch((error)=>{
                  console.log(error)
              })
          },
		findQueryStudent(context,params){
			console.log(params);
			if(params.name != "lend") {
				axios.get('http://localhost:8081/getReaderInfo',{headers: {
				Authorization:sessionStorage.getItem("token")
				},params}).then(({data})=>{
					// console.log("ddddd");
					// let list = new Array();
					// list.push(data);
					console.log(data);
					console.log(data);
					console.log(11)
					if(data.length == 0 || data[0] == null) {
						data = [];
					}
					context.commit('alterStudents',data);
				}).catch((error)=>{
					console.log(error);
				});
			} else {
				axios.get('http://localhost:8081/getMyLendList',{headers: {
				Authorization:sessionStorage.getItem("token")
				},params}).then(({data})=>{
					console.log(22)
					if(data.length == 0 || data[0] == null) {
						data = [];
					}
					context.commit('alterRecords',data)
				}).catch((error)=>{
					console.log(error);
				});
			}
			
		},
	}
}
