import http from '@/utils/http'
//获取所有
export const getAll = async (parm)=>{
  return await  http.get('/api/bkuser/list',parm)
}
//添加
export const addBkUser = async (parm)=>{
  return await http.post('/api/bkuser',parm)
}
//编辑
export const editBkUser = async (parm)=>{
  return await http.put('/api/bkuser',parm)
}
//删除
export const deletebkuser = async (parm)=>{
  return await http.delete('/api/bkuser',parm)
}
//重置密码
export const editpassword = async (id)=>{
  return await http.post('/api/bkuser/password/'+ id)
}
//修改状态
export const  editcheckstatus = async (id,checkStatus) =>{
  return await  http.post('/api/bkuser/editsheckstatus/'+id+"/"+checkStatus)
}
