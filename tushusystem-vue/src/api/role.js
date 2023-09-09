import http from '@/utils/http'
//列表
export const getRoleList = async(parm) =>{
  return await http.get("/api/role/list",parm)
}
//新增
export const addRole = async(parm) =>{
  return await http.post("/api/role",parm)
}
//编辑
export const editRole = async(parm) =>{
  return await http.put("/api/role",parm)
}
//删除
export const deleteRole = async(parm) =>{
  return await http.delete("/api/role",parm)
}
//获取回显数据
export const getAssingShow = async(parm) =>{
  return await http.get("/api/role/getAssingShow",parm)
}
//保存权限
export const assignSave = async (parm)=>{
  return await  http.post("/api/role/assignSave",parm)
}
