import http from '@/utils/http'
//随机毒鸡汤
export const getOne = async () =>{
  return await  http.get("https://api.uomg.com/api/rand.qinghua?format=json")
}
