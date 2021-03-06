import request from '@/utils/request'

export function addAdmin(code, operator, password, post, status, team, username) {
  return request({
    url: '/admin/add',
    method: 'post',
    data: {
      code,
      operator,
      password,
      post,
      status,
      team,
      username
    }
  })
}

export function addAgent(applyId,
                         password,
                         status,
                         username,
                         list) {
  return request({
    url: '/agent/add',
    method: 'post',
    data: {
      applyId,
      password,
      status,
      username,
      list
    }
  })
}

export function addMerchant(
  applyId,
  level,
  password,
  username,
  status,
  list
) {
  return request({
    url: '/merchant/add',
    method: 'post',
    data: {
      applyId,
      level,
      password,
      username,
      status,
      list
    }
  })
}
// 添加供码用户
export function addSupplier(id, level, password, username,status) {
  return request({
    url: '/supplier/add',
    method: 'post',
    data: {
      id,
      level,
      password,
      username,
      status
    }
  })
}

export function deleteAdmin(aid) {
  return request({
    url: '/admin/delete',
    method: 'get',
    params: {
      aid
    }
  })
}

export function deleteAgent(aid) {
  return request({
    url: '/agent/delete',
    method: 'get',
    params: {
      aid
    }
  })
}

export function deleteMerchant(aid) {
  return request({
    url: '/merchant/delete',
    method: 'get',
    params: {
      aid
    }
  })
}

export function updateMerchant(uid,
                               level,
                               name,
                               password,
                               status,
                               wechat,
                               alipay_TPASS,
                               alipay_TSOLID,
                               alipay_RPASSOFF,
                               alipay_RPASSQR,
                               alipay_RSOLID
) {
  return request({
    url: '/merchant/update/' + uid,
    method: 'put',
    data: {
      level,
      name,
      password,
      status,
      wechat,
      alipay_TPASS,
      alipay_TSOLID,
      alipay_RPASSOFF,
      alipay_RPASSQR,
      alipay_RSOLID
    }
  })
}

export function updateAgent(uid, alipay,
                            name,
                            password,
                            status,
                            wechat) {
  return request({
    url: '/agent/update/' + uid,
    method: 'put',
    data: {
      alipay,
      name,
      password,
      status,
      wechat
    }
  })
}

export function updateStaff(uid,
                            name,
                            password,
                            status,
                            team,
                            post) {
  return request({
    url: '/staff/update/' + uid,
    method: 'put',
    data: {
      name,
      password,
      status,
      team,
      post
    }
  })
}

export function deleteSupplier(sid) {
  return request({
    url: '/supplier/delete',
    method: 'get',
    params: { sid }
  })
}

// 用户修改密码
export function updatePsw(psw, uid) {
  var data = {
    password: psw,
    uid: uid
  }
  return request({
    url: '/admin/updatePassword',
    method: 'post',
    data: data
  })
}
// 查看全部管理员
export function adminsGet() {
  return request({
    url: '/admins',
    method: 'get'
  })
}

export function agentsGet() {
  return request({
    url: '/agents',
    method: 'get'
  })
}

export function merchantsGet() {
  return request({
    url: '/merchants',
    method: 'get'
  })
}

export function merchantsMy(id) {
  return request({
    url: '/myMerchants/' + id,
    method: 'get'
  })
}

export function suppliersGet() {
  return request({
    url: '/suppliers',
    method: 'get'
  })
}

export function supplierUpdate(payTypeId, level, password, status, uid, id) {
  return request({
    url: '/supplier/update/' + id,
    method: 'put',
    data: {
      payTypeId,
      level,
      password,
      status,
      uid
    }
  })
}

export function deviceUpdate(id, imei, status) {
  return request({
    url: '/app/device',
    method: 'get',
    params: {
      id,
      imei,
      status
    }
  })
}

export function deviceInfo(id) {

}

// 内部码账变订单
export function qrcode(condition,page,size) {
  // var abc = {
  //   condition:condition,
  //   page:page,
  //   size:size
  // };
  return request({
    // url: '/internalaccountchange/qrcode',
    url: '/internalaccountchange/qrcode?condition='+condition+"&page="+page+"&size="+size,
    method: 'post'
    // data: {
    //   condition:condition,
    //   page:page,
    //   size:size
    // }
  })
}
// 内部卡账变订单
export function ShowCardOrder(condition,page,size) {
  return request({
    url: '/internalaccountchange/ShowCardOrder?condition='+condition+"&page="+page+"&size="+size,
    method: 'post'
    // data: {
    //   condition: condition,
    //   page: page,
    //   size: size
    // }
  })
}
// 提现历史
export function withdrewHistory(condition,size,page) {
  // page 是页数，size是每页最多展示的数量
  return request({
    url: '/withdrew/history?condition='+condition+"&page="+page+"&size="+size,
    method: 'post'
    // data: {
    //   condition: condition,
    //   page: page,
    //   size: size
    // }
  })
}

export function Pcard(cardNumber_in, cardNumber_out, money, operateId) {
  return request({
    url: '/internalaccountchange/C2Pcard',
    method: 'post',
    data: {
      cardNumber_in, cardNumber_out, money, operateId
    }
  })
}

export function Ccard(cardNumber_in, cardNumber_out, money, operateId) {
  return request({
    url: '/internalaccountchange/P2Ccard ',
    method: 'post',
    data: {
      cardNumber_in, cardNumber_out, money, operateId
    }
  })
}

export function withdrew(cardId, id, money, type) {
  return request({
    url: '/withdrew',
    method: 'post',
    data: {
      cardId,
      id,
      money,
      type
    }
  })
}

export function addPlatform(codeCategory) {
  return request({
    url: '/company/payPlatform/add',
    method: 'post',
    data: { codeCategory }
  })
}

export function getSelect() {
  return request({
    url: '/company/payPlatform/get',
    method: 'get'
  })
}

export function addPayType(codeCategory, codeType, status) {
  return request({
    url: '/company/payType/add',
    method: 'post',
    data: { codeCategory, codeType, status }
  })
}

export function updatePayPlatform(id, codeCategory) {
  return request({
    url: '/company/payPlatform/update/' + id,
    method: 'put',
    data: { codeCategory }
  })
}

export function getPayType(codeCategory) {
  return request({
    url: '/company/payType/get/' + codeCategory,
    method: 'get'
  })
}

export function updatePayType(id, codeCategory, codeType, status) {
  return request({
    url: '/company/payType/update/' + id,
    method: 'put',
    data: { codeCategory, codeType, status }
  })
}

export function getPayRateList(uid, payTypeId) {
  return request({
    url: '/payRateList/get/' + uid + '/' + payTypeId,
    method: 'get'
  })
}

export function updatePayRateList(uid, payType_id, rate, status) {
  return request({
    url: '/payRateList/update/' + uid,
    method: 'put',
    data: { payType_id, rate, status }
  })
}

export function getDevice(uid) {
  return request({
    url: '/app/device/get' + '?uid=' + uid,
    method: 'get'
  })
}

export function getAlipayByDevice(imei) {
  return request({
    url: '/app/getAlipayByDevice' + '?imei=' + imei,
    method: 'get'
  })
}

export function addPersonalCard(loginId, cardBalance, cardNumber) {
  return request({
    url: '/app/supplier/addPersonalCard' + '?loginId=' + loginId,
    method: 'post',
    data: { cardBalance, cardNumber }
  })
}

export function companyCards() {
  return request({
    url: '/app/supplier/getCompanyCard',
    method: 'get'
  })
}

export function getCompanyCard1(uid) {
  return request({
    url: '/app/supplier/getCompanyCard?uid=' + uid,
    method: 'get'
  })
}

export function getCompanyCard2(uid) {
  return request({
    url: '/app/supplier/getCollectingCard' + '?uid=' + uid,
    method: 'get'
  })
}

export function internalaccountchangeUpdate(uid, id) {
  return request({
    url: '/internalaccountchange/card/update/' + id + '?uid=' + uid,
    method: 'put'
  })
}

export function internalaccountchangeRevoke(id, reason) {
  return request({
    url: '/internalaccountchange/card/revoke/' + id,
    method: 'put',
    params: { reason }
  })
}

export function withdrewRevoke(id, reason) {
  return request({
    url: '/withdrew/revoke/' + id,
    method: 'put',
    data: { reason }
  })
}
