import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/goods/queryPage',
    method: 'get',
    params
  })
}

export function saveGoods(params) {
  return request({
    url: '/goods/add',
    method: 'post',
    data: params
  })
}

export function editGoods(params) {
  return request({
    url: '/goods/update',
    method: 'put',
    data: params
  })
}

export function remove(goodsId) {
  return request({
    url: '/goods/delete/' + goodsId,
    method: 'delete'
  })
}

