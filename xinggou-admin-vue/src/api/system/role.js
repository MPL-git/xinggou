import request from '@/utils/request'

// 删除角色
export function roleDelete(data) {
  return request({
    url: '/bc/role/delete',
    method: 'post',
    data
  })
}

// 角色信息
export function roleInfo(data) {
  return request({
    url: '/bc/role/info',
    method: 'post',
    data
  })
}

// 角色列表
export function roleList(data) {
  return request({
    url: '/bc/role/list',
    method: 'post',
    data
  })
}

// 角色权限树获取（返回整颗树）
export function rolePermissionTree(data) {
  return request({
    url: '/bc/role/permission/tree',
    method: 'post',
    data
  })
}

// 更新角色权限
export function rolePermissionUpdate(data) {
  return request({
    url: '/bc/role/permission/update',
    method: 'post',
    data
  })
}

// 新增（更新）角色
export function roleSave(data) {
  return request({
    url: '/bc/role/save',
    method: 'post',
    data
  })
}
