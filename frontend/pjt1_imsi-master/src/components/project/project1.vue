<template>
  <!-- <a href=""> 클릭 시 링크 설정 -->
  <!-- <router-link :to="'/project/projectdetail/'+project.pid"> -->
  <div class="card">
    <!-- <a href="#none" v-on:click="popup()"> -->
    <!-- 카드 헤더 -->
    <div class="card-header">
      <div class="card-header-is_closed">
        <!-- 여기 모집중 / 모집완료 분기 -->
        <div class="card-header-text">모집중</div>
        <div class="card-header-number">{{pjtcnt[project.pid]}} / {{project.pjtMemberCnt}}</div>
      </div>
    </div>

    <!--  카드 바디 -->
    <div class="card-body">
      <!--  카드 바디 헤더 -->
      <div class="card-body-header">
        <h1>{{project.pjtName}}</h1>
        <p class="card-body-hashtag">#이슈태그</p>
        <p class="card-body-nickname">팀장: {{project.makeId}}</p>
      </div>
      <p class="card-body-description">
        {{project.pjtContent}}
        <br />
        <button id="myBtn" @click="openModal">팀원관리</button>

        <button v-on:click="popup()">일정관리페이지</button>
      </p>
      <!-- The Modal -->
      <div id="myModal" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
          <span class="close">&times;</span>
          <h3>프로젝트 팀원 관리</h3>

          <ul>
            <li v-for="pm in pmlist" :key="pm.userId">{{ pm.userId }}</li>
          </ul>
        </div>
      </div>
      <!--  카드 바디 본문 : 넣지말기-->
      <!--  카드 바디 푸터, 조회수, 댓글 아이콘 넣기-->
      <div class="card-body-footer">
        <hr style="margin-bottom: 8px; opacity: 0.5; border-color: #EF5A31" />
        <i class="icon icon-view_count"></i>조회 n회
        <i class="icon icon-comments_count"></i>댓글 n개
        <i class="reg_date">{{project.makeDay}}</i>
      </div>
    </div>
    <!-- </a> -->
  </div>
  <!-- </router-link> -->
</template>

<script>
import Constant from "../../Constant";
export default {
  name: "project1",
  component: {},
  data: function () {
    return { index: 0 };
  },
  computed: {
    pjtcnt() {
      console.log(
        this.project.pid +
          "번 프로젝트 : " +
          this.$store.state.projectstore.pjtcnt
      );
      return this.$store.state.projectstore.pjtcnt;
    },
    pmlist() {
      console.log("pm test" + this.project.pid);
      return this.$store.state.projectstore.pmlist;
    },
  },
  mounted() {
    console.log(this.project.pid);
    this.$store.dispatch(Constant.GET_CURRENT_MEMBER_COUNT, {
      pid: this.project.pid,
    });
  },
  props: {
    project: {
      type: Object,
      required: true,
    },
  },
  methods: {
    popup() {
      //배포 주소로 바꾸기! 포트도
      var url =
        "http://localhost:8081/projectdetail.jsp?pid=" +
        this.project.pid +
        "&userId=" +
        "ssafy"; //여기 세션 아이디로 교체
      var name = "project detail";
      var option =
        "width = 1000, height = 750, top = 100, left = 200, location = no";
      window.open(url, name, option);
    },
    openModal() {
      console.log(this.project.pid);
      this.$store.dispatch(Constant.GET_PROJECT_MEMBER_BY_PID, {
        pid: this.project.pid,
      });
      console.log(this.$store.state.projectstore.pmlist);
      // Get the modal
      var modal = document.getElementById("myModal");
      // Get the <span> element that closes the modal
      var span = document.getElementsByClassName("close")[0];
      // When the user clicks on the button, open the modal
      modal.style.display = "block";
      // When the user clicks on <span> (x), close the modal
      span.onclick = function () {
        modal.style.display = "none";
      };
      // When the user clicks anywhere outside of the modal, close it
      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      };
      this.$store.dispatch(Constant.GET_PROJECT_MEMBER_BY_PID, {
        pid: this.project.pid,
      });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
// 아래 css 변경 금지
html,
body,
div,
span,
applet,
object,
iframes,
h1,
h2,
h3,
h4,
h5,
h6,
p,
blockquote,
pre,
a,
abbr,
acronym,
address,
big,
quotes,
code,
del,
dfn,
em,
img,
ins,
kbd,
q,
s,
samp,
small,
strike,
sub,
sup,
tt,
var,
u,
i,
center,
dl,
dt,
dd,
ol,
ul,
li,
fieldset,
form,
label,
legend,
table,
caption,
tbody,
tfoot,
thead,
tr,
th,
td,
article,
aside,
canvas,
details,
embed,
figure,
figcaption,
footer,
header,
hgroup,
menu,
nav,
output,
ruby,
section,
summary,
time,
mark,
audio,
video {
  margin: 0;

  padding: 0;

  border: 0;

  font-size: 100%;

  do: inherit;

  vertical-align: baseline;
}

article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
menu,
nav,
section {
  display: block;
}

blockquote,
q {
  quotes: none;
}

blockquote:before,
blockquote:after,
q:before,
q:after {
  content: "";

  content: none;
}

table {
  border-collapse: collapse;

  border-spacing: 0;
}

/*css 초기화*/

.card {
  height: 400px;

  width: 20%;

  border-radius: 15px;

  display: inline-block;
  // float:left;
  margin-top: 30px;

  margin-left: 30px;

  margin-bottom: 0px;

  position: relative;

  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);

  overflow: hidden;
}

.card-header {
  -webkit-transition: 0.5s; /*사파리 & 크롬*/

  -moz-transition: 0.5s; /*파이어폭스*/

  -ms-transition: 0.5s; /*인터넷 익스플로러*/

  -o-transition: 0.5s; /*오페라*/

  transition: 0.5s;

  width: 100%;

  height: 270px;

  border-radius: 15px 15px 0 0;

  // background-image: url("images/korea.jpeg");

  background-size: 100% 280px;

  background-repeat: no-repeat;
}

.card:hover .card-header {
  opacity: 0.8;

  height: 100px;
}

.card-header-is_closed {
  background-color: #ef5a31;

  color: #fff;

  font-weight: bold;

  text-align: center;

  float: right;

  margin: 15px 15px 0 0;

  border-radius: 50%;

  font-weight: bold;

  padding: 10px 10px;

  line-height: 20px;
}

h1 {
  font-size: 22px;

  font-weight: bold;
}

.card-body {
}

.card-body-header {
  line-height: 25px;

  margin: 10px 20px 0px 20px;
}

.card-body-description {
  opacity: 0;

  color: #757b82;

  line-height: 25px;

  -webkit-transition: 0.2s ease-in-out; /*사파리&크롬*/

  -moz-transition: 0.2s ease-in-out; /*파이어폭스*/

  -ms-transition: 0.2s ease-in-out; /*익스플로러*/

  -o-transition: 0.2s ease-in-out; /*오페라*/

  transition: 0.2s ease-in-out;

  overflow: hidden;

  height: 180px;

  margin: 5px 20px;
}

.card:hover .card-body-description {
  opacity: 1;

  -webkit-transition: 0.5s ease-in-out;

  -moz-transition: 0.5s ease-in-out;

  -ms-transition: 0.5s ease-in-out;

  -o-transition: 0.5s ease-in-out;

  transition: 0.5s ease-in-out;

  overflow: scroll;
}

.card-body-hashtag {
  color: #2478ff;

  font-style: italic;
}

.card-body-footer {
  position: absolute;
  margin-top: 15px;
  margin-bottom: 6px;
  bottom: 0;
  width: 314px;
  font-size: 14px;
  color: #9fa5a8;
  padding: 0 15px;
}

.icon {
  display: inline-block;

  vertical-align: middle;

  margin-right: 2px;
}

.icon-view_count {
  width: 25px;

  height: 17px;

  // background: url("images/eye.jpg") no-repeat;
}

.icon-comments_count {
  margin-left: 5px;

  width: 25px;

  height: 17px;

  // background: url("images/comment.jpg") no-repeat;
}

.reg_date {
  float: right;
}

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0, 0, 0); /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content/Box */
.modal-content {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  width: 80%; /* Could be more or less, depending on screen size */
}
/* The Close Button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}
.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
