import axios from 'axios'
import Vue from 'Vue'
Vue.prototype.$http = axios

export default {
    state: {
        types: []
    },
    getters: {
        types:state=>state.types
    },
    mutations: {
        alterTypes(state,data) { 
            state.types=data;
        }
    },
    actions: {
        findAllTypes(context) {
            // axios.get('http://localhost:3000/booktype/findAll').then(({data})=>{
            //     context.commit('alterTypes',data);
            //     console.log('类型'+data);
            // }).catch((error)=>{
            //     console.log(error);
            // });
            axios({
                method:"get",
                url:"http://localhost:8081/findAllType",
                headers:{
                   Authorization:sessionStorage.getItem("token")     //请求头携带的token
                }
                }).then((data)=>{
                    context.commit('alterTypes',data.data);
                }).catch((error) => {
                    console.log(error);
                })
        }
    }

}