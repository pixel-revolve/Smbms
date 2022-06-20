import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/role/queryPage',
    method: 'get',
    params
  })
}

export function saveRole(params) {
  return request({
    url: '/role/add',
    method: 'post',
    data: params
  })
}

export function editRole(params) {
  return request({
    url: '/role/update',
    method: 'put',
    data: params
  })
}

export function remove(roleId) {
  return request({
    url: '/role/delete/' + roleId,
    method: 'delete'
  })
}

