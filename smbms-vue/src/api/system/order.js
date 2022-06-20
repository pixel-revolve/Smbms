import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/order/queryPage',
    method: 'get',
    params
  })
}

export function saveOrder(params) {
  return request({
    url: '/order/add',
    method: 'post',
    data: params
  })
}

export function editOrder(params) {
  return request({
    url: '/order/update',
    method: 'put',
    data: params
  })
}

export function remove(orderId) {
  return request({
    url: '/order/delete/' + orderId,
    method: 'delete'
  })
}

export function setRole(params) {
  return request({
    url: '/user/setRole',
    method: 'post',
    params
  })
}

export function changeStatus(userId) {
  return request({
    url: '/user/changeStatus',
    method: 'get',
    params: {
      userId
    }
  })
}
