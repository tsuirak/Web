<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>


      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="create()">生成</el-button>
    </el-form>

  </div>
</template>
<script>
import staApi from '@/api/sta/sta'
export default {
    data() {
        return {
            day:'',
            btnDisabled: false
        }
    },
    created() {

    },
    methods:{
        create() {
            staApi.createStaByDay(this.day)
                .then(response => {
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '生成数据成功!'
                    })
                    //跳转到图表显示页面
                    this.$router.push({path:'/sta/show'})
                })
        }
    }
}
</script>
