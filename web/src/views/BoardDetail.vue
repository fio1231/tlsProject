<script setup>
import {onMounted, ref} from "vue";
import {BButton} from "bootstrap-vue-3";
import {post} from "../utils/UseAxios.js";
import router from "../routes/index.js";

const board = ref({
  no:0
  ,subject:''
  ,content:''
});

const {mode, no} = router.options.history.state;

const fnRegist = async () => {

  if('' === board.value.subject){
    alert('제목을 입력하세요.');
    return;
  }

  if('' === board.value.content){
    alert('내용을 입력하세요.');
    return;
  }

  const res = await post('/save-board', {...board.value});

  if(res.resultCode = '00000'){
    alert('등록 되었습니다.');
    await router.push({name: 'Board'});
  }else{
    alert(res.resultMsg);
  }
}

const fnUpdate = async () => {
  const res = await post('/update-board', {...board.value});

  if(res.resultCode = '00000'){
    alert('저장 되었습니다.');
    await router.push({name: 'Board'});
  }else{
    alert(res.resultMsg);
  }
}

const fnDelete = async () => {
  const res = await post('/delete-board', {no: board.value.no});

  if(res.resultCode = '00000'){
    alert('삭제 되었습니다.');
    await router.push({name: 'Board'});
  }else{
    alert(res.resultMsg);
  }
}

const fnGoList = () => {
  router.push({name: 'Board'});
}

const fnGetData = async () => {
  const res = await post('/get-board-dtl', {no});

  if(res.resultCode === '00000'){
    board.value = res.resultData;
  }else{
    board.value = {};
  }
}

onMounted(()=>{
  if('dtl' === mode){
    fnGetData();
  }
})

</script>

<template>
  <b-card>

    <b-card-header>
      <b-card-title>게시글</b-card-title>
      <b-container fluid>
        <b-row class="my-1">
          <b-col sm="9" />
          <b-col sm="3">
            <b-button style="margin-right: 10px" variant="secondary" @click="fnGoList">목록</b-button>
            <b-button v-if="mode === 'reg'" style="margin-right: 10px" variant="primary" @click="fnRegist">등록</b-button>
            <b-button v-if="mode === 'dtl'" style="margin-right: 10px" variant="success" @click="fnUpdate">수정</b-button>
            <b-button v-if="mode === 'dtl'" variant="danger" @click="fnDelete">삭제</b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-card-header>

    <b-card-body>
      <b-form>
        <b-form-group id="subject-group" label="제목" label-for="subject" style="font-weight: bold;">
          <b-form-input id="subject" v-model="board.subject" :readonly="mode==='dtl'" placeholder="제목을 입력하세요" type="text"></b-form-input>
        </b-form-group>
        <b-form-group id="content-group" label="내용" label-for="content" style="font-weight: bold;">
          <b-form-textarea id="textarea" v-model="board.content" rows="8"></b-form-textarea>
        </b-form-group>
      </b-form>
    </b-card-body>

  </b-card>
</template>

<style scoped>

</style>