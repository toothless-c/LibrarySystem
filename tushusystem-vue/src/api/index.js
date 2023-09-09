import http from '@/utils/http'
//获取天气
export const getweatherApi = async (aprm) =>{
  return await  http.get("https://www.yiketianqi.com/free/day?appid=83797855&appsecret=f4XMm4RI&unescape=1")
}
