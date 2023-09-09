import http from '@/utils/http'
export const getClassifyList = async (parm) =>{
  return await  http.get("/api/bkclassify/list",parm)
}
//新增
export  const addClassify = async (parm) =>{
  return await http.post("/api/bkclassify",parm)
}
//编辑
export const editClassify = async (parm) =>{
  return await http.put("/api/bkclassify",parm)
}
// 删除
export  const  deleteClassify = async (parm) =>{
  return await http.delete("/api/bkclassify",parm)
}
//获取tree
export const getAll = async async => {
  return await http.get("/api/bkclassify/tree")
}
