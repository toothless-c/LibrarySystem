import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'

export function getToken() {
  return sessionStorage.getItem(TokenKey)
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey,token)
}

export function removeToken() {
  return sessionStorage.clear()
}
//存储用户id
export function setUserId(userId){
  return Cookies.set("userId",userId)
}
export function getUserId(userId){
  return Cookies.get("userId",userId)
}
export function setUserType(userType){
  return Cookies.set("userType",userType)
}
export function removeId(){
  return Cookies.remove("userId")
}
