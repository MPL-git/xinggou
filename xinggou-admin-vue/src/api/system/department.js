import request from '@/utils/request'

// 部门树
export function deptTree(data) {
  return request({
    url: '/bc/dept/tree',
    method: 'post',
    data
  })
}

// 新增部门
export function deptCreate(data) {
  return request({
    url: '/bc/dept/create',
    method: 'post',
    data
  })
}

// 删除部门
export function deptDelete(data) {
  return request({
    url: '/bc/dept/delete',
    method: 'post',
    data
  })
}

// 部门信息
export function deptInfo(data) {
  return request({
    url: '/bc/dept/info',
    method: 'post',
    data
  })
}

// 更新部门
export function deptUpdate(data) {
  return request({
    url: '/bc/dept/update',
    method: 'post',
    data
  })
}
