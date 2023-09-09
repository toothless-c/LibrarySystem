import http from '@/utils/http'
//列表
export const getMenuList = async() =>{
  return await http.get("/api/menu/list",null)
}
//上级列表
export const getParentMenuList = async() =>{
  return await http.get("/api/menu/parent",null)
}
//新增
export const addMenu = async(parm) =>{
  return await http.post("/api/menu",parm)
}
//编辑
export const editMenu = async(parm) =>{
  return await http.put("/api/menu",parm)
}
//删除
export const deleteMenu = async(parm) =>{
  return await http.delete("/api/menu",parm)
}
