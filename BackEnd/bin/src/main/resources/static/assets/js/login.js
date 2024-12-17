  // 로그인
  document.querySelector("#btn-login").addEventListener("click", function () {
    if (!document.querySelector("#login-id").value) {
      alert("아이디 입력!!");
      return;
    } else if (!document.querySelector("#login-password").value) {
      alert("비밀번호 입력!!");
      return;
    } else {
      let form = document.querySelector("#login-form");
      form.setAttribute("action", "/user/login");
      form.submit();
    }
  });

  // 회원가입
  document.querySelector("#btn-signup").addEventListener("click", function () {
    let userid = document.getElementById("signup-id").value;
    let userpw = document.getElementById("signup-password").value;
    let userpwc = document.getElementById("signup-password-check").value;
    let username = document.getElementById("signup-name").value;
    let usermname = document.getElementById("signup-emailid").value;
    let userms = document.getElementById("signup-emaildomain").value;
    if (
      !userid ||
      !userpw ||
      !userpwc ||
      !username ||
      !usermname ||
      !userms ||
      selectSido.value == 0 ||
      selectGugun.value == 0
    ) {
      alert("입력하지 않은 항목이 있습니다.");
      return;
    }
    else if (userpw != userpwc) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    } else {
      let form = document.querySelector("#signup-form");
      form.setAttribute("action", "/user/join");
      form.submit();
    }
  });