<template>
  <div>
    <!-- <div class="wrapper">
      <div class="sidebar" data-color="purple" data-image="./assets/issue_img/sidebar-5.jpg">
        <div class="sidebar-wrapper">
          <div class="logo">
            <a href class="simple-text">Project Info</a>
          </div>

          <ul class="nav">
            <li class="active">
              <a href="/main">
                <i class="pe-7s-graph"></i>
                <p>Issue Board</p>
              </a>
            </li>

            <li>
              <a href="teamInfo.html">
                <i class="pe-7s-note2"></i> 
                <p>Team Info</p>
              </a>
            </li>
            <li>
              <router-link :to="'/maps'">지도</router-link>
              <p>Maps</p>
              
            </li>
          </ul>
        </div>
    </div>-->

    <div class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-5">
            <div class="card">
              <div class="header">
                <h4 class="card-title">이슈 처리 현황 그래프</h4>
              </div>
              <div class="content">
                <div class="ct-chart">
                  <canvas id="myChart" style="height: 50%; width: 50%;"></canvas>
                </div>
                <div class="footer">
                  <hr />
                  <div class="stats">
                    <i class="fa fa-history"></i> Updated 3 minutes ago
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-7">
            <div class="card">
              <div class="header">
                <h4 class="card-title">최근 프로젝트 이슈</h4>
                <p class="category">날짜순</p>
              </div>
              <div class="content">
                <div class="overflow-auto table-responsive">
                  <table class="table">
                    <thead>
                      <th>날짜</th>
                      <th>제목</th>
                      <th class="td-actions text-right">상태</th>
                    </thead>
                    <tbody>
                      <tr v-for="(issue, index) in issues" :key="index">
                        <td>{{issue.changeDay}}</td>
                        <td>{{issue.issuetitle}}</td>
                        <td class="td-actions text-right">
                          <div v-if="issue.issuestate == 'created'" style="color:skyblue">생성됨</div>
                          <div v-if="issue.issuestate == 'ongoing'" style="color:red">진행중</div>
                          <div v-if="issue.issuestate == 'done'" style="color:yellowgreen">완료</div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>

                <div class="footer">
                  <hr />
                  <div class="stats">
                    <i class="fa fa-history"></i> Updated 3 minutes ago
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- <div class="col-4">
              <div class="card">
                <div class="header">
                  <h4 class="title" style="color: skyblue">생성된 이슈</h4>
                  <p class="category">날짜순</p>
                  <p align="right">
                    <button
                      @click="openModal"
                      type="button"
                      rel="tooltip"
                      title="Add"
                      class="btn btn-info btn-simple btn-xs"
                      data-toggle="modal"
                      data-target="#myModal"
                    >
                      <i class="fa fa-edit"></i>
                    </button>
                  </p>
                </div>
                <div class="content">
                  <div class="overflow-auto table-responsive">
                    <table class="table" ref="left" id="createdTable">
                      <tbody>
                        <tr>
                          <td>2020-08-01 10:01</td>
                          <td>생성된 이슈 테스팅1</td>
                          <td class="td-actions text-right">
                            <button
                              type="button"
                              rel="tooltip"
                              title="Remove"
                              class="btn btn-danger btn-simple btn-xs"
                            >
                              <i class="fa fa-times"></i>
                            </button>
                          </td>
                        </tr>
                        <tr>
                          <td>2020-08-01 20:09</td>
                          <td>생성된 이슈 테스팅2</td>
                          <td class="td-actions text-right">
                            <button
                              type="button"
                              rel="tooltip"
                              title="Remove"
                              class="btn btn-danger btn-simple btn-xs"
                            >
                              <i class="fa fa-times"></i>
                            </button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>

                  <div class="footer">
                    <hr />
                    <div class="stats">
                      <i class="fa fa-history"></i> Updated 3 minutes ago
                    </div>
                  </div>
                </div>
              </div>
          </div>-->

          <!-- <div class="col-4">
              <div class="card">
                <div class="header">
                  <h4 class="title" style="color: red">진행중인 이슈</h4>
                  <p class="category">날짜순</p>
                  <p align="right">
                    <button
                      type="button"
                      rel="tooltip"
                      title="Add"
                      data-toggle="modal"
                      data-target="#myModal"
                      class="btn btn-info btn-simple btn-xs"
                    >
                      <i class="fa fa-edit"></i>
                    </button>
                  </p>
                </div>
                <div class="content">
                  <div class="overflow-auto table-responsive">
                    <table class="table" id="ongoingTable" ref="right">
                      <tbody class="t_sortable">
                        <tr>
                          <td style="width: 40%; font-size: 8	0%">2020-08-03</td>
                          <td
                            style="width: 100%; font-size: 100%; overflow: hidden; text-overflow: ellipsis;"
                          >
                            진행중
                            이슈1
                          </td>
                          <td class="td-actions text-right">
                            <button
                              type="button"
                              rel="tooltip"
                              title="Remove"
                              class="btn btn-danger btn-simple btn-xs"
                            >
                              <i class="fa fa-times"></i>
                            </button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>

                  <div class="footer">
                    <hr />
                    <div class="stats">
                      <i class="fa fa-history"></i> Updated 3 minutes ago
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-4">
              <div class="card">
                <div class="header">
                  <h4 class="title" style="color: yellowgreen">완료된 이슈</h4>
                  <p class="category">날짜순</p>
                  <p align="right">
                    <button
                      type="button"
                      rel="tooltip"
                      title="Add"
                      data-toggle="modal"
                      data-target="#myModal"
                      class="btn btn-info btn-simple btn-xs"
                    >
                      <i class="fa fa-edit"></i>
                    </button>
                  </p>
                </div>
                <div class="content">
                  <div class="table-full-width">
                    <table class="table" id="doneTable">
                      <tbody class="t_sortable">
                        <tr>
                          <td>2020-08-03 23:02</td>
                          <td>완료 이슈 테스팅1</td>
                          <td class="td-actions text-right">
                            <button
                              type="button"
                              rel="tooltip"
                              title="Remove"
                              class="btn btn-danger btn-simple btn-xs"
                            >
                              <i class="fa fa-times"></i>
                            </button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="footer">
                  <hr />
                  <div class="stats">
                    <i class="fa fa-history"></i> Updated 3 minutes ago
                  </div>
                </div>
              </div>
          </div>-->
        </div>
        <!-- drag and drop -->
        <div class="content container-fluid row col-14 card" v-drag-and-drop:options="options">
          <h4 class="header card-title">이슈 현황</h4>
          <p align="right" class="category">
            <button
              @click="openModal"
              type="button"
              rel="tooltip"
              title="Add"
              class="btn btn-info btn-simple btn-xs"
              data-toggle="modal"
              data-target="#myModal"
            >
              <i class="fa fa-edit"></i>
            </button>
          </p>
          <hr />
          <ul class="drag-list content">
            <li class="drag-column" v-for="group in groups" :key="group.id">
              <span class="card-title">
                <h2 v-if="group.name == 'To Do'" style="color:skyblue">생성된 이슈</h2>
                <h2 v-if="group.name == 'In Progress'" style="color:red">진행중 이슈</h2>
                <h2 v-if="group.name == 'Done'" style="color:yellowgreen">완성된 이슈</h2>
                <!-- <feather-icon type="more-vertical"></feather-icon> -->
              </span>
              <vue-draggable-group
                v-model="group.items"
                :groups="groups"
                :data-id="group.id"
                @change="onGroupsChange"
              >
                <ul class="drag-inner-list" :data-id="group.id">
                  <li
                    class="drag-item"
                    v-for="item in group.items"
                    :key="item.id"
                    :data-id="item.id"
                  >
                    <div class="drag-item-text">{{ item.name }}</div>
                  </li>
                </ul>
              </vue-draggable-group>
            </li>
          </ul>
          <div class="footer">
            <hr />
            <div class="stats">
              <i class="fa fa-history"></i> Updated 3 minutes ago
            </div>
          </div>
        </div>
        <!-- drag and drop -->
      </div>
    </div>
  </div>
</template>

<script>
import { VueDraggableDirective } from "vue-draggable";
import Constant from "../../Constant";

export default {
  directives: {
    //dragAndDrop: VueDraggableDirective,
  },
  name: "issue1",
  data() {
    const componentInstance = this;
    return {
      data: [],
      left: {},
      right: {},
      groups: [
        {
          id: 1,
          name: "To Do",
          items: [
            // {
            //   id: 1,
            //   name: {
            //     pid : 1
            //   },
            //   groupId: 1,
            // },
          ],
        },
        {
          id: 2,
          name: "In Progress",
          items: [],
        },
        {
          id: 3,
          name: "Done",
          items: [],
        },
      ],
      options: {
        dropzoneSelector: ".drag-inner-list",
        draggableSelector: ".drag-item",
        reactivityEnabled: true,
        showDropzoneAreas: true,
        multipleDropzonesItemsDraggingEnabled: true,
        onDrop(event) {
          
        },
        // onDragstart(event) {
        //   event.stop();
        // },
        onDragend(event) {
          componentInstance.someDummyMethod();
          if (!event.droptarget) {
            //실패
          } else {
            // 성공
          }
        },
      },
    };
  },
  created() {
    this.$store.dispatch(Constant.GET_ISSUELIST, {
      pid: 1,
    });
    this.$store.dispatch(Constant.GET_ISSUELIST_BY_STATE, {
      pid: 1,
      issuestate: "done",
    });
    this.$store.dispatch(Constant.GET_ISSUELIST_BY_STATE, {
      pid: 1,
      issuestate: "ongoing",
    });
    this.$store.dispatch(Constant.GET_ISSUELIST_BY_STATE, {
      pid: 1,
      issuestate: "created",
    });
    this.loadIssues();
  },

  computed: {
    issues() {
      return this.$store.state.issuestore.issues;
    },

    issues_created() {
      return this.$store.state.issuestore.issues_created;
    },

    issues_done() {
      return this.$store.state.issuestore.issues_done;
    },

    issues_ongoing() {
      return this.$store.state.issuestore.issues_ongoing;
    },
  },

  mounted() {
    var colors = [
      "skyblue",
      "red",
      "yellowgreen",
      "#c3e6cb",
      "#dc3545",
      "#6c757d",
    ];

    var donutOptions = {
      cutoutPercentage: 30,
      legend: {
        position: "bottom",
        padding: 5,
        labels: {
          pointStyle: "circle",
          usePointStyle: true,
        },
      },
    };
    //this.data = [30,71,11];
    setTimeout(() => {
      this.data = [
        this.issues_created.length,
        this.issues_ongoing.length,
        this.issues_done.length,
      ];
      var chDonutData1 = {
        labels: ["생성된 이슈", "진행중인 이슈", "완료된 이슈"],
        datasets: [
          {
            backgroundColor: colors.slice(0, 3),
            borderWidth: 0,
            data: this.data,
          },
        ],
      };
      var chDonut1 = document.getElementById("myChart");
      if (chDonut1) {
        new Chart(chDonut1, {
          type: "pie",
          data: chDonutData1,
          options: donutOptions,
        });
      }
    }, 300);
  },

  methods: {
    someDummyMethod() {
      //
    },

    // {
    //           id: 1,
    //           name: {
    //             pid : 1
    //           },
    //           groupId: 1,
    //         },
    loadIssues() {
      let idx=1;
      // this.groups[0].items = this.issues_created;
      // this.groups[1].items = this.issues_ongoing;
      // this.groups[2].items = this.issues_done;
      this.issues_created.forEach(el => {
        this.groups[0].items.push({
          id : idx++,
          name : el.issuetitle,
          groupId : 1
        });
      });
      
      this.issues_ongoing.forEach(el => {
        this.groups[1].items.push({
          id : idx++,
          name : el.issuetitle,
          groupId : 2
        });
      });
      // this.groups[0].items = [{
      //   id: idx,
      //   name: {
      //     pid: 1,
      //   },
      //   groupId: 1,
      // },
      // {
      //   id: 2,
      //   name: {
      //     pid: 1,
      //   },
      //   groupId: 2,
      // }];
      
    },

    openModal() {
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
    },

    onGroupsChange(e) {
      //그룹바뀜
    },
  },
};
</script>

<style lang="scss" scoped>
$ease-out: all 0.3s cubic-bezier(0.23, 1, 0.32, 1);
$to-do: #f4ce46;
$in-progress: #2a92bf;
$approved: #00b961;

* {
  box-sizing: border-box;
}

body {
  background: #33363d;
  color: white;
  font-family: "Roboto Mono", serif;
  font-weight: 300;
  line-height: 1.5;
  -webkit-font-smoothing: antialiased;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.drag-container {
  max-width: 1000px;
  margin: 20px auto;
}

.drag-list {
  display: flex;
  align-items: flex-start;

  @media (max-width: 500px) {
    display: block;
  }
}

.drag-column {
  flex: 1;
  margin: 0 10px;
  position: relative;
  background: rgba(white, 0.2);
  overflow: hidden;

  @media (max-width: 690px) {
    margin-bottom: 30px;
  }

  h2 {
    font-size: 0.8rem;
    margin: 0;
    text-transform: uppercase;
    font-weight: 600;
  }

  &-to-do {
    .drag-column-header,
    .drag-options {
      background: $to-do;
    }
  }

  &-in-progress {
    .drag-column-header,
    .drag-options {
      background: $in-progress;
    }
  }

  &-approved {
    .drag-column-header,
    .drag-options {
      background: $approved;
    }
  }
}

.drag-column-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  user-select: none;
}

.drag-inner-list {
  height: 50vh;
  overflow: auto;
}

.drag-item {
  margin: 10px;
  height: 50px;
  background: rgba(black, 0.4);
  transition: $ease-out;

  /* items grabbed state */
  &[aria-grabbed="true"] {
    background: #5cc1a6;
    color: #fff;
  }

  .drag-item-text {
    font-size: 1rem;
    padding-left: 1rem;
    padding-top: 1rem;
  }
}

.drag-header-more {
  cursor: pointer;
}

@keyframes nodeInserted {
  from {
    opacity: 0.2;
  }
  to {
    opacity: 0.8;
  }
}

.item-dropzone-area {
  height: 6rem;
  background: #888;
  opacity: 0.8;
  animation-duration: 0.5s;
  animation-name: nodeInserted;
  margin-left: 0.6rem;
  margin-right: 0.6rem;
}
</style>
