import jwtDecode from "jwt-decode";
import router from "@/router";
import {
  login,
  findById,
  tokenRegeneration,
  logout,
  modifyPw,
  modifyInfo,
  deleteUser,
} from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    isUpdate: false,
    isDelete: false,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_USER_CHK: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_IS_UPDATE: (state, isUpdate) => {
      state.isUpdate = isUpdate;
    },
    SET_IS_DELETE: (state, isDelete) => {
      state.isDelete = isDelete;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userModifyPw({ commit }, updatePw) {
      await modifyPw(
        updatePw,
        ({ data }) => {
          if (data === "success") {
            commit("SET_IS_UPDATE", true);
          } else {
            commit("SET_IS_UPDATE", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userModifyInfo({ commit }, updateInfo) {
      await modifyInfo(
        updateInfo,
        ({ data }) => {
          if (data === "success") {
            commit("SET_IS_UPDATE", true);
          } else {
            commit("SET_IS_UPDATE", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userDelete({ commit }, userInfo) {
      await deleteUser(userInfo, ({ data }) => {
        if (data === "success") {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_VALID_TOKEN", false);
          commit("SET_IS_DELETE", true);
        }
      });
    },
    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      await findById(
        decodeToken.userId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
          }
        },
        async (error) => {
          console.log(error.response.status);
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userId,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("../modules/tourStore/SET_STORED_TOUR_PLAN", [], { root: true });
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    async userLogout({ commit }, userId) {
      await logout(
        userId,
        ({ data }) => {
          if (data.message === "success") {
            commit("tourStore/SET_STORED_TOUR_PLAN", [], { root: true });
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userStore;
