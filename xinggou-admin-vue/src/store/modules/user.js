import { login, logout, myInfo, permissionTree } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter, constantRoutes } from '@/router'
import Layout from '@/layout'

function childNodes(nodes, beforePath) {
  const res = []
  nodes.forEach((item) => {
    let a = {}
    const _p = beforePath + item.href
    // eslint-disable-next-line eqeqeq
    if (item.type == 10) {
      a = {
        // path: beforePath + item.href,
        path: '',
        component: Layout,
        name: item.name,
        // redirect: beforePath + item.href + '/' + item.childNodes[0].href,
        meta: { title: item.name, icon: 'el-icon-s-help' },
        children: childNodes(item.childNodes, beforePath + item.href + '/')
      }
    // eslint-disable-next-line eqeqeq
    } else if (item.type == 20) {
      a = {
        path: beforePath + item.href,
        name: item.name,
        component: resolve => require([`@/views${_p}`], resolve),
        meta: { title: item.name, icon: 'table' }
      }
    }
    res.push(a)
  })

  return res
}

const getDefaultState = () => {
  return {
    token: getToken(),
    nickName: '', // 名字
    avatar: '', // 头像
    gender: '', // 性别
    id: '', // 用户id
    mobile: '', // 手机号
    userName: '', // 账号
    permissionTree: '', // 菜单树
    routes: [],
    addRoutes: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NICKNAME: (state, nickName) => {
    state.nickName = nickName
  },

  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },

  SET_GENDER: (state, gender) => {
    state.gender = gender
  },

  SET_ID: (state, id) => {
    state.id = id
  },

  SET_MOBILE: (state, mobile) => {
    state.mobile = mobile
  },

  SET_USERNAME: (state, userName) => {
    state.userName = userName
  },

  SET_PERMISSIONTREE: (state, permissionTree) => {
    state.permissionTree = permissionTree
  },

  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ userName: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_NICKNAME', data.nickName)
        commit('SET_AVATAR', data.avatar)
        commit('SET_GENDER', data.gender)
        commit('SET_ID', data.id)
        commit('SET_MOBILE', data.mobile)
        commit('SET_USERNAME', data.userName)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  myInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      myInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        commit('SET_NICKNAME', data.nickName)
        commit('SET_AVATAR', data.avatar)
        commit('SET_GENDER', data.gender)
        commit('SET_ID', data.id)
        commit('SET_MOBILE', data.mobile)
        commit('SET_USERNAME', data.userName)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  permissionTree({ commit, state }) {
    return new Promise((resolve, reject) => {
      permissionTree(state.token).then(response => {
        // const { data } = response

        console.log(response)
        const data = childNodes(response.data.childNodes, '/')
        // const data = [{
        //   path: '/form',
        //   component: Layout,
        //   children: [
        //     {
        //       path: 'index',
        //       name: 'Form',
        //       component: () => import('@/views/system/user/index'),
        //       meta: { title: 'Form', icon: 'form' }
        //     }
        //   ]
        // }]

        if (!data) {
          return reject('获取菜单树失败')
        }
        commit('SET_PERMISSIONTREE', data)
        commit('SET_ROUTES', data)

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

