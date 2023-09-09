import http from '@/utils/http'
//获取所有
export const getBookList = async (parm) =>{
  return await http.get("/api/books/list",parm)
}
//新增
export  const addBook = async (parm) =>{
  return await http.post("/api/books",parm)
}
//编辑
export const editBook = async (parm) =>{
  return await http.put("/api/books",parm)
}
// 删除
export  const  deleteBook = async (parm) =>{
  return await http.delete("/api/books",parm)
}
//查询分类
export  const getTreeById = async (parm) =>{
  return await  http.get("/api/books",parm)
}
//根据ID查询分类
export const queryByBookId = async (parm) =>{
  return await  http.post("/api/books/s",parm)
}
//
export const querylist = async ()=>{
  return await http.get("/api/books/list")
}
export const querylistss = async (parm)=>{
  return await http.get("/api/books/query",parm)
}
