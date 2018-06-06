import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    paymentTable: {
      data: [],
      totalCount: 0,
      sortOrders: [],
      pageNumber: 0,
      pageSize: 2
    },
    typeList: []
  },
  getters: {
    paymentTable(state) {
      return state.paymentTable
    },
    typeList(state) {
      return state.typeList
    },
  },
  mutations: {
    mergePaymentTable(state, data) {
      state.paymentTable = {
        ...state.paymentTable,
        ...data
      }
    },
    setTypeList(state, list) {
      state.typeList = list
    },
  },
  actions: {
    loadPaymentData,
    deletePayment({commit, state}, id) {
      return fetch('http://127.0.0.1:8110/api/payment' + `/${id}`, {
        method: 'DELETE'
      })
        .then(res => {
          return loadPaymentData({commit, state});
        })
    },
    loadTypeList({commit, state}) {
      return fetch('http://127.0.0.1:8110/api/payment/type', {
        method: 'GET'
      })
        .then(res => res.json())
        .then(list => list.map(item => ({label: item.type, value: item.type})))
        .then(list => {
          commit('setTypeList', list);
        })
    },
    submitNewPayment({commit, state}, payment) {
      const isMod = payment.id > 0;
      return fetch('http://127.0.0.1:8110/api/payment' + (isMod ? `/${payment.id}` : ''), {
        method: isMod ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payment)
      });
    },
    changePaymentPage({commit, state}, pageNumber) {
      commit('mergePaymentTable', {pageNumber, data: []});
      return loadPaymentData({commit, state});
    }
  }
})


function loadPaymentData({commit, state}) {
  const {pageNumber, pageSize, sortOrders} = state.paymentTable
  const params = {pageNumber, pageSize, sortOrders}
  const paramStr = `?params=${encodeURIComponent(JSON.stringify(params))}`
  return fetch('http://127.0.0.1:8110/api/payment' + paramStr, {
    method: 'GET'
  })
    .then(res => res.json())
    .then(res => {
      commit('mergePaymentTable', _.pick(res, _.keys(state.paymentTable)));
    })
}