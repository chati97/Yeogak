/**
* Template Name: UpConstruction
* Updated: Mar 10 2023 with Bootstrap v5.2.3
* Template URL: https://bootstrapmade.com/upconstruction-bootstrap-construction-website-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
document.addEventListener('DOMContentLoaded', () => {
  "use strict";

  /**
   * Preloader
   */
  const preloader = document.querySelector('#preloader');
  if (preloader) {
    window.addEventListener('load', () => {
      preloader.remove();
    });
  }

  /**
   * Mobile nav toggle
   */

  const mobileNavShow = document.querySelector('.mobile-nav-show');
  const mobileNavHide = document.querySelector('.mobile-nav-hide');

  document.querySelectorAll('.mobile-nav-toggle').forEach(el => {
    el.addEventListener('click', function(event) {
      event.preventDefault();
      mobileNavToogle();
    })
  });

  function mobileNavToogle() {
    document.querySelector('body').classList.toggle('mobile-nav-active');
    mobileNavShow.classList.toggle('d-none');
    mobileNavHide.classList.toggle('d-none');
  }

  /**
   * Hide mobile nav on same-page/hash links
   */
  document.querySelectorAll('#navbar a').forEach(navbarlink => {

    if (!navbarlink.hash) return;

    let section = document.querySelector(navbarlink.hash);
    if (!section) return;

    navbarlink.addEventListener('click', () => {
      if (document.querySelector('.mobile-nav-active')) {
        mobileNavToogle();
      }
    });

  });

  /**
   * Toggle mobile nav dropdowns
   */
  const navDropdowns = document.querySelectorAll('.navbar .dropdown > a');

  navDropdowns.forEach(el => {
    el.addEventListener('click', function(event) {
      if (document.querySelector('.mobile-nav-active')) {
        event.preventDefault();
        this.classList.toggle('active');
        this.nextElementSibling.classList.toggle('dropdown-active');

        let dropDownIndicator = this.querySelector('.dropdown-indicator');
        dropDownIndicator.classList.toggle('bi-chevron-up');
        dropDownIndicator.classList.toggle('bi-chevron-down');
      }
    })
  });

  /**
   * Scroll top button
   */
  const scrollTop = document.querySelector('.scroll-top');
  if (scrollTop) {
    const togglescrollTop = function() {
      window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
    }
    window.addEventListener('load', togglescrollTop);
    document.addEventListener('scroll', togglescrollTop);
    scrollTop.addEventListener('click', window.scrollTo({
      top: 0,
      behavior: 'smooth'
    }));
  }

  /**
   * Initiate glightbox
   */
  const glightbox = GLightbox({
    selector: '.glightbox'
  });

  /**
   * Porfolio isotope and filter
   */
  let portfolionIsotope = document.querySelector('.portfolio-isotope');

  if (portfolionIsotope) {

    let portfolioFilter = portfolionIsotope.getAttribute('data-portfolio-filter') ? portfolionIsotope.getAttribute('data-portfolio-filter') : '*';
    let portfolioLayout = portfolionIsotope.getAttribute('data-portfolio-layout') ? portfolionIsotope.getAttribute('data-portfolio-layout') : 'masonry';
    let portfolioSort = portfolionIsotope.getAttribute('data-portfolio-sort') ? portfolionIsotope.getAttribute('data-portfolio-sort') : 'original-order';

    window.addEventListener('load', () => {
      let portfolioIsotope = new Isotope(document.querySelector('.portfolio-container'), {
        itemSelector: '.portfolio-item',
        layoutMode: portfolioLayout,
        filter: portfolioFilter,
        sortBy: portfolioSort
      });

      let menuFilters = document.querySelectorAll('.portfolio-isotope .portfolio-flters li');
      menuFilters.forEach(function(el) {
        el.addEventListener('click', function() {
          document.querySelector('.portfolio-isotope .portfolio-flters .filter-active').classList.remove('filter-active');
          this.classList.add('filter-active');
          portfolioIsotope.arrange({
            filter: this.getAttribute('data-filter')
          });
          if (typeof aos_init === 'function') {
            aos_init();
          }
        }, false);
      });

    });

  }

  /**
   * Init swiper slider with 1 slide at once in desktop view
   */
  new Swiper('.slides-1', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    }
  });

  /**
   * Init swiper slider with 2 slides at once in desktop view
   */
  new Swiper('.slides-2', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    breakpoints: {
      320: {
        slidesPerView: 1,
        spaceBetween: 20
      },

      1200: {
        slidesPerView: 2,
        spaceBetween: 20
      }
    }
  });

  /**
   * Initiate pURE cOUNTER
   */
  new PureCounter();

  /**
   * Animation on scroll function and init
   */
  function aos_init() {
    AOS.init({
      duration: 800,
      easing: 'slide',
      once: true,
      mirror: false
    });
  }
  window.addEventListener('load', () => {
    aos_init();
  });


});

function regist() {
  // 문서에서 id 로 input data 가져오기
  let id = document.getElementById("id").value;
  let password = document.getElementById("password").value;
  let passwordCheck = document.getElementById("passwordCheck").value;
  let name = document.getElementById("name").value;
  let email = document.getElementById("email").value;
  let age = document.getElementById("age").value;

  // 입력값 검증
  if (!id || !password || !name || !email || !age) {
    alert("빈칸이 없도록 입력해주세요.");
    return;
  }
  else if(password!==passwordCheck){
    alert("비밀번호가 일치하지 않습니다.")
  }
  else {
    // input data로 user 만들기
    const user = {
      id: id,
      password: password,
      name: name,
      email: email,
      age: age,
    };

    // user 객체 문자열로 바꿔서 로컬스토리지에 저장
    localStorage.setItem(id, JSON.stringify(user));
    alert("회원가입 성공. 로그인 창으로 이동합니다.");
    // 로그인 화면으로 돌아가기
    window.open("login.html","_parent");
  
  }
}

function login() {
  // 문서에서 id로 input data 가져오기
  let id = document.getElementById("id").value;
  let password = document.getElementById("password").value;

  // 로컬스토리지에 "user" 키로 저장된 item 가져와서 json 객체로 만들기
  const user = JSON.parse(localStorage.getItem(id));
  localStorage.setItem("user", JSON.stringify(user));

  // 입력값 검증
  if (user && user.id==id && user.password==password) {
    alert("메인 화면으로 이동합니다.");
    // 로그인 성공하면 index 페이지로 이동.
    window.open("index.html", "_parent");
    let logbar = document.getElementById("loginNav");
    logbar.setAttribute("style", "display:none");
    console.log(logbar)
    
  } else {
    localStorage.removeItem("user");
    alert("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
  }
}

function edit() {
  // 문서에서 id 로 input data 가져오기
  let id = document.getElementById("id").value;
  let password = document.getElementById("password").value;
  let name = document.getElementById("username").value;
  let email = document.getElementById("email").value;
  let age = document.getElementById("age").value;
  var temp = user.id;

  // 입력값 검증
  if (!id || !password || !name || !email || !age) {
    alert("빈칸이 없도록 입력해주세요.");
    return;
  }
  else {
    // input data로 user 만들기
    const user = {
      id: id,
      password: password,
      name: name,
      email: email,
      age: age,
    };
    localStorage.removeItem(temp);

    // user 객체 문자열로 바꿔서 로컬스토리지에 저장
    localStorage.setItem(id, JSON.stringify(user));
    localStorage.setItem("user", JSON.stringify(user));
    alert("정보 수정 성공. 홈 화면으로 이동합니다.");
    // 로그인 화면으로 돌아가기
    window.open("index.html","_parent");
  
  }
}

function signout() {
  // 문서에서 id로 input data 가져오기
  var temp = user.id;
  localStorage.removeItem("user");
  localStorage.removeItem(temp);
  window.open('index.html', '_self');
}

function logout() {
  // 문서에서 id로 input data 가져오기
  localStorage.removeItem("user");
}

