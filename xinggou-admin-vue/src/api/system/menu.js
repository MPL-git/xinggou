import request from '@/utils/request'

// 新增菜单
export function menuCreate(data) {
  return request({
    url: '/bc/menu/create',
    method: 'post',
    data
  })
}

// 删除菜单
export function menuDelete(data) {
  return request({
    url: '/bc/menu/delete',
    method: 'post',
    data
  })
}

// 删除菜单
export function menuInfo(data) {
  return request({
    url: '/bc/menu/info',
    method: 'post',
    data
  })
}

// 菜单树
export function menuTree(data) {
  return request({
    url: '/bc/menu/tree',
    method: 'post',
    data
  })
}

// 更新菜单
export function menuUpdate(data) {
  return request({
    url: '/bc/menu/update',
    method: 'post',
    data
  })
}
