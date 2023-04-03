// import axios from "axios";
//
// const api = axios.create({
//     baseURL: "http://localhost:8000",
// });
//
// // api.interceptors.response.use(null, error => {
// //     console.log(error.response.status + error.response.message() + " aboba");
// // })
//
// api.interceptors.request.use(config => {
//     alert("im here")
//     config.headers = {'Authorization': 'Bearer_'+ localStorage.token};
//     return config
// })