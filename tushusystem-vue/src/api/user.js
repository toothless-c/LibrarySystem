import request from '@/utils/request'
import http from '@/utils/http'
// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }

// export function getInfo(token) {
//   return request({
//     url: '/vue-admin-template/user/info',
//     method: 'get',
//     params: { token }
//   })
// }

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

//获取所有
export const getUserList = async (parm) => {
  return await http.get("/api/user/list", parm)
}
//添加
export const addUser = async (parm) =>{
  return await http.post("/api/user",parm)
}
//编辑
export  const editUser =  async(parm) =>{
  return await http.put("/api/user",parm)
}
//删除
export const deleteUser = async(parm) =>{
  return await http.delete("/api/user",parm)
}
//用户登录

// export const loginApi = async(parm) =>{
//   return await http.post("/api/login",parm)
// }
export const loginApi = async(parm) =>{
  return await http.post("/api/login",parm)
}
export const getInfos = async(parm) =>{
  return await http.get("/api/getinfo",parm)
}
//获取用户的菜单
export const getMenuList = async() =>{
  return await http.get("/api/getMenuList",null)
}
