import request from '@/utils/request'

// 员工列表
export function staffList(data) {
  return request({
    url: '/bc/staff/list',
    method: 'post',
    data
  })
}

// 创建员工
export function staffCreate(data) {
  return request({
    url: '/bc/staff/create',
    method: 'post',
    data
  })
}

// 更新员工
export function staffUpdate(data) {
  return request({
    url: '/bc/staff/update',
    method: 'post',
    data
  })
}

// 激活员工
export function staffActive(data) {
  return request({
    url: '/bc/staff/active',
    method: 'post',
    data
  })
}

// 删除员工
export function staffDelete(data) {
  return request({
    url: '/bc/staff/delete',
    method: 'post',
    data
  })
}

// 禁用员工
export function staffDisabled(data) {
  return request({
    url: '/bc/staff/disabled',
    method: 'post',
    data
  })
}

// 重置员工密码
export function staffPasswordReset(data) {
  return request({
    url: '/bc/staff/password/reset',
    method: 'post',
    data
  })
}

// 员工角色添加
export function staffRoleAdd(data) {
  return request({
    url: '/bc/staff/role/add',
    method: 'post',
    data
  })
}

// 员工角色列表查询
export function staffRoleList(data) {
  return request({
    url: '/bc/staff/role/list',
    method: 'post',
    data
  })
}

// 员工角色移除
export function staffRoleRemove(data) {
  return request({
    url: '/bc/staff/role/remove',
    method: 'post',
    data
  })
}
