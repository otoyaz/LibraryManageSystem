import axios from 'axios'
import Vue from 'Vue'
Vue.prototype.$http = axios
// 处理书籍的数据
export default{
  state: {
    books: []
  },
  getters: {
    books: function (state) {
      return state.books
    }
  },
  mutations: {
    alterBook (state, data) {
      state.books = data
    }
  },
  actions: {
    // 查找所有书籍
    findAllBook (context) {
      axios({
        method: "get",
        url: "http://localhost:8081/getAllBooks",
        headers: {
          Authorization:sessionStorage.getItem("token")
        }
    }).then(function(res) {
        context.commit('alterBook', res.data)
        console.log(res);
    });
    },
    // 模糊查询(通过书名或作者)
    findQueryBook (context,params) {
      axios.get('http://localhost:8081/selectBookByName',{params}).then(({data}) => {
        context.commit('alterBook',data)
        console.log(data)
      }).catch((error)=>{
        console.log(error)
      })
    },
    // 删除书籍
    batchDeleteBook (context,bookId) {
      let params = {bookId:bookId};
      return new Promise ((resolve,reject)=>{
        axios.delete('http://localhost:8081/deleteBook',{headers: {
          Authorization:sessionStorage.getItem("token")
        },params}).then((data)=>{
          context.dispatch('findAllBook')
          resolve(data)
        }).catch((error)=>{
          reject(error)
        })
      })
    },
    // 修改书籍
    updateBook(context,form){
			return new Promise(function(resolve,reject){
				axios.put('http://localhost:8081/updateBook',form).then((data)=>{
					context.dispatch('findAllBook')
					resolve(data)
				}).catch(function(error){
					reject(error)
				})
			})
    },
    // 保存书籍信息
    saveBook(context,form){
      return new Promise(function(resolve,reject){
        axios.post('http://localhost:8081/saveBook',form,
        {
          headers: {
            Authorization:sessionStorage.getItem("token")
          }
        }).then((data)=>{
          context.dispatch('findAllBook')
          resolve(data)
        }).catch((error)=>{
          reject(error)
        })
      })
    },
    // 修改书籍当前数量减一
		updateBookNum(context,id){
			return new Promise(function(resolve,reject){
				// axios.post('http://localhost:3000/book/updatenum',{id:id}).then((data)=>{
				// 	context.dispatch('findAllBook');
				// 	resolve(data);
				// }).catch(function(error){
				// 	reject(error);
				// })
			})
		},
		// 修改书籍当前数量加一
		updateBookNumadd(context,id){
			return new Promise(function(resolve,reject){
				// axios.post('http://localhost:3000/book/updatenumadd',{id:id}).then((data)=>{
				// 	context.dispatch('findAllBook');
				// 	resolve(data);
				// }).catch(function(error){
				// 	reject(error);
				// })
			})
		}

  }

}
