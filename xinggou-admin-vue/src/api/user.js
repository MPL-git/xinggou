import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function myInfo() {
  return request({
    url: '/my/info',
    method: 'post',
  })
}

export function permissionTree() {
  return request({
    url: '/my/permission/tree',
    method: 'post'
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function upload() {
  return request({
    url: '/rc/oss/upload',
    method: 'post'
  })
}
