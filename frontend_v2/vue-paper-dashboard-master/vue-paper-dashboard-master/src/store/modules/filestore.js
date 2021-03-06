import Vuex from 'vuex';
import Vue from 'vue';

import http from '../../http-common.js';
import axios from "axios";
Vue.use(Vuex);

import router from '../../router';
const filestore = {
    state: {
        filesBoard: [],
        // 여기랑 스프링 reffile controller 수정 필요
        fileUrl:"http://i3a208.p.ssafy.io:8080/api/reffile/image/file",
        uploadUrl:"http://i3a208.p.ssafy.io:8080/api/reffile/files",
    },
    
    actions: {
        upFiledirect:(store,payload)=>{
            let formData = new FormData();
            formData.append("file", payload.file);
            formData.append("fboardno", payload.bno);
            formData.append("makeId", window.localStorage.getItem("userid"));
            axios
              .post(store.state.uploadUrl, formData, {
                headers: {
                  accept: "application/json",
                  "Content-Type": "multipart/form-data",
                },
              })
              .then(function (result) {
                  if (payload.reload){
                      router.go()
                  }
            })
            // .catch(function () {
            //   alert("오류가 발생했습니다. 인터넷 연결 확인 후 새로고침해주세요");
            // });
        },
        upFileForBoard: (store, payload) => {
            store.state.filesBoard.forEach((el) => {

                let formData = new FormData();
                formData.append("file", el);
                formData.append("fboardno", payload.bno);
                formData.append("makeId", window.localStorage.getItem("userid"));
                axios
                    .post(store.state.uploadUrl, formData, {
                        headers: {
                            accept: "application/json",
                            "Content-Type": "multipart/form-data",
                        },
                    })
                    .then(function (result) {
                        store.commit('delTempFiles')
                    })
                    // .catch(function () {
                    //     alert('오류')
                    // });
            })
        },

    },

    mutations: {
        upBoardFiles: (state, payload) => {
            state.filesBoard.push(payload.file)
        },
        delTempFiles: (state) => {
            state.filesBoard = []
        }
    },

    modules: {
    }
};

export default filestore;
