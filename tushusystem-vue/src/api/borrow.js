import http from '@/utils/http'
// 借书
export const borrow = async(parm) =>{
  return await http.post("/api/borrow",parm)
}
//查询用户信息
export  const QueryByUsernameOrStudentId = async (parm)=>{
  return await http.get("/api/bkuser/getByName",parm)
}
//还书列表
export const returnBorrowApi = async(parm) =>{
  return await http.get("/api/borrow/getBorrowList",parm)
}
//借阅列表
export const getLookBorrowList = async(parm) =>{
  return await http.get("/api/borrow/getLookBorrowList",parm)
}
//还书
export const returnBooks = async(parm) =>{
  return await http.post("/api/borrow/returnBook",parm)
}

//异常还书
export const exceptionBooksApi = async(parm) =>{
  return await http.post("/api/borrow/exceptionBooks",parm)
}
