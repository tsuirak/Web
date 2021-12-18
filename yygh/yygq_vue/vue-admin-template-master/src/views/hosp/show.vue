<template>
    <div class="app-container">
    <h4>基本信息</h4>
    <table class="table table-striped table-condenseda table-bordered" width="100%">
        <tbody>
            <tr>
                <th width="15%">医院名称</th>
                <td width="35%"><b style="font-size: 14px">{{ hospital.hosname }}</b> | {{ hospital.param.hostypeString }}</td>
                <th width="15%">医院logo</th>
                <td width="35%">
                    <img :src="'data:image/jpeg;base64,'+hospital.logoData" width="80">
                </td>
            </tr>
            <tr>
                <th>医院编码</th>
                <td>{{ hospital.hoscode }}</td>
                <th>地址</th>
                <td>{{ hospital.param.fullAddress }}</td>
            </tr>
            <tr>
                <th>坐车路线</th>
                <td colspan="3">{{ hospital.route }}</td>
            </tr>
            <tr>
                <th>医院简介</th>
                <td colspan="3">{{ hospital.intro }}</td>
            </tr>
        </tbody>
        </table>

        <h4>预约规则信息</h4>
        <table class="table table-striped table-condenseda table-bordered" width="100%">
        <tbody>
            <tr>
                <th width="15%">预约周期</th>
                <td width="35%">{{ bookingRule.cycle }}天</td>
                <th width="15%">放号时间</th>
                <td width="35%">{{ bookingRule.releaseTime }}</td>
            </tr>
            <tr>
                <th>停挂时间</th>
                <td>{{ bookingRule.stopTime }}</td>
                <th>退号时间</th>
                <td>{{ bookingRule.quitDay == -1 ? '就诊前一工作日' : '就诊当日' }}{{ bookingRule.quitTime }} 前取消</td>
            </tr>
            <tr>
                <th>预约规则</th>
                <td colspan="3">
                <ol>
                <li v-for="item in bookingRule.rule" :key="item">{{ item }}</li>
                </ol>
                </td>
            </tr>
        <br>
            <el-row>
            <el-button @click="back">返回</el-button>
            </el-row>
        </tbody>
    </table>
</div>
</template>

<script>
import hospApi from '@/api/hosp'
export default {
    data() {
        return {
            hospital: null,  // 医院信息
            bookingRule: null // 预约信息
        }
    },
    created() {
        const id = this.$route.params.id
        this.getHospDetail(id)
    },
    methods: {
        // 根据id查询医院详情
        getHospDetail(id) {
            hospApi.getHospById(id)
                .then(response => {
                    this.hospital = response.data.hospital
                    this.bookingRule = response.data.bookingRule
                })
        },
        //返回医院列表
        back() {
            this.$router.push({ path: '/hospSet/hosp/list' })
        }
    }
}
</script>

<style>
.app-container{
  background: #fff;
  position: absolute;
  width:100%;
  height: 100%;
  overflow: auto;
}
table {
  border-spacing: 0;
  border-collapse: collapse;
}
.table-bordered {
  border: 1px solid #ddd;
}
.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th {
  background-color: #f9f9f9;
}
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th,
.table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td {
  font-size: 14px;
  color: #333;
  padding: 8px;
  line-height: 1.42857143;
  vertical-align: top;
  border-top: 1px solid #ddd;
}
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th{
  text-align: right;
  width: 120px;
}
.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
  border: 1px solid #ddd;
}
.active_content{
  padding: 0 20px  ;
  border-radius: 4px;
  border: 1px solid #ebeef5;
  background-color: #fff;
  overflow: hidden;
  color: #303133;
  transition: .3s;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);

}
.active_content h4{
  /*line-height: 0;*/
}
.active_content span{
  font-size: 12px;
  color: #999;
}
</style>