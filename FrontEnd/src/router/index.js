import Vue from "vue";
import VueRouter from "vue-router";
import MainPage from "@/views/MainPage";

import store from "@/store";

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo != null && token) {
    await store.dispatch("userStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    router.push({ name: "UserLogin" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/user",
    redirect: "/user/login",
    component: () => import("@/views/UserPage.vue"),
    children: [
      {
        path: "login",
        name: "UserLogin",
        component: () => import("@/components/user/UserLogin.vue"),
      },
      {
        path: "regist",
        name: "UserRegist",
        component: () => import("@/components/user/UserRegist.vue"),
      },
      {
        path: "mypage",
        name: "UserMyPage",
        component: () => import("@/components/user/UserMyPage.vue"),
      },
      {
        path: "modify/password",
        name: "UserModifyPw",
        component: () => import("@/components/user/UserModifyPw.vue"),
      },
      {
        path: "modify/info",
        name: "UserModifyInfo",
        component: () => import("@/components/user/UserModifyInfo.vue"),
      },
    ],
  },
  {
    path: "/board",
    redirect: "/board/list",
    component: () => import("@/views/BoardPage.vue"),
    children: [
      {
        path: "list",
        name: "BoardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "BoardWrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "view/:id",
        name: "BoardView",
        component: () => import("@/components/board/BoardView.vue"),
      },
      {
        path: "modify/:id",
        name: "BoardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/place",
    name: "PlacePage",
    redirect: "/place/hotplace",
    component: () => import("@/views/PlacePage.vue"),
    children: [
      {
        path: "hotplace",
        name: "PlaceHotPlace",
        component: () => import("@/components/place/PlaceHotPlace.vue"),
      },
      {
        path: "view/:contentId",
        name: "PlaceView",
        component: () => import("@/components/place/PlaceView.vue"),
      },
    ],
  },
  {
    path: "/tour",
    name: "TourPage",
    redirect: "/tour/planlist",
    component: () => import("@/views/TourPage.vue"),
    children: [
      {
        path: "choose",
        name: "TourPlanPlaceChoose",
        component: () => import("@/components/tour/TourPlanPlaceChoose.vue"),
      },
      {
        path: "planlist",
        name: "TourPlanList",
        component: () => import("@/components/tour/TourPlanList.vue"),
      },
      {
        path: "plan/modify/:id",
        name: "TourPlanModify",
        component: () => import("@/components/tour/TourPlanModify.vue"),
      },
      {
        path: "plan/write",
        name: "TourPlanWrite",
        component: () => import("@/components/tour/TourPlanWrite.vue"),
      },
      {
        path: "plan/view/:id",
        name: "TourPlanView",
        component: () => import("@/components/tour/TourPlanView.vue"),
      },
    ],
  },
  {
    path: "/notice",
    redirect: "/notice/list",
    component: () => import("@/views/NoticePage.vue"),
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "write",
        name: "NoticeWrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeWrite.vue"),
      },
      {
        path: "view/:id",
        name: "NoticeView",
        component: () => import("@/components/notice/NoticeView.vue"),
      },
      {
        path: "modify",
        name: "NoticeModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
    ],
  },
  {
    path: "/qna",
    name: "QnAView",
    redirect: "/qna/list",
    component: () => import("@/views/QnAPage.vue"),
    children: [
      {
        path: "list",
        name: "QnAList",
        component: () => import("@/components/board/QnAList.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
