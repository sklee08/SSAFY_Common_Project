<template>
  <section class="messagesend">
    <h6>{{id}}님에게 전송</h6>
    <textarea v-model="mesval" cols="50" rows="3"></textarea>
    <button @click="send">보내기</button>
  </section>
</template>

<script>
export default {
  name: "messagesend",
  computed: {
    id: function () {
      return this.$store.state.userstore.mesdetailid;
    },
  },
  data: function () {
    return {
      mesval: "",
    };
  },
  methods: {
    send: function () {
      if (this.mesval !== "") {
        this.$store.dispatch("sendMes", {
          other: this.id,
          content: this.mesval,
        });
        this.mesval = '';
        let com = document.querySelector(".detaillist");
        setTimeout(() => {
          com.scrollTop = 9999999999;
        }, 50);
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.messagesend {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  font-size: 1rem;
  padding: 5px;
  max-height: 35vh;
}
</style>
