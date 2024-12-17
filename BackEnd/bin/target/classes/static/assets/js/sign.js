//index page 로딩 후 전국의 시도 설정.
let sidoUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;

const selectSido = document.getElementById("select-sido");
const selectGugun = document.getElementById("select-gugun");

fetch(sidoUrl, { method: "GET" })
  .then((response) => response.json())
  .then((data) => makeOption(data));

function makeOption(data) {
  let sidos = data.response.body.items.item;
  sidos.forEach((sido) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", sido.code);
    opt.appendChild(document.createTextNode(sido.name));
    selectSido.appendChild(opt);
  });
}

// 시도 클릭시 그 지역의 군구 설정
selectSido.addEventListener("change", function () {
  let sidoCode = selectSido.value;
  if (sidoCode != 0) {
    let gugunUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${serviceKey}&numOfRows=30&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=${sidoCode}&_type=json`;
    fetch(gugunUrl, { method: "GET" })
      .then((response) => response.json())
      .then((data) => makeOption(data));
    function makeOption(data) {
      selectGugun.innerHTML = `<option value="0" selected>시/군/구 선택</option>`;
      let guguns = data.response.body.items.item;
      guguns.forEach((gugun) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", gugun.code);
        opt.appendChild(document.createTextNode(gugun.name));
        selectGugun.appendChild(opt);
      });
    }
  }
});

//아이디 중복 확인
//var idOverlap = false;
//var signupid = "";
//document.getElementById("signup-id-check").addEventListener("click", function () {
//  idOverlap = false;
//  let idlist = [];
//  let idlistparams = new URLSearchParams();
//  idlistparams.append("action", "getidlist");
//  fetch("/users", { method: "POST" })
//    .then((response) => response.json())
//    .then(
//      (data) =>
//        function (data) {
//          for (var i = 0; i < idlist.length; i++) {
//            console.log(idlist[i]);
//          }
//        }
//    );
//});

//비밀번호, 비밀번호 확인 체크
document.getElementById("signup-name").addEventListener("click", function () {
  let password = document.getElementById("signup-password");
  let passwordCheck = document.getElementById("signup-password-check");
  let msg = document.getElementById("password-check-msg");
  let checkIcon = document.getElementById("signup-password-icon");

  if (password.value == passwordCheck.value) {
    if (password.value != "") {
      msg.innerText = "비밀번호가 일치합니다.";
      msg.setAttribute("style", "color: green");
      checkIcon.setAttribute("class", "bi-check pwcheck-icon");
      checkIcon.setAttribute("style", "color: green");
    } else {
      msg.innerText = "비밀번호를 입력하지 않았습니다.";
      msg.setAttribute("style", "color: red");
      checkIcon.setAttribute("class", "bi-x pwcheck-icon");
      checkIcon.setAttribute("style", "color: red");
      password.focus();
    }
  } else {
    msg.innerText = "비밀번호가 일치하지 않습니다.";
    msg.setAttribute("style", "color: red");
    checkIcon.setAttribute("class", "bi-x pwcheck-icon");
    checkIcon.setAttribute("style", "color: red");
    password.focus();
  }
});
