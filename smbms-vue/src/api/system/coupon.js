import request from '@/utils/request'

export function getList(params) {
return request({
url: '/coupon/queryPage',
method: 'get',
params
})
}

export function saveCoupon(params) {
  return request({
    url: '/coupon/add',
    method: 'post',
    data: params
  })
}

export function editCoupon(params) {
  return request({
    url: '/coupon/update',
    method: 'put',
    data: params
  })
}

export function remove(couponId) {
return request({
url: '/coupon/delete/'+couponId,
method: 'delete',
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
params:{
    userId
}
})
}


export function resetPassword(userId) {
return request({
url: '/user/resetPassword',
method: 'post',
params: {
    userId
}
})
}
