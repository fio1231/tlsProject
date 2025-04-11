<script setup>
import {onMounted, ref, watch} from 'vue'
import {BButton} from "bootstrap-vue-3";
import {post} from '/src/utils/UseAxios.js';
import router from "../routes/index.js";

const searchObj = ref({
  subject: ''
  ,pageNo: 1
  ,pageSize: 8
});

const boardList = ref([]);
const totCnt = ref(0);

const fnGetList = async () => {

  const res = await post('/get-board', {...searchObj.value});

  if(res.resultCode === '00000'){
    boardList.value = res.resultData.boardList;
    totCnt.value = res.resultData.totCnt;
  }else{
    boardList.value = [];
  }
}

const fnGoRegist = () => {
  router.push({name:'BoardDetail', state:{mode:'reg'}});
}

const fnGoDetail = (param) => {
  router.push({name:'BoardDetail', state:{mode:'dtl', no: param.no}});
}

onMounted(()=>{
  fnGetList();
})

watch(()=>searchObj.value.pageNo,()=>{
  fnGetList();
})

</script>

<template>
    <b-card>

      <b-card-header>
        <b-card-title>게시글</b-card-title>
        <b-container fluid>
          <b-row class="my-1">
            <b-col sm="8" />
            <b-col sm="2">
              <b-form-input v-model="searchObj.subject" placeholder="검색어를 입력하세요" type="text"></b-form-input>
            </b-col>
            <b-col sm="2">
              <b-button style="margin-right: 10px" variant="primary" @click="fnGetList">조회</b-button>
              <b-button variant="success" @click="fnGoRegist">등록</b-button>
            </b-col>
          </b-row>
        </b-container>
      </b-card-header>

      <b-card-body>
        <b-container class="text-center">
          <b-list-group style="margin-bottom: 10px">
            <b-list-group-item>
              <b-row style="font-weight: bold; font-size: 12pt;">
                <b-col>글 번호</b-col>
                <b-col cols="10">제목</b-col>
             </b-row>
            </b-list-group-item>
          </b-list-group>

            <b-list-group v-if="boardList && boardList.length > 0">
              <b-list-group-item v-for="(item, idx) in boardList" :key="idx" :variant="idx%2===0?'info':''" button @click="fnGoDetail(item)">
                <b-row>
                  <b-col>{{item.no}}</b-col>
                  <b-col cols="10" style="text-align: left">{{item.subject}}</b-col>
                </b-row>
              </b-list-group-item>
            </b-list-group>

          <template v-else>
            <b-row>
              <b-col cols="11">조회 된 데이터가 없습니다.</b-col>
            </b-row>
          </template>
        </b-container>
      </b-card-body>

      <b-card-footer>
        <div class="overflow-auto;">
          <b-pagination v-model="searchObj.pageNo" :per-page="searchObj.pageSize" :total-rows="totCnt" align="center" />
        </div>
      </b-card-footer>
    </b-card>
</template>
<style scoped>
.list-group-item {
  min-height: 50px !important;
}
</style>
