const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickName: state => state.user.nickName,
  gender: state => state.user.gender,
  id: state => state.user.id,
  mobile: state => state.user.mobile,
  userName: state => state.user.userName,
  permissionTree: state => state.user.permissionTree,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  user_routes: state => state.user.routes,

}
export default getters
