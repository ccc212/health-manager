import Cookies from 'js-cookie'

const JwtKey = 'Admin-Jwt'
const TokenKey = 'Admin-Token'

export function getJwt() {
  return Cookies.get(JwtKey)
}

export function setJwt(authorization) {
  return Cookies.set(JwtKey, authorization)
}

export function removeJwt() {
  return Cookies.remove(JwtKey)
}

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
