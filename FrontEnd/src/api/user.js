import axios from "./http-common";

async function login(user, success, fail) {
  await axios.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userId, success, fail) {
  axios.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await axios.get(`/user/info/${userId}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  axios.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await axios.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userId, success, fail) {
  await axios.get(`/user/logout/${userId}`).then(success).catch(fail);
}

async function modifyPw(updatePw, success, fail) {
  await axios.post(`/user/modify/password`, updatePw).then(success).catch(fail);
}

async function modifyInfo(updateInfo, success, fail) {
  await axios.put(`/user/modify/info`, updateInfo).then(success).catch(fail);
}

async function deleteUser(userInfo, success, fail) {
  await axios
    .delete(`/user/delete`, {
      params: userInfo,
    })
    .then(success)
    .catch(fail);
}
export { login, findById, tokenRegeneration, logout, modifyPw, modifyInfo, deleteUser };
