<template>
	<el-container>
    欢迎使用大众优购小程序系统
	</el-container>
</template>

<script>
  export default {
    name: 'welcome' 
  }
</script>

<style scoped lang="scss">
  div {
    animation: leftMoveIn .7s ease-in;
  }

  @keyframes leftMoveIn {
    0% {
      transform: translateX(-100%);
      opacity: 0;
    }
    100% {
      transform: translateX(0%);
      opacity: 1;
    }
  }

  .title {
    text-align: center;
    font-size: 25px;
  }


  p {
    text-indent: 2em;
  }

  a {
    text-decoration: none
  }
</style>
