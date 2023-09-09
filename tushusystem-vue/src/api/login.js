import http from '@/utils/http'
export const login = async (parm) =>{
  return await  http.post("/api/login",parm)
}
