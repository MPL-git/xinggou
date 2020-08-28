import request from '@/utils/request'

// 删除数据字典
export function dictDelete(data) {
  return request({
    url: '/bc/dict/delete',
    method: 'post',
    data
  })
}

// 数据字典信息
export function dictInfo(data) {
  return request({
    url: '/bc/dict/info',
    method: 'post',
    data
  })
}

// 数据字典列表
export function dictList(data) {
  return request({
    url: '/bc/dict/list',
    method: 'post',
    data
  })
}

// 新增（修改）数据字典
export function dictSave(data) {
  return request({
    url: '/bc/dict/save',
    method: 'post',
    data
  })
}

