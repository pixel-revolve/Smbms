import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/provider/queryPage',
    method: 'get',
    params
  })
}

export function saveProvider(params) {
  return request({
    url: '/provider/add',
    method: 'post',
    data: params
  })
}

export function editProvider(params) {
  return request({
    url: '/provider/update',
    method: 'put',
    data: params
  })
}

export function remove(providerId) {
  return request({
    url: '/provider/delete/' + providerId,
    method: 'delete'
  })
}
